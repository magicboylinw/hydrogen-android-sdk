package com.minapp.android.sdk.auth.model;

import com.google.gson.annotations.SerializedName;

public class UpdateUserReq {

    /**
     * username : wefw
     * email : aaa@bbb
     * password : adwfqwiekf
     * new_password : isdkf asdf
     */

    @SerializedName("username")
    private String username;        // 用户名 （不区分大小写）
    @SerializedName("email")
    private String email;           // 用户邮箱 （不区分大小写）
    @SerializedName("password")
    private String password;        // 用户密码 （若有提交 new_password 则为必填）
    @SerializedName("new_password")
    private String newPassword;     // 新用户密码

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
