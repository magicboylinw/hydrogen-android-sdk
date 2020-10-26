package com.minapp.android.sdk.test.base

import com.minapp.android.sdk.auth.Auth
import com.minapp.android.sdk.test.Util
import org.junit.AfterClass
import org.junit.BeforeClass
import java.security.Security

open class BaseAuthedTest : BaseAndroidTest() {

    companion object {

        private val email = Util.randomEmail()
        private val pwd = Util.randomString()

        @BeforeClass
        @JvmStatic
        fun signIn() {
            try {
                Auth.signUpWithEmail(email, pwd)
            } catch (e: Exception) {}
            Auth.signInWithEmail(email, pwd)
        }

        @AfterClass
        @JvmStatic
        fun logout() {
            Auth.logout()
        }
    }
}
