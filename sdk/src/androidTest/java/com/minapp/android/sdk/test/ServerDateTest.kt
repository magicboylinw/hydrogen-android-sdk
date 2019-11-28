package com.minapp.android.sdk.test

import android.os.StrictMode
import android.security.NetworkSecurityPolicy
import android.util.Log
import com.minapp.android.sdk.BaaS
import com.minapp.android.sdk.auth.Auth
import com.minapp.android.sdk.test.base.BaseAndroidTest
import com.minapp.android.sdk.test.base.BaseTest
import com.minapp.android.sdk.util.DateUtil
import com.minapp.android.sdk.util.Util
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class ServerDateTest: BaseTest() {

    companion object {
        private const val TAG = "ServerDateTest"

        // 测试环境的配置
        private const val ENDPOINT = "http://172.16.0.88:8000"
        private const val CLIENT_ID = "9ed06615381bae89790f"
    }

    @Before
    fun init() {
        BaaS.init(CLIENT_ID, ENDPOINT, app)
    }

    /**
     * 测试获取服务器时间
     */
    @Test
    fun serverDateTest() {
        val serverTime = BaaS.getServerDate() ?: throw NullPointerException("server time")
        Log.d(TAG, "server date test: ${DateUtil.formatDBDateString(serverTime)}")
        assertNotNull(serverTime)
    }

}