package com.minapp.android.sdk.test

import com.minapp.android.sdk.BaaS
import com.minapp.android.sdk.exception.HttpException
import com.minapp.android.sdk.test.base.BaseAuthedTest
import org.junit.Test

class SmsTest: BaseAuthedTest() {

    companion object {
        private const val PHONE = "136602260"
    }

    /**
     * 发送短信验证码
     */
    @Test
    fun sendSmsTest() {
        assert(BaaS.sendSmsCode(PHONE))
    }

    /**
     * 校验短信验证码
     * 这里输入空验证码，所以返回 [HttpException] (code == 400) 才是正常的
     */
    @Test
    fun verifySmsTest() {
        val code = ""
        try {
            BaaS.verifySmsCode(PHONE, code)
        } catch (e: HttpException) {
            if (e.code == 400)
                return
        }
        throw IllegalStateException()
    }

}