package com.minapp.android.sdk.auth.model;

import com.google.gson.annotations.SerializedName;

public class UpdateUserResp {


    /**
     * username : wefw
     * email : aaa@bbb
     * email_verified : false
     */

    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("email_verified")
    private Boolean emailVerified;
    @SerializedName("phone")
    private String phone;
    @SerializedName("phone_verified")
    private Boolean phoneVerified;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(Boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }
}

