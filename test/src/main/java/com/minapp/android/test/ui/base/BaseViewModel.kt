package com.minapp.android.test.ui.base

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

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}