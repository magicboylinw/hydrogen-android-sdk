package com.minapp.android.sdk.auth.model;

import com.google.gson.annotations.SerializedName;

public class ThirdPartySignInReq {

    /**
     * 微信登录、微博登录拿到的 token
     */
    @SerializedName("auth_token")
    public String authToken;

    public ThirdPartySignInReq(String authToken) {
        this.authToken = authToken;
    }
}
