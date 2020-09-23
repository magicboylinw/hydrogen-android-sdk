package com.minapp.android.sdk.push;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import com.minapp.android.sdk.Assert;
import com.minapp.android.sdk.util.BsLog;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushMessage;

public class BsPushManager {

    private static final BsLog LOG = Log.get();
    static Class appReceiverClz = null;

    /**
     * 注册小米推送
     * @param context
     * @param miAppId   小米 app id
     * @param miAppKey  小米 app key
     */
    public static final void registerMiPush(
            @NonNull Context context, @NonNull String miAppId, @NonNull String miAppKey) {
        Assert.notNull(context, "context");
        Assert.notBlank(miAppId, "miAppId");
        Assert.notBlank(miAppKey, "miAppKey");
        parseAppReceiverClz(context);

        MiPushClient.registerPush(context, miAppId, miAppKey);
        LOG.d("register mi push success");
    }


    private static void parseAppReceiverClz(Context ctx) {
        ActivityInfo[] receivers = null;
        try {
            receivers = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_RECEIVERS).receivers;
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException("app push receiver not found", e);
        }

        for (ActivityInfo receiver : receivers) {
            try {
                Class clz = Class.forName(receiver.name);
                if (BaseBsPushReceiver.class.isAssignableFrom(clz)) {
                    appReceiverClz = clz;
                    break;
                }
            } catch (Throwable tr) {
                LOG.e(tr, "exception in parse app push receiver");
            }
        }
        Assert.notNullState(appReceiverClz, "app push receiver");
        LOG.d("find app receiver:%s", appReceiverClz.getCanonicalName());
    }

}
