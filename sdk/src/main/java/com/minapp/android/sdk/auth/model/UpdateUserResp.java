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
}

