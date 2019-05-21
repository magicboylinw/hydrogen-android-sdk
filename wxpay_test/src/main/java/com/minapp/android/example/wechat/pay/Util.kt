package com.minapp.android.example.wechat.pay

import com.thoughtworks.xstream.XStream
import okhttp3.OkHttpClient
import java.text.SimpleDateFormat
import java.util.*

object Util {

    private val SDF = SimpleDateFormat("yyyyMMddHHmmSSS")

    val XSTREAM by lazy {
        XStream().apply {

        }
    }

    val OK_HTTP: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }



    /**
     * 生成长度为 32 的随机数字符串
     */
    fun random32(): String {
        return "5K8264ILTKCH16CQ2502SI8ZNMTM67VS"
    }

    /**
     * 商户订单号（基于时间戳）
     */
    fun timeTradeNo() = SDF.format(Date())
}