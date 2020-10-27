package com.minapp.android.sdk.test

import com.minapp.android.sdk.database.Table
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.database.query.Where
import com.minapp.android.sdk.test.base.BaseAuthedTest
import com.minapp.android.sdk.ws.SubscribeEventData
import com.minapp.android.sdk.ws.SubscribeCallback
import com.minapp.android.sdk.ws.SubscribeEvent
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.locks.ReentrantLock

class WebSocketTest: BaseAuthedTest() {

    companion object {
        private const val TAG = "WebSocketTest"
    }

    /**
     * 一个简单的测试
     */
    @Test
    fun simple() {
        val table = Table("danmu");

        var initCount = 0
        val update = mutableListOf<String?>()
        var exception: Throwable? = null
        val cb = object: SubscribeCallback {
            override fun onInit() {
                initCount++;
            }

            override fun onEvent(event: SubscribeEventData) {
                when (event) {
                    SubscribeEvent.CREATE -> {
                        update.add(event.after?.getString("desc"))
                    }

                    SubscribeEvent.UPDATE -> {
                        update.add(event.after?.getString("desc"))
                    }

                    SubscribeEvent.DELETE -> {
                        update.add(null)
                    }
                }
            }

            override fun onError(tr: Throwable) {
                exception = tr
            }
        }


        val content = "烈焰弹幕使(${Util.randomString()})"
        val query = Query().apply {
            put(Where().apply {
                equalTo("name", content)
            })
        }
        table.subscribe(query, SubscribeEvent.CREATE, cb);
        table.subscribe(query, SubscribeEvent.UPDATE, cb);
        table.subscribe(query, SubscribeEvent.DELETE, cb);

        val record = table.createRecord().apply {
            put("name", content)
            put("desc", SubscribeEvent.CREATE.event)
            save()
        }
        delay()

        record.put("desc", SubscribeEvent.UPDATE.event)
        record.save()
        delay()

        record.delete()
        delay()

        delay(millis = 5 * 1000)
        Assert.assertEquals("init should be invoked three times", 3, initCount)
        Assert.assertNull("exception should be null", exception)
        Assert.assertEquals("update list should be size 3", 3, update.size)
        val expectedUpdate = arrayOf(SubscribeEvent.CREATE.event, SubscribeEvent.UPDATE.event, null)
        Assert.assertArrayEquals("update list not the same", expectedUpdate,
            update.toTypedArray())
    }
}