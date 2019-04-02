package com.minapp.android.example.auth.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.minapp.android.example.R
import com.minapp.android.example.base.BaseActivity
import com.minapp.android.example.util.trimToNull
import com.minapp.android.sdk.auth.model.UpdateUserReq
import kotlinx.android.synthetic.main.activity_edit_user.*

class EditUserActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)
        init()
    }

    fun init() {
        provideViewModel(EditUserViewModel::class.java).apply {
            currentUser.observe(this@EditUserActivity, Observer {
                curUserTv.text = it
            })

            saveBtn.setOnClickListener {
                val request = UpdateUserReq().apply {
                    usernameEt.text.toString().trimToNull()?.also { username = it }
                    emailEt.text.toString().trimToNull()?.also { email = it }
                    pwdEt.text.toString().trimToNull()?.also { password = it }
                    newPwdEt.text.toString().trimToNull()?.also { newPassword = it }
                }
                save(request)
            }
        }
    }
}
