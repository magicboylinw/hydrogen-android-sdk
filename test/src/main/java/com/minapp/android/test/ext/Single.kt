package com.minapp.android.test.ext

import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Single


fun <T> Single<T>.autoDisposable(owner: LifecycleOwner) =
    `as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))