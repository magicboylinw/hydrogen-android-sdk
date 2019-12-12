package com.minapp.android.sdk.auth.model;

import com.google.gson.annotations.SerializedName;

/**
 * 注册返回
 * {
 *      "status": "ok",
 *      "token": "i4i1kcs0pfyb658vdtru88cqud19svub",
 *      "expires_in": 2592000,
 *      "user_id": 122137745887618,
 *      "message": "Authentication succeeded.",
 *      "user_info": {
 *          "avatar": "...",
 *          "nickname": "NICKNAME"
 *      }
 * }
 *
 * 关联返回：
 * {
 *      "user_id": 122136891146696,
 *      "status": "ok",
 *      "message": "Association succeeded.",
 *      "user_info": {
 *          "avatar": "...",
 *          "nickname": "NICKNAME"
 *      }
 * }
 *
 */
public class ThirdPartySignInResp {

    public static final String STATUS_OK = "ok";

    @SerializedName("status")
    public String status;

    @SerializedName("token")
    public String token;

    @SerializedName("expires_in")
    public long expiresIn;

    @SerializedName("user_id")
    public long userId;

    @SerializedName("message")
    public String message;

    @SerializedName("user_info")
    public UserInfo userInfo;

    public boolean isOk() {
        return STATUS_OK.equalsIgnoreCase(status);
    }

    public class UserInfo {

        @SerializedName("avatar")
        public String avatar;

        @SerializedName("nickname")
        public String nickname;
    }

}
