package com.minapp.android.sdk.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.minapp.android.sdk.Assert;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.util.BsLog;
import com.minapp.android.sdk.util.Util;

abstract class PushUtil {

    static final String EXTRA_MESSAGE = "EXTRA_BAAS_PUSH_MESSAGE";

    private static BsLog LOG = Log.get();

    /**
     * 将各个推送渠道收到的文本内容发送给 {@link BaseBsPushReceiver}
     * @param message
     * @param ctx
     */
    static void broadcastMessage(@NonNull String message, @NonNull Context ctx) {
        Assert.notNull(message, "message");
        Assert.notNull(ctx, "Context");
        try {
            broadcastMessage(Message.parse(message), ctx);
        } catch (Exception e) {
            LOG.e(e);
        }
    }

    /**
     * 将各个推送渠道收到的文本内容发送给 {@link BaseBsPushReceiver}
     * @param message
     * @param ctx
     */
    static void broadcastMessage(Message message, Context ctx) {

        // 华为推送用了中间层 activity 打开 push receiver，此时 appReceiverClz 有可能为空
        // 比如 register 被放在 main activity，或者被放在 worker thread
        Class receiverClz = BsPushManager.appReceiverClz != null ? BsPushManager.appReceiverClz
                : parseAppReceiverClz(ctx);

        if (message != null && ctx != null && receiverClz != null) {
            Intent intent = new Intent(ctx, receiverClz);
            intent.putExtra(EXTRA_MESSAGE, message);
            ctx.sendBroadcast(intent);
        }
    }

    @Nullable
    static <T extends BaseBsPushReceiver> Class<T> parseAppReceiverClz(Context ctx) {
        ActivityInfo[] receivers = null;
        try {
            receivers = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_RECEIVERS).receivers;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
        if (receivers == null || receivers.length == 0)
            return null;

        for (ActivityInfo receiver : receivers) {
            try {
                Class clz = Class.forName(receiver.name);
                if (BaseBsPushReceiver.class.isAssignableFrom(clz)) {
                    return clz;
                }
            } catch (Throwable ignored) {}
        }
        return null;
    }
}
