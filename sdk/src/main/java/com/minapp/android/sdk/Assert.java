package com.minapp.android.sdk;

public abstract class Assert {
    public static void notNull(Object obj, String name) throws NullPointerException {
        if (obj == null)
            throw new NullPointerException(String.format("%s can not be null", name));
    }
}
