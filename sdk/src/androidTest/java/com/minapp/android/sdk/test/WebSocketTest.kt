package com.minapp.android.sdk.test

import android.util.Log
import com.minapp.android.sdk.Global
import com.minapp.android.sdk.auth.Auth
import com.minapp.android.sdk.database.Record
import com.minapp.android.sdk.database.Table
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.database.query.Where
import com.minapp.android.sdk.exception.SessionMissingException
import com.minapp.android.sdk.test.base.BaseAuthedTest
import com.minapp.android.sdk.test.util.SimpleCondition
import com.minapp.android.sdk.test.util.Util
import com.minapp.android.sdk.util.BaseCallback
import com.minapp.android.sdk.util.LazyProperty
import com.minapp.android.sdk.ws.*
import com.minapp.android.sdk.ws.exceptions.SubscribeErrorException
import io.crossbar.autobahn.websocket.WebSocketConnection
import io.crossbar.autobahn.websocket.interfaces.IWebSocketConnectionHandler
import okhttp3.internal.wait
import org.junit.Assert
import org.junit.Test
import java.lang.reflect.Field
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Semaphore
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

class WebSocketTest: BaseAuthedTest() {

    companion object {
        private const val TAG = "WebSocketTest"
    }

    private val cond by lazy { SimpleCondition() }
    private val table by lazy { Table("danmu") }
    private val sm by lazy { SessionManager.get() }

    /**
     * 一个简单的测试
     * 订阅 ON_CREATE/ON_UPDATE/ON_DELETE 三个事件
     * 然后执行 create/update/delete 操作
     * 最后判断是否收到这三个事件
     */
    @Test
    fun curd() {
        var initCount = 0
        val updateList = CopyOnWriteArrayList<String>()
        var exception: Throwable? = null
        val cb = object: SubscribeCallback {

            override fun onInit() {
                initCount++;
                Log.d(TAG, "init: $initCount")

                // create/update/delete 三个订阅者都订阅成功后，开个线程执行 CURD 操作
                if (initCount == 3) {
                    thread(start = true) {
                        try {
                            val record = table.createRecord()
                            record.put("name", SubscribeEvent.CREATE.event)
                            record.save()
                            record.put("name", SubscribeEvent.UPDATE.event)
                            record.save()
                            record.delete()
                        } catch (e: Exception) {
                            exception = e
                            cond.signalAll()
                        }
                    }
                }
            }

            /**
             * 另一个线程会执行 CURD 操作，这里应该会按顺序收到 create/update/delete 三个事件
             */
            override fun onEvent(event: SubscribeEventData) {
                Log.d(TAG, "onEvent: ${event.event}")
                when (event.event) {
                    SubscribeEvent.CREATE -> {
                        updateList.add(event.after?.getString("name"))
                    }

                    SubscribeEvent.UPDATE -> {
                        updateList.add(event.after?.getString("name"))
                    }

                    SubscribeEvent.DELETE -> {
                        updateList.add(SubscribeEvent.DELETE.event)
                        cond.signalAll()
                    }
                }
            }

            // 把异常记录起来，恢复测试线程
            override fun onError(tr: Throwable) {
                Log.d(TAG, "onError: ${tr.message}")
                exception = tr
                cond.signalAll()
            }
        }

        // 订阅后，阻塞测试线程
        table.subscribe(SubscribeEvent.CREATE, cb);
        table.subscribe(SubscribeEvent.UPDATE, cb);
        table.subscribe(SubscribeEvent.DELETE, cb);
        cond.await()

        // 测试线程恢复
        exception?.also { throw it }
        val expectedUpdateList = arrayOf(
            SubscribeEvent.CREATE.event, SubscribeEvent.UPDATE.event, SubscribeEvent.DELETE.event)
        Assert.assertArrayEquals(
            "update list not the same", expectedUpdateList, updateList.toTypedArray())
    }

    /**
     * 未登录的情况
     */
    @Test(expected = SessionMissingException::class)
    fun unSignIn() {
        Auth.logout()
        var exception: Throwable? = null
        table.subscribe(SubscribeEvent.CREATE, object : SubscribeCallback {
            override fun onInit() {
                cond.signalAll()
            }

            override fun onEvent(event: SubscribeEventData) {
                cond.signalAll()
            }

            override fun onError(tr: Throwable) {
                exception = tr
                cond.signalAll()
            }
        })

        cond.await()
        throw exception ?: Exception("it should be SessionMissingException")
    }

    /**
     * 当发生异常时（[SubscribeCallback.onError]），request 应该被 unsubscribe
     * 1）写入错误的 token，导致 onError
     * 2）断言有 exception 发生
     * 3）等待一会（unsubscribe 发生在 wamp thread），断言 request 已被 unsubscribe
     */
    @Test
    fun unsubscribeWhenOnError() {
        Auth.logout()
        Auth.signIn("000", "000", Long.MAX_VALUE)

        val throwable = AtomicReference<Throwable>(null)
        val subscription = table.subscribe(SubscribeEvent.CREATE, object : SubscribeCallback {
            override fun onInit() {
                cond.signalAll()
            }

            override fun onEvent(event: SubscribeEventData) {
                cond.signalAll()
            }

            override fun onError(tr: Throwable) {
                throwable.set(tr)
                cond.signalAll()
            }
        })

        Log.d(TAG, "test thread await")
        cond.await()
        Assert.assertNotNull(throwable.get())

        Log.d(TAG, "test thread wait 3s, enable request to be unsubscribed")
        cond.await(millisecond = 3 * 1000)
        Assert.assertFalse(subscription.alive())
    }

