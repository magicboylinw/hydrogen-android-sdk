package com.minapp.android.example.database.edit

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.load.engine.bitmap_recycle.IntegerArrayAdapter
import com.minapp.android.example.base.BaseViewModel
import com.minapp.android.example.database.dao.Horse
import com.minapp.android.example.util.Util
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditViewModel: BaseViewModel() {

    val name = MutableLiveData<String>()
    val age = MutableLiveData<Int>()
    val btnText = MutableLiveData<String>()
    val btnEnabled = MutableLiveData<Boolean>()
    val close = MutableLiveData<Boolean>()
    private var data: Horse? = null

    fun init(id: String?) {
        ioScope.launch {
            if (id != null) {
                btnText.postValue("Update")
                loadingDialog.postValue(true)
                runCatching {
                    data = Horse(id)
                    name.postValue(data?.name)
                    age.postValue(data?.age)
                    loadingDialog.postValue(false)
                }.onFailure {
                    toast.postValue(it.message)
                    btnEnabled.postValue(false)
                }
                loadingDialog.postValue(false)

            } else {
                btnText.postValue("Create")
            }
        }
    }

    fun save() {
        ioScope.launch {
            loadingDialog.postValue(true)
            runCatching {
                if (data != null) {
                    data?.name = name.value
                    data?.age = runCatching { age.value?.toInt() }.getOrNull()
                    data?.save()

                } else {
                    Horse().apply {
                        name = this@EditViewModel.name.value
                        age = this@EditViewModel.age.value
                    }.save()
                }

                opToast.postValue(true)
                close.postValue(true)
            }.onFailure {
                toast.postValue(it.message)
            }
            loadingDialog.postValue(false)
        }
    }
}