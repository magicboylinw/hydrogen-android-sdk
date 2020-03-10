package com.minapp.android.sdk.auth.model;

import com.google.gson.annotations.SerializedName;

public class ThirdPartySignInReq {

    /**
     * 微信登录、微博登录拿到的 token
     */
    @SerializedName("auth_token")
    public String authToken;

    /**
     * 更新用户信息的方式，选填
     * overwrite（覆盖）
     * setnx（值不存在时设置）
     * false（不更新）
     */
    @SerializedName("update_userprofile")
    public String updateUserprofile;

    public ThirdPartySignInReq(String authToken) {
        this.authToken = authToken;
    }

    public ThirdPartySignInReq(String authToken, String updateUserprofile) {
        this.authToken = authToken;
        this.updateUserprofile = updateUserprofile;
    }
}
