package com.minapp.android.sdk.push;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Strings;
import com.google.firebase.messaging.FirebaseMessaging;
import com.heytap.msp.push.HeytapPushManager;
import com.heytap.msp.push.mode.ErrorCode;
import com.huawei.hms.push.HmsMessaging;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.minapp.android.sdk.Assert;
import com.minapp.android.sdk.Const;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.Persistence;
import com.minapp.android.sdk.auth.Auth;
import com.minapp.android.sdk.exception.HttpException;
import com.minapp.android.sdk.exception.SessionMissingException;
import com.minapp.android.sdk.exception.TurnOnVivoPushException;
import com.minapp.android.sdk.exception.UninitializedException;
import com.minapp.android.sdk.exception.UnknowDeviceVendorException;
import com.minapp.android.sdk.model.PushConfig;
import com.minapp.android.sdk.model.PushMetaData;
import com.minapp.android.sdk.util.BsLog;
import com.minapp.android.sdk.util.Util;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;
import com.vivo.push.util.VivoPushException;
import com.xiaomi.mipush.sdk.MiPushClient;

import retrofit2.Response;

public class BsPushManager {

    private static final BsLog LOG = Log.get();
    static Class appReceiverClz = null;

    /**
     * 自动检测 vendor 并注册，如果不能识别 vendor 会抛出异常
     */
    @WorkerThread
    public static void registerPush() throws UninitializedException, SessionMissingException {
        Context ctx = Global.getApplication();
        if (ctx == null) throw new UninitializedException();
        DeviceVendor vendor = DeviceVendor.get(ctx);
        if (vendor == null) throw new UnknowDeviceVendorException();
        Assert.signIn();

        // 区分各个渠道注册推送，注册成功拿到 regId 后，post 给后端
        switch (vendor) {
            case MI:
                registerMiPush(ctx);
                break;

            case HUAWEI:
                registerHmsPush(ctx);
                break;

            case VIVO:
                registerVivoPush(ctx);
                break;

            case FLYME:
                registerFlymePush(ctx);
                break;

            case OPPO:
                registerOppoPush(ctx);
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
    @WorkerThread
    public static final void registerFCM(@NonNull Context ctx) throws SessionMissingException {
        Assert.signIn();
        parseAppReceiverClz(ctx);
        LOG.d("register fcm push success");

        OnCompleteListener<String> listener = new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isComplete()) {
                    if (task.isSuccessful()) {
                        String regId = task.getResult();
                        if (!Strings.isNullOrEmpty(regId)) {
                            LOG.d("fcm regId: %s", task.getResult());
                            BsPushManager.uploadPushMetaData(DeviceVendor.FCM, task.getResult());
                        }
                    }
                }
            }
        };
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(listener);
    }

    /**
     * 注册 oppo push
     * @param ctx
     */
    @WorkerThread
    public static void registerOppoPush(@NonNull Context ctx) throws SessionMissingException {
        Assert.signIn();
        PushConfig config = getConfig(DeviceVendor.OPPO);
        HeytapPushManager.init(ctx, true);
        HeytapPushManager.register(ctx, config.oppoAppKey, config.oppoAppSecret, new VivoPushRegisterCallbackAdapter() {
            @Override
            public void onRegister(int code, String regId) {
                switch (code) {
                    case ErrorCode.SUCCESS:
                        LOG.d("oppo regId: %s", regId);
                        uploadPushMetaData(DeviceVendor.OPPO, regId);
                        break;

                    default:
                        HeytapPushManager.getRegister();
                }
            }
        });
        parseAppReceiverClz(ctx);
        LOG.d("register oppo push success");
    }

    public static void requestNotificationPermissionForOppo() {
        HeytapPushManager.requestNotificationPermission();
    }

    /**
     * 注册 FLYME 推送
     * regId 在 {@link BsFlymeMessageReceiver#onRegisterStatus(Context, RegisterStatus)}
     * @param ctx
     */
    @WorkerThread
    public static void registerFlymePush(@NonNull Context ctx) throws SessionMissingException {
        Assert.signIn();
        PushConfig config = getConfig(DeviceVendor.FLYME);
        PushManager.register(ctx, config.flymeAppId, config.flymeAppKey);
        parseAppReceiverClz(ctx);
        LOG.d("register flyme push success");
    }

    /**
     * 注册 VIVO 推送
     * appId 和 appKey 是配置在 manifest.Application.MetaData 里
     * @param ctx
     */
    @WorkerThread
    public static void registerVivoPush(@NonNull Context ctx) throws SessionMissingException {
        Assert.signIn();
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
     * appId 和 appKey 是配置在 agc 配置文件里
     * regId 在 {@link BsHmsMessageService#onNewToken(String)}
     * @param ctx
     */
    @WorkerThread
    public static void registerHmsPush(@NonNull Context ctx) throws SessionMissingException {
        Assert.signIn();
        HmsMessaging.getInstance(ctx).setAutoInitEnabled(true);
        parseAppReceiverClz(ctx);
        LOG.d("register hms push success");
    }


    /**
     * 注册小米推送
     * @param context
     */
    @WorkerThread
    public static void registerMiPush(@NonNull Context context) throws SessionMissingException {
        Assert.signIn();
        parseAppReceiverClz(context);
        PushConfig config = getConfig(DeviceVendor.MI);
        MiPushClient.registerPush(context, config.miAppId, config.miAppKey);
        LOG.d("register mi push success");
        new RegIdLogger(context).start();
    }

    private static void parseAppReceiverClz(Context ctx) {
        appReceiverClz = PushUtil.parseAppReceiverClz(ctx);
        Assert.notNullState(appReceiverClz, "app push receiver");
    }

    private static PushConfig getConfig(DeviceVendor vendor) {
        try {
            return Global.httpApi().getPushConfig(vendor.name().toLowerCase()).execute().body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传推送配置到服务器
     * @param vendor
     * @param regId
     */
    @WorkerThread
    public static void uploadPushMetaData(@Nullable DeviceVendor vendor, @Nullable String regId) {
        if (vendor == null || Strings.isNullOrEmpty(regId) || !Auth.signedIn()) return;

        String installationId = Persistence.getString(Const.PUSH_INSTALLATION_ID);
        if (installationId == null) {
            installationId = Util.genInstallationId();
            Persistence.put(Const.PUSH_INSTALLATION_ID, installationId);
        }

        PushMetaData body = new PushMetaData();
        body.installationId = installationId;
        body.regId = regId;
        body.vendor = vendor.name().toLowerCase();

        // 暂时不做额外的保障，网络错误等导致上传失败，后续可以单独做个 service
        try {
            Response response;
            try {
                response = Global.httpApi().uploadPushMetaData(body).execute();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (!response.isSuccessful()) throw HttpException.valueOf(response);
            LOG.d("uploadPushMetaData success");
        } catch (Exception e) {
            LOG.e(e, "uploadPushMetaData fail");
        }
    }

}
