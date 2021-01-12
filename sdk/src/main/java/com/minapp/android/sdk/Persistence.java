package com.minapp.android.sdk;

import android.content.Context;
import android.content.SharedPreferences;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class Persistence {

    private static SharedPreferences SP = null;

    public static @Nullable SharedPreferences get() {
        if (SP == null) {
            synchronized (Persistence.class) {
                if (SP == null) {
                    Context ctx = Global.getApplication();
                    if (ctx != null) {
                        SP = ctx.getSharedPreferences(Const.SP_NAME, Context.MODE_PRIVATE);
                    }
                }
            }
        }
        return SP;
    }

    public static void put(@Nonnull String key, @Nullable Object value) {
        SharedPreferences sp = get();
        if (sp == null) return;

        if (value == null) {
            sp.edit().remove(key).apply();
            return;
        }

        SharedPreferences.Editor editor = sp.edit();
        if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        }
        editor.apply();
    }

    public static @Nullable String getString(@Nonnull String key) {
        SharedPreferences sp = get();
        if (sp == null) return null;
        return sp.getString(key, null);
    }

    public static float getFloat(@Nonnull String key) {
        SharedPreferences sp = get();
        if (sp == null) return 0;
        return sp.getFloat(key, 0);
    }

    public static int getInt(@Nonnull String key) {
        SharedPreferences sp = get();
        if (sp == null) return 0;
        return sp.getInt(key, 0);
    }

    public static long getLong(@Nonnull String key) {
        SharedPreferences sp = get();
        if (sp == null) return 0;
        return sp.getLong(key, 0);
    }

    public static boolean getBoolean(@Nonnull String key) {
        SharedPreferences sp = get();
        if (sp == null) return false;
        return sp.getBoolean(key, false);
    }
}
