package com.minapp.android.sdk.test

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.minapp.android.sdk.BaaS
import org.junit.BeforeClass
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseTest {

    companion object {

        val ctx: Context = ApplicationProvider.getApplicationContext()
        val app: Application = ApplicationProvider.getApplicationContext()

        @BeforeClass
        @JvmStatic
        fun registerSdk() {
            BaaS.init(
                TestConst.BAAS_CLIENT_ID,
                app
            )
        }

    }

}