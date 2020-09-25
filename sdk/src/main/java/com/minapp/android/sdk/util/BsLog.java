package com.minapp.android.sdk.util;

import android.util.Log;

import androidx.annotation.NonNull;

import com.minapp.android.sdk.Assert;

public class BsLog {

    public static final BsLog get(@NonNull String tag) {
        return new BsLog(tag);
    }

    private String tag;

    private BsLog(@NonNull String tag) {
        Assert.notNull(tag, "tag");
        this.tag = tag;
    }

    public void v(String msg, Object... args) {
        Log.v(tag, String.format(msg, args));
    }

    public void v(String msg) {
        Log.v(tag, msg);
    }

    public void d(String msg, Object... args) {
        Log.d(tag, String.format(msg, args));
    }

    public void d(String msg) {
        Log.d(tag, msg);
    }

    public void i(String msg, Object... args) {
        Log.i(tag, String.format(msg, args));
    }

    public void i(String msg) {
        Log.i(tag, msg);
    }

    public void w(String msg, Object... args) {
        Log.w(tag, String.format(msg, args));
    }

    public void w(String msg) {
        Log.w(tag, msg);
    }

    public void e(Throwable tr, String msg, Object... args) {
        Log.e(tag, String.format(msg, args), tr);
    }

    public void e(Throwable tr) {
        Log.e(tag, tr.getMessage() != null ? tr.getMessage() : "");
    }
}
