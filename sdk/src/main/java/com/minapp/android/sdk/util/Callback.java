package com.minapp.android.sdk.util;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;

public interface Callback<T> {

    @MainThread
    void onSuccess(@Nullable T t);

    @MainThread
    void onFailure(Exception e);

}
