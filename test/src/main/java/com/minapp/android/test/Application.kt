package com.minapp.android.test

import android.app.Application
import android.database.Observable
import com.minapp.android.sdk.BaaS
import io.reactivex.plugins.RxJavaPlugins

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        BaaS.init(Const.BAAS_CLIENT_ID, this)
        RxJavaPlugins.setErrorHandler {}
    }
}