package com.ifanr.activitys;

import android.app.Application;
import android.content.Context;

import com.google.common.base.Strings;
import com.minapp.android.sdk.Const;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.push.BsPushManager;
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
                    BsPushManager.registerMiPush(App.this, ks.getMiAppId(), ks.getMiAppKey());
                    Workers.post(new LogRegId());
                } catch (Exception e) {
                    LOG.e(e, "register push fail");
                }
            }
        });
    }

    /**
     * 打印 mi push regId
     */
    private class LogRegId implements Runnable {
        @Override
        public void run() {
            String regId = MiPushClient.getRegId(App.this);
            if (!Strings.isNullOrEmpty(regId)) {
                LOG.d("mi push regId:%s", regId);
            } else {
                Workers.postDelayed(this, 1000 * 3);
            }
        }
    }
}
