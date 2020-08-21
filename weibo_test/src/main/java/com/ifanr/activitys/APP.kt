package com.ifanr.activitys

import android.app.Application
import android.os.Handler
import android.os.HandlerThread
import com.minapp.android.sdk.BaaS

class APP: Application() {

    override fun onCreate() {
        super.onCreate()

        // TODO 初始化
        BaaS.init("", "", this)
        BaaS.initWeiboComponent(this, "", "", "")
    }
}

val workerHandler by lazy {
    val worker = HandlerThread("app_worker")
    worker.start()
    Handler(worker.looper)
}