package com.minapp.android.example

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import com.minapp.android.example.util.Util
import com.minapp.android.sdk.BaaS
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initBaaS()
    }

    fun initBaaS() {
        GlobalScope.launch {
            try {
                packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)?.metaData?.getString("baasClientId").takeIf { !it.isNullOrBlank() }?.also {
                    BaaS.init(it, this@App)
                }
            } catch (e: Exception) {
                Log.e(Const.TAG, e.message, e)
            }
        }
    }

}