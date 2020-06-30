package com.minapp.android.example;

import androidx.annotation.Nullable;

import com.minapp.android.sdk.auth.Auth;
import com.minapp.android.sdk.auth.model.SignInByPhoneRequest;
import com.minapp.android.sdk.auth.model.UpdateUserReq;
import com.minapp.android.sdk.user.User;
import com.minapp.android.sdk.util.BaseCallback;
import com.minapp.android.sdk.wechat.AssociationCallback;
import com.minapp.android.sdk.wechat.AssociationType;
import com.minapp.android.sdk.wechat.WechatComponent;

public class Test {
    public static void main(String[] args) {
        SignInByPhoneRequest request = new SignInByPhoneRequest("15023449384", "123456");
        Auth.signInByPhoneInBackground(request, new BaseCallback<User>() {
            @Override
            public void onSuccess(User user) {
                // 登录成功
            }

            @Override
            public void onFailure(Throwable e) {
                // 登录失败
            }
        });
    }
}
