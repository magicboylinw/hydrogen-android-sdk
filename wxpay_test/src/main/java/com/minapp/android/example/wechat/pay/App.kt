package com.minapp.android.example.wechat.pay

import android.app.Application
import com.minapp.android.sdk.BaaS

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        BaaS.init(BuildConfig.CLIENT_ID, this)
        BaaS.initWechatComponent(BuildConfig.APP_ID, this)
        BaaS.initWeiboComponent(this, BuildConfig.WB_APP_ID,
            "https://api.weibo.com/oauth2/default.html", "email")
    }

}