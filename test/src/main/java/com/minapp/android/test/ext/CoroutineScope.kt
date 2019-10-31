package com.minapp.android.test.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

suspend fun CoroutineScope.withMainIfActive(block: suspend CoroutineScope.() -> Unit): Unit {
    if (isActive) {
        withContext(Dispatchers.Main) {
            block.invoke(this)
        }
    }
}