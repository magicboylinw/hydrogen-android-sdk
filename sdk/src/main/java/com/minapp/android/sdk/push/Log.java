package com.minapp.android.sdk.push;

import com.minapp.android.sdk.util.BsLog;

public abstract class Log {

    private static BsLog INSTANCE = null;

    public static final BsLog get() {
        if (INSTANCE == null) {
            synchronized (Log.class) {
                if (INSTANCE == null) {
                    INSTANCE = BsLog.get("BsPush");
                }
            }
        }
        return INSTANCE;
    }
}
