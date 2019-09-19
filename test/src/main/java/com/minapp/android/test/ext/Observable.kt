package com.minapp.android.test.ext

import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable

fun <T> Observable<T>.autoDisposable(owner: LifecycleOwner) =
    `as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))