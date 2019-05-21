package com.minapp.android.example.wechat.pay

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.minapp.android.sdk.wechat.WechatComponent
import com.minapp.android.sdk.wechat.WechatOrder
import com.minapp.android.sdk.wechat.WechatOrderResult
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendWxOrderBtn.setOnClickListener {
            WechatComponent.sendWechatOrder(WechatOrder(0.01f, "知晓云充值"), SEND_WX_ORDER, this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {

            SEND_WX_ORDER -> {
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "充值成功", Toast.LENGTH_SHORT).show()
                } else {

                    val result = WechatComponent.getOrderResultFromData(data)
                    Toast.makeText(this, "充值失败，参见 logcat", Toast.LENGTH_SHORT).show()
                    Log.e(Const.TAG, "code: ${result?.payResp?.errCode}, str: ${result?.payResp?.errStr}, exception: ${result?.exception}")
                }
            }
        }
    }

    companion object {
        private const val SEND_WX_ORDER = 5
    }
}
