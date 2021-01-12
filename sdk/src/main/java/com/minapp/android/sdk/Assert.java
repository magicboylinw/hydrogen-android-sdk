package com.minapp.android.sdk;

import com.minapp.android.sdk.auth.Auth;
import com.minapp.android.sdk.exception.SessionMissingException;
import com.minapp.android.sdk.util.StringUtil;

public abstract class Assert {

    public static void signIn() throws SessionMissingException {
        if (!Auth.signedIn())
            throw new SessionMissingException();
    }

    public static void notNullState(Object obj, String name) throws IllegalArgumentException {
        if (obj == null)
            throw new IllegalStateException(String.format("%s can not be null", name));
    }

    public static void notNullPointer(Object obj, String name) throws NullPointerException {
        if (obj == null)
            throw new NullPointerException(String.format("%s can not be null", name));
    }

    public static void notNull(Object obj, String name) throws IllegalArgumentException {
        if (obj == null)
            throw new IllegalArgumentException(String.format("%s can not be null", name));
    }

    public static void notBlank(String value, String name) throws IllegalArgumentException {
        if (StringUtil.trimToNull(value) == null)
            throw new IllegalArgumentException(String.format("%s can not be blank", name));
    }

    public static void notNull(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
    }
}
