package com.minapp.android.sdk.auth.model;

import com.google.gson.annotations.SerializedName;

public class SignInWithPhoneRequest {

    /**
     * 用户手机号
     */
    @SerializedName("phone")
    public String phone;

    /**
     * 短信验证码
     */
    @SerializedName("code")
    public String code;

    /**
     * 手机号查询不到用户时，是否允许创建用户，默认允许
     */
    @SerializedName("create_user")
    public Boolean createUser;

    public SignInWithPhoneRequest() {}

    public SignInWithPhoneRequest(String phone, String code) {
        this.phone = phone;
        this.code = code;
    }
}
