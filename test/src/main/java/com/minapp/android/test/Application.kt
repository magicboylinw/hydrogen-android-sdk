package com.minapp.android.test

import android.app.Application
import com.minapp.android.sdk.BaaS

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        BaaS.init(Const.BAAS_CLIENT_ID, this)
    }
}