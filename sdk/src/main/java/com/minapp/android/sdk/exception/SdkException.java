package com.minapp.android.sdk.exception;

import android.annotation.TargetApi;

import java.io.IOException;

public class SdkException extends IOException {

    private int code;
    private String msg;

    public SdkException(int code, String msg) {
        this(code, msg, null);
    }

    public SdkException(int code, String msg, Throwable cause) {
        super("code: " + code + (msg != null ? (", " + msg) : ""), cause);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
