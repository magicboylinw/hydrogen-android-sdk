package com.minapp.android.sdk.util;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;

public interface BaseCallback<T> {

    @MainThread
    void onSuccess(T t);

    @MainThread
    void onFailure(Throwable e);

}
