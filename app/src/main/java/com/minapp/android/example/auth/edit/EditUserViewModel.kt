package com.minapp.android.example.auth.edit

import androidx.lifecycle.MutableLiveData
import com.minapp.android.example.base.BaseViewModel
import com.minapp.android.sdk.Global
import com.minapp.android.sdk.auth.Auth
import com.minapp.android.sdk.auth.model.UpdateUserReq
import kotlinx.coroutines.launch

class EditUserViewModel: BaseViewModel() {

    val currentUser = object : MutableLiveData<String>() {
        override fun onActive() {
            ioScope.launch {
                Auth.currentUser()?.also {
                    postValue(Global.gsonPrint().toJson(it))
                }
            }
        }
    }

    val saveFailure = MutableLiveData<Exception>()


    fun save(request: UpdateUserReq) {
        ioScope.launch {
            loadingDialog.postValue(true)
            try {
                val currentUser = Auth.currentUser()
                if (currentUser == null) {
                    toast.postValue("请先非匿名登录")
                } else {
                    currentUser.updateUser(request)
                    Auth.logout()
                    toast.postValue("修改成功，请重新登录")
                    closeActivity.postValue(true)
                }
            } catch (e: Exception) {
                toast.postValue(e.message)
            }
            loadingDialog.postValue(false)
        }
    }
}