    /**
     * 当所有的订阅请求都被取消后，自动关闭 session
     */
    @Test
    fun autoDisconnect() {
        sm.clearSubscribers()
        cond.await(millisecond = 3000)

        var initCount = 0
        val cb = object : SubscribeCallback {
            override fun onInit() {
                initCount++
                if (initCount == 3)
                    cond.signalAll()
            }

            override fun onEvent(event: SubscribeEventData) {}
            override fun onError(tr: Throwable) {}
        }

        val subscriptions = mutableListOf<WampSubscription>()
        subscriptions.add(table.subscribe(SubscribeEvent.CREATE, cb))
        subscriptions.add(table.subscribe(SubscribeEvent.DELETE, cb))
        subscriptions.add(table.subscribe(SubscribeEvent.UPDATE, cb))
        cond.await()

        Assert.assertTrue(sm.isConnected)
        subscriptions.forEach { it.unsubscribe() }
        cond.await(millisecond = 3000)
        Assert.assertFalse(sm.isConnected)
    }

    /**
     * 测试订阅异常：表不存在
     */
    @Test
    fun invalidTable() {
        val exception = AtomicReference<Throwable>(null)
        Table(Util.randomString()).subscribe(SubscribeEvent.CREATE, object : SubscribeCallback {
            override fun onInit() {
                cond.signalAll()
            }

            override fun onEvent(event: SubscribeEventData) {
                cond.signalAll()
            }

            override fun onError(tr: Throwable) {
                exception.set(tr)
                cond.signalAll()
            }
        })
        cond.await()

        Assert.assertTrue(exception.get() is SubscribeErrorException)
    }

    /**
     * reconnect 机制的测试
     * 1）正常订阅，然后发送 CLOSE 消息触发 reconnect
     * 2）等待个 2s，执行 CREATE，应该会收到事件
     * 3）作为对比，增加一个 disable reconnect 的对比项
     */
    @Test
    fun reconnect() {
        var exception: Throwable? = null
        SessionManager.ENABLE_RECONNECT = false
        table.subscribe(SubscribeEvent.CREATE, object : SubscribeCallback {
            override fun onInit() {
                cond.signalAll()
            }

            override fun onEvent(event: SubscribeEventData) {}

            override fun onError(tr: Throwable) {
                exception = tr
                cond.signalAll()
            }
        })

        // 订阅成功
        cond.await()
        exception?.also { throw it }

        // 发送伪装的 CLOSE_CONNECTION_LOST，在 reconnect disable 的情况下会执行 onError
        Global.postDelayed({ closeConnection() }, 1000)
        cond.await()
        Assert.assertNotNull(exception)

        // 开启 reconnect 后重新订阅
        SessionManager.ENABLE_RECONNECT = true
        exception = null
        var record: Record? = null
        table.subscribe(SubscribeEvent.CREATE, object : SubscribeCallback {
            override fun onInit() {
                cond.signalAll()
            }

            override fun onEvent(event: SubscribeEventData) {
                record = event.after
                cond.signalAll()
            }

            override fun onError(tr: Throwable) {
                exception = tr
                cond.signalAll()
            }
        })
        cond.await()
        exception?.also { throw it }

        // 断开网络，等待一会待它恢复，这时不会收到异常
        closeConnection()
        cond.await(millisecond = 3000)
        Assert.assertNull(exception)
        Assert.assertNull(record)

        // 执行 CURD，依然会收到订阅消息
        val content = Util.randomString()
        table.createRecord()
            .put("name", content)
            .saveInBackground(object : BaseCallback<Record> {
                override fun onSuccess(t: Record?) {}
                override fun onFailure(e: Throwable?) {
                    cond.signalAll()
                }
            })
        cond.await()

        Assert.assertNull(exception)
        Assert.assertNotNull(record)
        Assert.assertEquals(content, record?.getString("name"))
    }

    private fun closeConnection() {
        val session = SessionManager::class.java.getDeclaredField("session")
            .apply { isAccessible = true }
            .let { (it.get(SessionManager.get()) as LazyProperty<*>).get() }

        val transport = session::class.java.getDeclaredField("transport")
            .apply { isAccessible = true }
            .get(session)

        val connection = transport::class.java.getDeclaredField("mConnection")
            .apply { isAccessible = true }
            .get(transport) as WebSocketConnection

        val onClose = WebSocketConnection::class.java.getDeclaredMethod(
            "onClose", Int::class.java, String::class.java)
            .apply { isAccessible = true }
        onClose.invoke(connection, IWebSocketConnectionHandler.CLOSE_CONNECTION_LOST, "")
    }
}