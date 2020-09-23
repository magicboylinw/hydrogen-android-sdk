package com.ifanr.activitys;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.annotation.NonNull;

public abstract class Workers {

    private static Handler HANDLER = null;

    private static Handler getHandler() {
        if (HANDLER == null) {
            synchronized (Workers.class) {
                if (HANDLER == null) {
                    HandlerThread thread = new HandlerThread("worker");
                    thread.start();
                    HANDLER = new Handler(thread.getLooper());
                }
            }
        }
        return HANDLER;
    }

    public static void postDelayed(@NonNull Runnable r, long delayMillis) {
        getHandler().postDelayed(r, delayMillis);
    }

    public static void post(@NonNull Runnable r) {
        getHandler().post(r);
    }
}
