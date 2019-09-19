package com.minapp.android.test.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.minapp.android.sdk.BaaS
import com.minapp.android.sdk.model.StatusResp
import com.minapp.android.sdk.util.BaseCallback
import com.minapp.android.test.Const
import com.minapp.android.test.R
import com.minapp.android.test.ext.toast
import com.minapp.android.test.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_sms.*

class SmsFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendBtn.setOnClickListener {
            val phone = phoneTv.text.toString().trim()
            BaaS.sendSmsCode(phone, object : BaseCallback<StatusResp> {
                override fun onSuccess(t: StatusResp) {
                    var msg = "已发送验证码"
                    if (!t.isOk) {
                        msg = "发送失败：${t.status}"
                    }
                    toast(msg)
                }

                override fun onFailure(e: Throwable) {
                    Log.e(Const.TAG, e.message, e)
                    toast(e.message ?: "error")
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
                    toast(msg)
                }

                override fun onFailure(e: Throwable) {
                    toast(e.message ?: "error")
                }
            })
        }
    }
}