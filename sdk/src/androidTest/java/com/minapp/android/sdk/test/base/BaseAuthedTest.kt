package com.minapp.android.sdk.test.base

import com.minapp.android.sdk.auth.Auth
import com.minapp.android.sdk.test.Util
import org.junit.AfterClass
import org.junit.BeforeClass

open class BaseAuthedTest : BaseTest() {

    companion object {

        private val email = Util.randomEmail()
        private val pwd = Util.randomString()

        @BeforeClass
        @JvmStatic
        fun signIn() {
            try {
                Auth.signUpByEmail(email, pwd)
            } catch (e: Exception) {}
            Auth.signInByEmail(email, pwd)
        }

        @AfterClass
        @JvmStatic
        fun logout() {
            Auth.logout()
        }
    }
}
