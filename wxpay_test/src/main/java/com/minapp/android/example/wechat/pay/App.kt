package com.minapp.android.example.wechat.pay

import android.app.Application
import com.minapp.android.sdk.BaaS

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        BaaS.init("e33fcb18cb2c0a4763eb", this)
        BaaS.initWechatComponent("wx4b3c1aff4c5389f5", this)
    }

}