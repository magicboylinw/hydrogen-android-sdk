package com.minapp.android.sdk.push;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.heytap.msp.push.HeytapPushManager;
import com.heytap.msp.push.callback.ICallBackResultService;
import com.heytap.msp.push.mode.ErrorCode;
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

            case OPPO:
                registerOppoPush(config.oppoAppKey, config.oppoAppSecret, ctx);
                break;

            case FCM:
                registerFCM(ctx);
                break;
        }
    }

    /**
     * 注册 FCM
     * 实际上 FCM 不需要主动注册，配置好 google-services.json 和 google services plugin 后，FCM 会主动注册
     * 这里做一些其他工作
     *
     * TODO 目前 FCM 只能在前台才能接收到消息，而且不是通知栏消息（不能把 app 拉到前台）待后端接入 fcm admin sdk 后看看
     */
    public static final void registerFCM(@NonNull Context ctx) {
        parseAppReceiverClz(ctx);
        new RegIdLogger(ctx).start();
        LOG.d("register fcm push success");
    }

    /**
     * 注册 oppo push
     * @param appKey
     * @param appSecret
     * @param ctx
     */
    public static final void registerOppoPush(
            @NonNull String appKey, @NonNull String appSecret, @NonNull Context ctx) {
        Assert.notNull(appKey, "appKey");
        Assert.notNull(appSecret, "appSecret");
        Assert.notNull(ctx, "Context");

        HeytapPushManager.init(ctx, true);
        HeytapPushManager.register(ctx, appKey, appSecret, new VivoPushRegisterCallbackAdapter() {
            @Override
            public void onRegister(int code, String regId) {
                switch (code) {
                    case ErrorCode.SUCCESS:
                        LOG.d("oppo push regId:%s", regId);
                        break;

                    default:
                        HeytapPushManager.getRegister();
                }
            }
        });
        parseAppReceiverClz(ctx);
        LOG.d("register oppo push success");
    }

    public static final void requestNotificationPermissionForOppo() {
        HeytapPushManager.requestNotificationPermission();
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
