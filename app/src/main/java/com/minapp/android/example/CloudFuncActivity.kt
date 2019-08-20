package com.minapp.android.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.JsonObject
import com.minapp.android.example.base.BaseActivity
import com.minapp.android.sdk.BaaS
import com.minapp.android.sdk.Global
import com.minapp.android.sdk.model.CloudFuncResp
import com.minapp.android.sdk.util.BaseCallback
import kotlinx.android.synthetic.main.activity_cloud_func.*

class CloudFuncActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloud_func)
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
                    Toast.makeText(this@CloudFuncActivity, "执行成功", Toast.LENGTH_SHORT).show()
                    outputTv.text = Global.gsonPrint().toJson(t)
                }

                override fun onFailure(e: Throwable) {
                    Toast.makeText(this@CloudFuncActivity, e.message, Toast.LENGTH_SHORT).show()
                    outputTv.text = ""
                }
            })
        }
    }
}
