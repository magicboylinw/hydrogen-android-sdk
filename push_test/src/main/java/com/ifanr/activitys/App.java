package com.ifanr.activitys;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
        return config;
    }
}
