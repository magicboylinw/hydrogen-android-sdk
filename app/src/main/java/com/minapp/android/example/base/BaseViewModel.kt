package com.minapp.android.example.base

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel: ViewModel() {

    private val job = Job()
    protected val ioScope = CoroutineScope(job + Dispatchers.IO)
    val loadingDialog = MutableLiveData<Boolean>()
    val toast = MutableLiveData<String>()
    val opToast = MutableLiveData<Boolean>()
    val startActivity = MutableLiveData<Class<out Activity>>()
    val startActivityForResult = MutableLiveData<Pair<Class<out Activity>, Int>>()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}