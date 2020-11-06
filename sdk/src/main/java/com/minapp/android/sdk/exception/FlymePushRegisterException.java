package com.minapp.android.sdk.exception;

import androidx.annotation.NonNull;

import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;

public class FlymePushRegisterException extends RuntimeException {

    public FlymePushRegisterException(@NonNull RegisterStatus status) {
        super(String.format("flyme push register fail, %s, %s", status.code, status.message));
    }
}
