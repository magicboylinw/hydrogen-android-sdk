package com.minapp.android.sdk.push;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.common.base.Strings;
import com.huawei.hms.push.HmsMessaging;
import com.minapp.android.sdk.Assert;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.util.BsLog;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.lang.ref.WeakReference;

public class BsPushManager {

    private static final BsLog LOG = Log.get();
    static Class appReceiverClz = null;


    public static void registerPush(@NonNull PushConfiguration config, @NonNull Context ctx) {
        Assert.notNull(config, "PushConfiguration");
        Assert.notNull(ctx, "Context");

        DeviceVendor vendor = DeviceVendor.get(ctx);
        if (vendor == null) {
            throw new IllegalStateException("unknown device vendor");
        }

        switch (vendor) {
            case MI:
                registerMiPush(ctx, config.miAppId, config.miAppKey);
                break;

            case HUAWEI:
                registerHmsPush(ctx);
                break;
        }
    }

    /**
     * 注册华为推送
     * @param ctx
     */
    public static final void registerHmsPush(@NonNull Context ctx) {
        Assert.notNull(ctx, "Context");
        HmsMessaging.getInstance(ctx).setAutoInitEnabled(true);
        parseAppReceiverClz(ctx);
        LOG.d("register hms push success");
    }


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
        Global.postDelayed(new LogMiRegId(context), 1000 * 3);
        LOG.d("register mi push success");
    }

    private static void parseAppReceiverClz(Context ctx) {
        appReceiverClz = PushUtil.parseAppReceiverClz(ctx);
        Assert.notNullState(appReceiverClz, "app push receiver");
    }


    /**
     * 打印 mi push regId
     */
    private static class LogMiRegId implements Runnable {

        private WeakReference<Context> ctxRef;

        LogMiRegId(Context ctx) {
            ctxRef = new WeakReference(ctx);
        }

        @Override
        public void run() {
            if (ctxRef == null)
                return;
            Context ctx = ctxRef.get();
            if (ctx == null)
                return;

            String regId = MiPushClient.getRegId(ctx);
            if (!Strings.isNullOrEmpty(regId)) {
                LOG.d("mi push regId: %s", regId);
                ctxRef.clear();
                ctxRef = null;

            } else {
                Global.postDelayed(this, 1000 * 3);
            }
        }
    }

    public static final class PushConfiguration{
        public String miAppId;
        public String miAppKey;
    }
}
