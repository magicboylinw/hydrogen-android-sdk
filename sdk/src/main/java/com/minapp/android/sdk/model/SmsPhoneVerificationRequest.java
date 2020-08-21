package com.minapp.android.sdk.model;

import com.google.gson.annotations.SerializedName;

public class SmsPhoneVerificationRequest {

    @SerializedName("code")
    public String code;

    public SmsPhoneVerificationRequest(String code) {
        this.code = code;
    }
}
