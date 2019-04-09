package com.minapp.android.sdk.exception;

public class SessionMissingException extends SdkException {

    static final int CODE = 604;
    static final String MSG = "session missing";

    public SessionMissingException() {
        super(CODE, MSG);
    }

    public SessionMissingException(Throwable cause) {
        super(CODE, MSG, cause);
    }
}
