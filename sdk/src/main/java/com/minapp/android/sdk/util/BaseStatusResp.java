package com.minapp.android.sdk.util;

import com.google.gson.annotations.SerializedName;

public class BaseStatusResp {

    public static final String OK = "ok";

    @SerializedName("status")
    private String status;

    public boolean isOk() {
        return OK.equalsIgnoreCase(status);
    }

    public static String getOK() {
        return OK;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
