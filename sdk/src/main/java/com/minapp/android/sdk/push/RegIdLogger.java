package com.minapp.android.sdk.push;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Strings;
import com.google.firebase.messaging.FirebaseMessaging;
import com.minapp.android.sdk.Const;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.Persistence;
import com.minapp.android.sdk.exception.EmptyResponseException;
import com.minapp.android.sdk.model.PushMetaData;
import com.minapp.android.sdk.util.BsLog;
import com.minapp.android.sdk.util.Util;
import com.vivo.push.PushClient;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.io.IOException;
import java.lang.ref.WeakReference;

import retrofit2.Response;

/**
 * 拿到各个渠道的 push regId 推送给后端
 */
class RegIdLogger extends Thread {

    private WeakReference<Context> ctxRef;
    private BsLog log = Log.get();

    RegIdLogger(Context ctx) {
        ctxRef = new WeakReference<>(ctx);
    }

    @Override
    public void run() {
        if (ctxRef == null) return;
        Context ctx = ctxRef.get();
        if (ctx == null) return;
        DeviceVendor vendor = DeviceVendor.get(ctx);
        if (vendor == null) return;

        boolean exit = false;
        while (!exit) {
            String regId = null;
            switch (vendor) {

                case MI:
                    regId = MiPushClient.getRegId(ctx);
                    BsPushManager.uploadPushMetaData(DeviceVendor.MI, regId);
                    break;

                case VIVO:
                    regId = PushClient.getInstance(ctx).getRegId();
                    BsPushManager.uploadPushMetaData(DeviceVendor.VIVO, regId);
                    break;

                default:
                    exit = true;
            }

            if (!Strings.isNullOrEmpty(regId)) {
                log.d("%s regId: %s", vendor.name(), regId);
                ctxRef.clear();
                ctxRef = null;
                exit = true;

            } else {
                try {
                    Thread.sleep(3 * 1000);
                } catch (InterruptedException ignored) {}
            }
        }
    }

}
