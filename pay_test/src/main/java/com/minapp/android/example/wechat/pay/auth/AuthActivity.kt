package com.minapp.android.example.wechat.pay.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.minapp.android.example.wechat.pay.Const
import com.minapp.android.example.wechat.pay.MainActivity
import com.minapp.android.example.wechat.pay.R
import com.minapp.android.example.wechat.pay.toast
import com.minapp.android.sdk.auth.Auth
import com.minapp.android.sdk.wechat.*
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // 微信登录
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

        // 邮箱登录
        emailSignIn.setOnClickListener {
            val email = emailTv.text.toString()
            val pwd = pwdTv.text.toString()

            GlobalScope.launch {
                try {
                    try {
                        Auth.signUpWithEmail(email, pwd)
                    } catch (e: Exception) {}
                    Auth.signInWithEmail(email, pwd)
                    WechatComponent.associationWithWechat(AssociationType.OVERWRITE, object: AssociationCallback {
                        override fun onSuccess() {
                            toast("绑定成功")
                        }

                        override fun onFailure(ex: Exception?) {
                            toast("绑定失败(${ex?.message})")
                            if (ex != null) {
                                Log.e(Const.TAG, ex.message, ex)
                            }
                        }
                    })
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        toast("绑定失败(${e.message})")
                        Log.e(Const.TAG, e.message, e)
                    }
                }
            }
        }


        pay.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
