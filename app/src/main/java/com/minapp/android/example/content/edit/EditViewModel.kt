package com.minapp.android.example.content.edit

import androidx.lifecycle.MutableLiveData
import com.minapp.android.example.base.BaseViewModel
import com.minapp.android.sdk.content.Content
import com.minapp.android.sdk.content.Contents
import kotlinx.coroutines.launch

class EditViewModel: BaseViewModel() {

    val data = MutableLiveData<Content>()

    fun init(id: String) {
        ioScope.launch {
            loadingDialog.postValue(true)
            try {
                data.postValue(Contents.content(id))
            } catch (e: Exception) {
                toast.postValue(e.message)
            }
            loadingDialog.postValue(false)
        }
    }

}