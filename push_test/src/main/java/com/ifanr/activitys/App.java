package com.ifanr.activitys;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.common.base.Strings;
import com.minapp.android.sdk.Const;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.push.BsPushManager;
import com.minapp.android.sdk.push.HmsReceiverActivity;
import com.minapp.android.sdk.push.Log;
import com.minapp.android.sdk.push.PushConfiguration;
import com.minapp.android.sdk.util.BsLog;
import com.xiaomi.mipush.sdk.MiPushClient;

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
                    BsPushManager.registerPush(readPushConfig(), App.this);
                } catch (Exception e) {
                    LOG.e(e, "register push fail");
                }
            }
        });
    }

    private PushConfiguration readPushConfig() throws PackageManager.NameNotFoundException {
        Bundle meta = getPackageManager()
                .getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA)
                .metaData;
        PushConfiguration config = new PushConfiguration();
        config.flymeAppId = String.valueOf(meta.getInt("flymeAppId"));
        config.flymeAppKey = meta.getString("flymeAppKey");
        config.oppoAppKey = meta.getString("oppoAppKey");
        config.oppoAppSecret = meta.getString("oppoAppSecret");
        return config;
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
