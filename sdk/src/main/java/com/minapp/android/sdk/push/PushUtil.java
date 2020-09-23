package com.minapp.android.sdk.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.util.Util;

public abstract class PushUtil {

    public static final String ACTION_PUSH = "com.minapp.cloud.push";
    public static final String EXTRA_MESSAGE = "EXTRA_BAAS_PUSH_MESSAGE";

    public static void broadcastMessage(
            Message message, Class<? extends BroadcastReceiver> clz, Context ctx) {
        if (message != null && ctx != null) {
            Intent intent = new Intent(ctx, clz);
            intent.putExtra(EXTRA_MESSAGE, message);
            ctx.sendBroadcast(intent);
        }
    }

}
