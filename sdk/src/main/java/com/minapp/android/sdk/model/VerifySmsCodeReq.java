package com.minapp.android.sdk.model;

import com.google.gson.annotations.SerializedName;

public class VerifySmsCodeReq {

    @SerializedName("phone")
    private String phone;

    @SerializedName("code")
    private String verificationCode;

    public VerifySmsCodeReq() {
    }

    public VerifySmsCodeReq(String phone, String verificationCode) {
        this.phone = phone;
        this.verificationCode = verificationCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
