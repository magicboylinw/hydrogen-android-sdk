package com.minapp.android.sdk;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置
 */
public class Config {
    private Config() {}

    public static final String CLIENT_ID = "CLIENT_ID";

    private static final Map<String, Object> CONFIG = new HashMap<>();

    public static @Nullable String getClientId() {
        return (String) CONFIG.get(CLIENT_ID);
    }

    public static void setClientId(String clientId) {
        CONFIG.put(CLIENT_ID, clientId);
    }
}
