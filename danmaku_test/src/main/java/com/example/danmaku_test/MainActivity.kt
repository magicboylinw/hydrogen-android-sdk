package com.example.danmaku_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.minapp.android.sdk.BaaS
import com.minapp.android.sdk.auth.Auth
import com.minapp.android.sdk.database.Table
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.ws.SubscribeCallback
import com.minapp.android.sdk.ws.SubscribeEvent
import com.minapp.android.sdk.ws.SubscribeEventData
import io.crossbar.autobahn.Autobahn
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    private val mWorker by lazy {
        val thread = HandlerThread("worker")
        thread.start()
        Handler(thread.looper)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ifanrx -> SDK 测试应用 - 知晓程序
        BaaS.init("a4d2d62965ddb57fa4d6", application)
        Autobahn.enableDebugLog(true)

        // 按钮 A 点击事件
        // 错误的 table
        btnA.setOnClickListener { mWorker.post {
            signIn()
            subscribe(event = SubscribeEvent.CREATE, tableName = "abc")
        } }

        // 按钮 B 点击事件
        // 正确的 table
        btnB.setOnClickListener { mWorker.post {
            subscribe(event = SubscribeEvent.CREATE, tableName = "danmu_list")
        } }

        // 按钮 C 点击事件
        // 输出 create 对象
        btnC.setOnClickListener { mWorker.post {
            Table("danmu_list").createRecord()
                .put("text", "弹幕内容")
                .put("time", System.currentTimeMillis())
                .save()
        } }
    }

    private fun subscribe(query: Query? = null, event: SubscribeEvent, tableName: String) {
        Table(tableName).subscribe(query ?: Query(), event, object : SubscribeCallback {

            /**
             * 订阅成功，打印 init
             */
            override fun onInit() {
                print("$tableName init")
            }

            /**
             * 打印事件类型，before 和 after
             */
            override fun onEvent(event: SubscribeEventData) {
                print("${event.event.name}\n${event.before}\n${event.after}")
            }

            /**
             * 打印异常
             */
            override fun onError(tr: Throwable) {
                print("$tableName error\n${tr.message}")
            }
        })
    }

    private fun print(text: String?) {
        mHandler.post {
            console.text = text
        }
    }

    private fun signIn() {
        val email = Util.randomEmail()
        val pwd = Util.randomString()
        try {
            Auth.signUpWithEmail(email, pwd)
        } catch (ignored: Exception) {}
        Auth.signInWithEmail(email, pwd)
    }

    private fun logout() {
        Auth.logout()
    }
}
