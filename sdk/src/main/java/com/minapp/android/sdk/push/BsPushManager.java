package com.minapp.android.sdk.push;

import android.content.Context;

import androidx.annotation.NonNull;

import com.huawei.hms.push.HmsMessaging;
import com.meizu.cloud.pushsdk.PushManager;
import com.minapp.android.sdk.Assert;
import com.minapp.android.sdk.exception.PushDeviceUnsupportedException;
import com.minapp.android.sdk.exception.TurnOnVivoPushException;
import com.minapp.android.sdk.util.BsLog;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;
import com.vivo.push.util.VivoPushException;
import com.xiaomi.mipush.sdk.MiPushClient;

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

            case VIVO:
                registerVivoPush(ctx);
                break;

            case FLYME:
                registerFlymePush(config.flymeAppId, config.flymeAppKey, ctx);
                break;
        }
    }

    /**
     * 注册 FLYME 推送
     * @param ctx
     */
    public static final void registerFlymePush(
            @NonNull String appId, @NonNull String appKey, @NonNull Context ctx) {
        Assert.notNull(appId, "appId");
        Assert.notNull(appKey, "appKey");
        Assert.notNull(ctx, "ctx");

        DeviceVendor vendor = DeviceVendor.get(ctx);
        if (vendor != DeviceVendor.FLYME)
            throw new PushDeviceUnsupportedException(vendor);

        PushManager.register(ctx, appId, appKey);
        parseAppReceiverClz(ctx);
        LOG.d("register flyme push success");
    }

    /**
     * 注册 VIVO 推送
     * @param ctx
     */
    public static final void registerVivoPush(@NonNull Context ctx) {
        Assert.notNull(ctx, "Context");
        PushClient client = PushClient.getInstance(ctx);
        Assert.notNull(client, "PushClient");
        try {
            client.checkManifest();
        } catch (VivoPushException e) {
            throw new RuntimeException(e);
        }
        client.initialize();
        client.turnOnPush(new IPushActionListener() {
            @Override
            public void onStateChanged(int i) {
                if (i == 0) {
                    LOG.d("turn on vivo push success");
                    new RegIdLogger(ctx).start();
                } else {
                    LOG.e(new TurnOnVivoPushException(i));
                }
            }
        });
        parseAppReceiverClz(ctx);
        LOG.d("register vivo push success");
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
        new RegIdLogger(context).start();
        LOG.d("register mi push success");
    }

    private static void parseAppReceiverClz(Context ctx) {
        appReceiverClz = PushUtil.parseAppReceiverClz(ctx);
        Assert.notNullState(appReceiverClz, "app push receiver");
    }

}
