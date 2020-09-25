package com.ifanr.activitys;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.common.base.Strings;
import com.minapp.android.sdk.Const;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.push.BsPushManager;
import com.minapp.android.sdk.push.HmsReceiverActivity;
import com.minapp.android.sdk.push.Log;
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
                    KeyStore ks = KeyStore.get(App.this);
                    BsPushManager.registerPush(ks.toPushConfiguration(), App.this);
                } catch (Exception e) {
                    LOG.e(e, "register push fail");
                }
            }
        });
    }
}
