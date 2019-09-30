package com.minapp.android.sdk.test

import android.util.Log
import com.minapp.android.sdk.auth.Auth
import org.junit.*
import org.junit.Assert.*
import org.junit.runners.MethodSorters
import java.util.*

/**
 * 这里要对执行顺序进行排序，先执行 signUp 后执行 signin
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AuthTest: BaseTest() {


    companion object {

        private lateinit var email: String
        private lateinit var username: String
        private lateinit var pwd: String


        @BeforeClass
        @JvmStatic
        fun genParams() {
            email = Util.randomEmail()
            username = Util.randomString()
            pwd = Util.randomString()
        }
    }



    @Before
    fun init() {
        Auth.logout()
    }

    @After
    fun destroy() {
        Thread.sleep(2000)
    }

    /**
     * 邮箱注册
     */
    @Test
    fun signUpByEmailTest() {
        val user = Auth.signUpByEmail(email, pwd)
        assertEquals(Auth.currentUserWithoutData()!!.userId, user.userId)
    }

    /**
     * 用户名注册
     */
    @Test
    fun signUpByUsername() {
        val user = Auth.signUpByUsername(username, pwd)
        assertEquals(Auth.currentUserWithoutData()!!.userId, user.userId)
    }

    /**
     * 邮箱注册
     */
    @Test
    fun signinByEmail() {
        val user = Auth.signInByEmail(email, pwd)
        assertEquals(Auth.currentUserWithoutData()!!.userId, user.userId)
    }

    /**
     * 用户名登录
     */
    @Test
    fun signinByUsername() {
        val user = Auth.signInByUsername(username, pwd)
        assertEquals(Auth.currentUserWithoutData()!!.userId, user.userId)
    }

    /**
     * 匿名登录
     */
    @Test
    fun signinAnonymous() {
        Auth.signInAnonymous()
        assertTrue(Auth.isAnonymous())
    }

    /**
     * 登出
     */
    @Test
    fun signoutTest() {
        signinByEmail()
        Auth.logout()
        assertFalse(Auth.signedIn())
    }

    /**
     * 判断是否登录
     */
    @Test
    fun signedInTest() {
        signinByEmail()
        assertTrue(Auth.signedIn())
    }
}