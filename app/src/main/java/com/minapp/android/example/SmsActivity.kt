package com.minapp.android.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.minapp.android.example.base.BaseActivity
import com.minapp.android.sdk.BaaS
import com.minapp.android.sdk.model.StatusResp
import com.minapp.android.sdk.util.BaseCallback
import kotlinx.android.synthetic.main.activity_sms.*

class SmsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)

        sendBtn.setOnClickListener {
            val phone = phoneTv.text.toString().trim()
            BaaS.sendSmsCode(phone, object : BaseCallback<StatusResp> {
                override fun onSuccess(t: StatusResp) {
                    var msg = "已发送验证码"
                    if (!t.isOk) {
                        msg = "发送失败：${t.status}"
                    }
                    Toast.makeText(this@SmsActivity, msg, Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(e: Throwable) {
                    Log.e(Const.TAG, e.message, e)
                    Toast.makeText(this@SmsActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

        verifyBtn.setOnClickListener {
            val phone = phoneTv.text.toString().trim()
            val code = codeTv.text.toString().trim()
            BaaS.verifySmsCode(phone, code, object : BaseCallback<StatusResp> {
                override fun onSuccess(t: StatusResp) {
                    var msg = "校验通过"
                    if (!t.isOk) {
                        msg = "校验失败：${t.status}"
                    }
                    Toast.makeText(this@SmsActivity, msg, Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(e: Throwable) {
                    Toast.makeText(this@SmsActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
