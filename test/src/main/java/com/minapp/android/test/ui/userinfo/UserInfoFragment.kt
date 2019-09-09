package com.minapp.android.test.ui.userinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.minapp.android.sdk.Global
import com.minapp.android.sdk.auth.model.UpdateUserReq
import com.minapp.android.test.R
import com.minapp.android.test.ext.launchWithIO
import com.minapp.android.test.ext.toast
import com.minapp.android.test.ext.trimToNull
import com.minapp.android.test.ext.withMainIfActive
import com.minapp.android.test.ui.base.BaseFragment
import com.minapp.android.test.util.Auths
import kotlinx.android.synthetic.main.fragment_userinfo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

class UserInfoFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_userinfo, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        updateInfoBtn.setOnClickListener {
            updateUserInfo(email = emailTv.text.toString().trimToNull(), username = usernameTv.text.toString().trimToNull(),
                pwd = pwdTv.text.toString().trimToNull(), newPwd = newPwdTv.text.toString().trimToNull())
        }


        loadFieldBtn.setOnClickListener { printField(fieldNameTv.text.toString()) }

        sendFieldBtn.setOnClickListener { setField(fieldNameTv.text.toString(), fieldValueTv.text.toString()) }

        printUserBtn.setOnClickListener { printCurrentUser() }
    }

    /**
     * 设置自定义字段
     */
    private fun setField(key: String, value: String) {
        launchWithIO {
            try {
                val record = Auths.currentUser.put(key, value).save()
                withMainIfActive {
                    toast("操作成功")
                    printJson(record)
                }
            } catch (e: Exception) {
                withMainIfActive {
                    toast("操作失败")
                    printJson("")
                }
            }
        }
    }

    /**
     * 打印自定义字段
     */
    private fun printField(key: String) {
        launchWithIO {
            try {
                val value = Auths.currentUser.getString(key)
                withMainIfActive { printJson(value ?: "null") }
            } catch (e: Exception) {
                withMainIfActive {
                    toast("获取自定义字段($key)失败，${e.message}")
                    printJson("")
                }
            }
        }
    }


    /**
     * 修改个人资料
     */
    private fun updateUserInfo(email: String?, username: String?, pwd: String?, newPwd: String?) {
        launchWithIO {
            try {
                val resp = Auths.currentUser.updateUser(UpdateUserReq().also {
                    it.email = email
                    it.username = username
                    it.password = pwd
                    it.newPassword = newPwd
                })
                withMainIfActive {
                    toast("更新资料成功")
                    printJson(resp)
                }
            } catch (e: Exception) {
                withMainIfActive {
                    toast("更新资料失败，${e.message}")
                    printJson("")
                }
            }
        }
    }

    /**
     * 打印当前用户信息
     */
    private fun printCurrentUser() {
        launchWithIO {
            try {
                val user = Auths.currentUser
                withMainIfActive { printJson(user) }
            } catch (e: Exception) {
                withMainIfActive {
                    toast("获取个人信息失败，${e.message}")
                    printJson("")
                }
            }
        }
    }

    private fun printJson(any: Any) {
        infoTv.text = Global.gsonPrint().toJson(any)
    }
}