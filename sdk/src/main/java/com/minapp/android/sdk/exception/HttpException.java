package com.minapp.android.sdk.exception;

import com.google.gson.JsonObject;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.model.ErrorResp;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpException extends RuntimeException {

    private final int code;
    private final String errorMsg;

    public static HttpException valueOf(Response resp) {
        String message = "";
        JsonObject json = null;

        try {
            String error = new String(resp.errorBody().bytes(), StandardCharsets.UTF_8).trim();
            json = Global.gson().fromJson(error, JsonObject.class);
        } catch (Exception e) {}

        if (json != null) {
            try {
                message = json.get("error_msg").getAsString();
            } catch (Exception e) {}

            if (message == null || message.isEmpty()) {
                try {
                    message = json.get("message").getAsString();
                } catch (Exception e) {}
            }
        }

        message = message == null ? "" : message;
        return new HttpException(resp.code(), message);
    }

    public HttpException(int code) {
        this(code, null, null);
    }

    public HttpException(int code, String errorMsg) {
        this(code, errorMsg, null);
    }

    public HttpException(int code, String errorMsg, Throwable cause) {
        super(String.format("status code %s\n%s", code, errorMsg), cause);
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
