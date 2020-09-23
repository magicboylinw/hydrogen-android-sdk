package com.ifanr.activitys;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.common.io.CharStreams;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.minapp.android.sdk.Global;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.annotation.Nullable;

public abstract class Util {

    public static @Nullable String readStringFromAssets(
            @NonNull Context ctx, @NonNull String filePath) {
        InputStream is = null;
        try {
            is = ctx.getAssets().open(filePath);
            return CharStreams.toString(new InputStreamReader(is, Const.CHARSET_UTF8));
        } catch (Throwable tr) {
            try {
                is.close();
            } catch (Throwable ignored) {}
            return null;
        }
    }

    public static @Nullable <T> T readJsonFromAssets(
            @NonNull Context ctx, @NonNull String filePath, Class<T> clz) {
        try {
            return Global.gson().fromJson(readStringFromAssets(ctx, filePath), clz);
        } catch (Throwable tr) {
            return null;
        }
    }

}
