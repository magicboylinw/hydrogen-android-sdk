package com.minapp.android.sdk.push;

import android.content.Context;

import com.google.common.base.Strings;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.util.BsLog;
import com.vivo.push.PushClient;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.lang.ref.WeakReference;

/**
 * 打印各个渠道的 push regId
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
                    break;

                case VIVO:
                    regId = PushClient.getInstance(ctx).getRegId();
                    break;

                default:
                    exit = true;
            }

            if (!Strings.isNullOrEmpty(regId)) {
                log.d("%s push regId: %s", vendor.name(), regId);
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
