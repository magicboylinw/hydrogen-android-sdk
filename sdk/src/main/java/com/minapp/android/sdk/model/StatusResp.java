package com.minapp.android.sdk.model;

import com.google.gson.annotations.SerializedName;

public class StatusResp {

    public static final String OK = "ok";

    @SerializedName("status")
    private String status;

    public boolean isOk() {
        return OK.equalsIgnoreCase(status);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
