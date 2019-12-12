package com.minapp.android.example.wechat.pay.auth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.minapp.android.example.wechat.pay.BuildConfig
import com.minapp.android.example.wechat.pay.Const
import com.minapp.android.example.wechat.pay.R
import com.minapp.android.sdk.wechat.WXEntryActivity
import com.minapp.android.sdk.wechat.WechatComponent
import com.minapp.android.sdk.wechat.WechatSignInCallback
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import kotlinx.android.synthetic.main.activity_auth.*
import java.lang.Exception

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        wechatLogin.setOnClickListener {
            WechatComponent.signIn(object: WechatSignInCallback {
                override fun onSuccess() {
                    Toast.makeText(this@AuthActivity, "微信登录成功", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(ex: Exception?) {
                    Toast.makeText(this@AuthActivity, "微信登录失败: ${ex?.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
