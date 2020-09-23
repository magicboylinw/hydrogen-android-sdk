package com.ifanr.activitys;

import android.content.Context;

import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.util.BsLog;

import javax.annotation.Nullable;

public class KeyStore {

    private static KeyStore INSTANCE = null;

    public static KeyStore get(Context ctx) {
        if (INSTANCE == null) {
            synchronized (KeyStore.class) {
                if (INSTANCE == null) {
                    INSTANCE = new KeyStore(ctx);
                }
            }
        }
        return INSTANCE;
    }

    @Nullable
    private final JsonStore store;

    private KeyStore(Context ctx) {
        store = Util.readJsonFromAssets(ctx, "keystore.json", JsonStore.class);
    }

    public @Nullable String getMiAppId() {
        return store != null ? store.miAppId : null;
    }

    public @Nullable String getMiAppKey() {
        return store != null ? store.miAppKey : null;
    }

    public static class JsonStore {

        @SerializedName("miAppId")
        public String miAppId = null;

        @SerializedName("miAppKey")
        public String miAppKey = null;
    }
}
