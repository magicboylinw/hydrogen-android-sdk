package com.minapp.android.test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.minapp.android.sdk.BaaS
import com.minapp.android.sdk.Global
import com.minapp.android.sdk.model.CloudFuncResp
import com.minapp.android.sdk.util.BaseCallback
import com.minapp.android.test.R
import com.minapp.android.test.ext.toast
import com.minapp.android.test.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_cloud_func.*

class CloudFuncFragment: BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cloud_func, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        invokeBtn.setOnClickListener {
            val funcName = funcNameTv.text.toString().trim()
            val data = dataTv.text.toString().trim()
            val sync = when {
                syncBtn.isChecked -> true
                asyncBtn.isChecked -> false
                else -> null
            }

            BaaS.invokeCloudFunc(funcName, data, sync, object: BaseCallback<CloudFuncResp> {
                override fun onSuccess(t: CloudFuncResp) {
                    toast("执行成功")
                    outputTv.text = Global.gsonPrint().toJson(t)
                }

                override fun onFailure(e: Throwable) {
                    toast(e.message ?: "error")
                    outputTv.text = ""
                }
            })
        }
    }
}