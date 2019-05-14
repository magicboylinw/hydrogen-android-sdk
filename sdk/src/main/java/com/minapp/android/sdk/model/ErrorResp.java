package com.minapp.android.sdk.model;

import com.google.gson.annotations.SerializedName;

public class ErrorResp extends StatusResp {

    @SerializedName("error_msg")
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
