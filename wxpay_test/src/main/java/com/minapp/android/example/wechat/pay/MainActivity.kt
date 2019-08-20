package com.minapp.android.example.wechat.pay

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.minapp.android.sdk.BaaS
import com.minapp.android.sdk.Global
import com.minapp.android.sdk.alipay.AlipayComponent
import com.minapp.android.sdk.alipay.AlipayOrder
import com.minapp.android.sdk.auth.Auth
import com.minapp.android.sdk.model.OrderResp
import com.minapp.android.sdk.util.BaseCallback
import com.minapp.android.sdk.wechat.WechatComponent
import com.minapp.android.sdk.wechat.WechatOrder
import com.minapp.android.sdk.wechat.WechatOrderResult
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendWxOrderBtn.setOnClickListener {
            WechatComponent.sendWechatOrder(WechatOrder(0.01f, "知晓云充值-微信支付"), SEND_WX_ORDER, this)
        }
        alipayBtn.setOnClickListener {
            AlipayComponent.sendAlipayOrder(AlipayOrder(0.01f, "知晓云充值-支付宝支付"), SEND_ALIPAY_ORDER, this)
        }
        signIn()
    }

    /**
     * 默认登录策略
     */
    private fun signIn() {
        GlobalScope.launch {
            val user = "wxpay_test"
            val pwd = "123455"
            try {
                Auth.signUpByUsername(user, pwd)
            } catch (e: Exception) { Log.e(Const.TAG, e.message, e) }
            try {
                Auth.signInByUsername(user, pwd)
            } catch (e: Exception) { Log.e(Const.TAG, e.message, e) }
            withContext(Dispatchers.Main) {
                printSignIn()
            }
        }
    }

    private fun printSignIn() {
        tipTv.text = if (Auth.signedIn()) "已登录" else "支付前请先登录知晓云"
    }

    override fun onResume() {
        super.onResume()
        printSignIn()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {

            /**
             * 接收微信支付的结果
             */
            SEND_WX_ORDER -> {
                val result = WechatComponent.getOrderResultFromData(data)
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "充值成功", Toast.LENGTH_SHORT).show()
                    WechatComponent.getOrderInfo(result.orderInfo?.transactionNo, object : BaseCallback<OrderResp> {
                        override fun onSuccess(t: OrderResp) {
                            infoTv.text = Global.gsonPrint().toJson(t)
                        }

                        override fun onFailure(e: Throwable) {
                            Toast.makeText(this@MainActivity, "获取订单信息失败，${e.message}", Toast.LENGTH_SHORT).show()
                            Log.e(Const.TAG, e.message, e)
                        }
                    })
                } else {

                    Toast.makeText(this, "充值失败，参见 logcat", Toast.LENGTH_SHORT).show()
                    Log.e(Const.TAG, "code: ${result?.payResp?.errCode}, str: ${result?.payResp?.errStr}")
                    result?.exception?.also { Log.e(Const.TAG, it.message, it) }
                }
            }

            /**
             * 接收支付宝支付结果
             */
            SEND_ALIPAY_ORDER -> {
                val result = AlipayComponent.getOrderResultFromData(data)
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "充值成功", Toast.LENGTH_SHORT).show()
                    AlipayComponent.getOrderInfo(result.orderInfo?.transactionNo, object : BaseCallback<OrderResp> {
                        override fun onSuccess(t: OrderResp) {
                            infoTv.text = Global.gsonPrint().toJson(t)
                        }

                        override fun onFailure(e: Throwable) {
                            Toast.makeText(this@MainActivity, "获取订单信息失败，${e.message}", Toast.LENGTH_SHORT).show()
                            Log.e(Const.TAG, e.message, e)
                        }
                    })
                } else {

                    Toast.makeText(this, "充值失败，参见 logcat", Toast.LENGTH_SHORT).show()
                    Log.e(Const.TAG, Global.gsonPrint().toJson(result))
                }
            }
        }
    }

    companion object {
        private const val SEND_WX_ORDER = 5
        private const val SEND_ALIPAY_ORDER = 6
    }
}
