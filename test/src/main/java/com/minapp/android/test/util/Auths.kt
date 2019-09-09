package com.minapp.android.test.util

import com.minapp.android.sdk.auth.Auth
import com.minapp.android.sdk.auth.CurrentUser
import java.lang.Exception


class MissAuthException: Exception("请先登录")


object Auths {

    val currentUser: CurrentUser
    get() = Auth.currentUser() ?: throw MissAuthException()
}