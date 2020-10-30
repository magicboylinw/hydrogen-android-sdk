package com.minapp.android.sdk.test

import android.util.Log
import com.minapp.android.sdk.auth.Auth
import com.minapp.android.sdk.database.Table
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.database.query.Where
import com.minapp.android.sdk.exception.SessionMissingException
import com.minapp.android.sdk.test.base.BaseAuthedTest
import com.minapp.android.sdk.ws.SubscribeEventData
import com.minapp.android.sdk.ws.SubscribeCallback
import com.minapp.android.sdk.ws.SubscribeEvent
import okhttp3.internal.wait
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

class WebSocketTest: BaseAuthedTest() {

    companion object {
        private const val TAG = "WebSocketTest"
    }

    /**
     * 一个简单的测试
     * 订阅 ON_CREATE/ON_UPDATE/ON_DELETE
     * 执行 create/update/delete 操作
     */
    @Test
    fun simple() {
        val table = Table("danmu");

        var initCount = 0
        val updateList = CopyOnWriteArrayList<String>()
        var exception: Throwable? = null
        val lock = ReentrantLock()
        val condition = lock.newCondition()
        val cb = object: SubscribeCallback {

            override fun onInit() {
                initCount++;
                Log.d(TAG, "init: $initCount")
                if (initCount == 3) {
                    lock.lock()
                    condition.signal()
                    lock.unlock()
                }
            }

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
                        lock.lock()
                        condition.signal()
                        lock.unlock()
                    }
                }
            }

            override fun onError(tr: Throwable) {
                Log.d(TAG, "onError: ${tr.message}")
                exception = tr
                lock.lock()
                condition.signal()
                lock.unlock()
            }

            override fun onDisconnect() {
                Log.d(TAG, "onDisconnect")
                exception = Exception("onDisconnect")
                lock.lock()
                condition.signal()
                lock.unlock()
            }
        }

        table.subscribe(SubscribeEvent.CREATE, cb);
        table.subscribe(SubscribeEvent.UPDATE, cb);
        table.subscribe(SubscribeEvent.DELETE, cb);

        // 等待三个订阅都成功后，开始 curd
        lock.lock()
        try {
            Log.d(TAG, "wait until subscribe complete")
            condition.await()
        } finally {
            lock.unlock()
        }

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
                lock.lock()
                condition.signal()
                lock.unlock()
            }
        }

        // 等待 curd 完成 or 异常
        lock.lock()
        try {
            Log.d(TAG, "wait until curd complete")
            condition.await()
        } finally {
            lock.unlock()
        }

        Log.d(TAG, "test finish")
        exception?.also { throw it }
        val expectedUpdateList = arrayOf(SubscribeEvent.CREATE.event, SubscribeEvent.UPDATE.event,
            SubscribeEvent.DELETE.event)
        Assert.assertArrayEquals("update list not the same", expectedUpdateList,
            updateList.toTypedArray())
    }

    /**
     * 未登录的情况
     */
    @Test(expected = SessionMissingException::class)
    fun unSignIn() {
        Auth.logout()

        val lock = ReentrantLock()
        lock.lock()
        val condition = lock.newCondition()
        var exception: Throwable? = null
        Table("danmu").subscribe(SubscribeEvent.CREATE, object : SubscribeCallback {
            override fun onInit() {
                lock.lock()
                condition.signal()
                lock.unlock()
            }

            override fun onEvent(event: SubscribeEventData) {
                lock.lock()
                condition.signal()
                lock.unlock()
            }

            override fun onError(tr: Throwable) {
                exception = tr
                lock.lock()
                condition.signal()
                lock.unlock()
            }

            override fun onDisconnect() {
                lock.lock()
                condition.signal()
                lock.unlock()
            }
        })

        try {
            condition.await()
        } finally {
            lock.unlock()
        }

        throw exception ?: Exception("it should be SessionMissingException")
    }
}