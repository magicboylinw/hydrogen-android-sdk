package com.ifanr.activitys;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.google.common.base.Strings;
import com.minapp.android.sdk.BaaS;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.auth.Auth;
import com.minapp.android.sdk.push.BsPushManager;
import com.minapp.android.sdk.push.Log;
import com.minapp.android.sdk.util.BsLog;

public class App extends Application {

    private static final BsLog LOG = Log.get();

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        registerPush();
    }

    private void registerPush() {
        Workers.post(new Runnable() {
            @Override
            public void run() {
                try {
                    BaaS.init(Const.BAAS_CLIENT_ID, App.this);
                    signIn();
                    BsPushManager.registerPush();
                } catch (Exception e) {
                    LOG.e(e, "register push fail");
                }
            }
        });
    }

    private void signIn() throws Exception {
        String email = "adf0q48nva=qd9ms@gmail.com";
        String pwd = "utnbv9037en";
        try {
            Auth.signUpWithEmail(email, pwd);
        } catch (Exception ignored) {}
        Auth.signInWithEmail(email, pwd);
    }

    private void createNotificationChannel() {
        Global.post(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationManager nm =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationChannelGroup group = new NotificationChannelGroup(
                            "9", "测试");
                    NotificationChannel channel = new NotificationChannel(
                            "99", "推送测试", NotificationManager.IMPORTANCE_HIGH);
                    channel.setGroup(group.getId());

                    nm.createNotificationChannelGroup(group);
                    nm.createNotificationChannel(channel);
                }
            }
        });
    }
}
