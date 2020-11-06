package com.minapp.android.sdk.exception;

public class TurnOnVivoPushException extends RuntimeException {

    public TurnOnVivoPushException(int code) {
        super("trun on vivo push fail, code " + String.valueOf(code));
    }
}
