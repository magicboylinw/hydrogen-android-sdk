package com.minapp.android.sdk.exception;

public class AnonymousNotAllowedException extends SdkException {

    static final int CODE = 612;
    static final String MSG = "anonymous user is not allowed";

    public AnonymousNotAllowedException() {
        super(CODE, MSG);
    }

    public AnonymousNotAllowedException(Throwable cause) {
        super(CODE, MSG, cause);
    }
}
