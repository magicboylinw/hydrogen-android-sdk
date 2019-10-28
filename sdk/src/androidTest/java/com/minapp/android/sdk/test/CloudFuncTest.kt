package com.minapp.android.sdk.test

import com.minapp.android.sdk.BaaS
import com.minapp.android.sdk.test.base.BaseAuthedTest
import com.minapp.android.sdk.test.base.BaseTest
import org.junit.Assert.*
import org.junit.Test

class CloudFuncTest: BaseAuthedTest() {

    companion object {
        private const val FUNC_NAME = "android_test_helloworld"
    }

    /**
     * 测试云函数
     * requirement:
     * 1) 一个名为 [FUNC_NAME] 的云函数
     * 2）代码为 「Hello World」模板，也即返回「hello world」，参考：
     * exports.main = function functionName(event, callback) {
     *      callback(null, "hello world")
     * }
     */
    @Test
    fun execCloudFuncTest() {
        assertEquals(BaaS.invokeCloudFunc(FUNC_NAME, null, true).data.asString, "hello world")
    }

}