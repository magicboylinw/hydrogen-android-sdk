package com.minapp.android.sdk.model;

import com.google.gson.annotations.SerializedName;

public class SendSmsCodeReq {

    @SerializedName("phone")
    private String phone;

    public SendSmsCodeReq() {
    }

    public SendSmsCodeReq(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
