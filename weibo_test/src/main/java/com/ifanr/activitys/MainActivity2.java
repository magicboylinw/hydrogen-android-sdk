package com.ifanr.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.minapp.android.sdk.auth.Auth;
import com.minapp.android.sdk.auth.CurrentUser;
import com.minapp.android.sdk.util.BaseCallback;
import com.minapp.android.sdk.weibo.SignInCallback;
import com.minapp.android.sdk.weibo.WeiboComponent;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "MainActivity2";

    private SsoHandler handler = null;
    private SignInCallback wbSignInCb = new SignInCallback() {
        @Override
        public void onSuccess() {
            CurrentUser user = Auth.currentUserWithoutData();
            Long userId = user != null ? user.getUserId() : -1;
            Toast.makeText(MainActivity2.this,
                    String.format("登录成功（%s）", userId.toString()), Toast.LENGTH_SHORT).show();
            Log.d(TAG, user.toString());
        }

        @Override
        public void onCancel() {
            Toast.makeText(MainActivity2.this, "取消登录", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(@NonNull Throwable tr) {
            Toast.makeText(MainActivity2.this, String.format("登录失败：%s", tr.getMessage()),
                    Toast.LENGTH_SHORT).show();
            Log.e(TAG, tr.getMessage(), tr);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findViewById(R.id.wb).setOnClickListener(v -> handler = WeiboComponent.signIn(this, wbSignInCb));

        findViewById(R.id.asso).setOnClickListener(v -> {
            Auth.logout();
            Auth.signInAnonymousInBackground(new BaseCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    handler = WeiboComponent.associationWithWeibo(
                            MainActivity2.this, wbSignInCb);
                }

                @Override
                public void onFailure(Throwable e) {}
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (handler != null) {
            handler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}