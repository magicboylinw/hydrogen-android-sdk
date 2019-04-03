package com.minapp.android.sdk.exception;

public class UninitializedException extends SdkException {

    static final int CODE = 602;
    static final String MSG = "uninitialized, you need invoke BaaS.init first!";

    public UninitializedException() {
        super(CODE, MSG);
    }

    public UninitializedException(Throwable cause) {
        super(CODE, MSG, cause);
    }
}
