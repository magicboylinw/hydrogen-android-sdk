package com.minapp.android.sdk.exception;

import java.io.IOException;

public class EmptyResponseException extends IOException {

    public static final String MSG = "http response body is empty";

    public EmptyResponseException() {
        super(MSG);
    }

    public EmptyResponseException(String message) {
        super(message);
    }

    public EmptyResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyResponseException(Throwable cause) {
        super(MSG, cause);
    }
}
