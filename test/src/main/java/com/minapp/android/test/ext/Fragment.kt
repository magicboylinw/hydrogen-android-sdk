package com.minapp.android.test.ext

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun Fragment.launchWithIO(block: suspend CoroutineScope.() -> Unit) {
    val job = Job()
    CoroutineScope(Dispatchers.IO + job).launch(block = block)
    lifecycle.addObserver(object: LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy(owner: LifecycleOwner) {
            job.cancel()
            owner.lifecycle.removeObserver(this)
        }
    })
}

fun Fragment.toast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}