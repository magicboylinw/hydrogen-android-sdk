package com.minapp.android.example;

import com.minapp.android.sdk.auth.Auth;
import com.minapp.android.sdk.auth.model.SignInWithPhoneRequest;
import com.minapp.android.sdk.user.User;
import com.minapp.android.sdk.util.BaseCallback;

public class Test {
    public static void main(String[] args) {
        SignInWithPhoneRequest request = new SignInWithPhoneRequest("15023449384", "123456");
        Auth.signInWithPhoneInBackground(request, new BaseCallback<User>() {
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
