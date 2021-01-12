package com.minapp.android.sdk.util;

import android.app.Application;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.minapp.android.sdk.Assert;
import com.minapp.android.sdk.Const;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.database.Record;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

public abstract class Util {

    private static final String[] POINTER_FEATURE = new String[]{
            Record.ID, Record.TABLE
    };

    /**
     * copy from cn.leancloud.AVInstallation#genInstallationId
     * @return
     */
    public static @Nullable String genInstallationId() {
        Application app = Global.getApplication();
        if (app == null) return null;
        String pkg = app.getPackageName();
        String addition = UUID.randomUUID().toString();
        return MD5.computeMD5(pkg + addition);
    }


    /**
     * copy from:
     * retrofit2.Utils#getParameterUpperBound(int, java.lang.reflect.ParameterizedType)
     * @param index
     * @param type
     * @return
     */
    public static Type getParameterUpperBound(int index, ParameterizedType type) {
        Type[] types = type.getActualTypeArguments();
        if (index < 0 || index >= types.length) {
            throw new IllegalArgumentException(
                    "Index " + index + " not in range [0," + types.length + ") for " + type);
        }
        Type paramType = types[index];
        if (paramType instanceof WildcardType) {
            return ((WildcardType) paramType).getUpperBounds()[0];
        }
        return paramType;
    }

    /**
     * copy from:
     * retrofit2.Utils#getRawType(java.lang.reflect.Type)
     * @param type
     * @return
     */
    public static @Nonnull Class<?> getRawType(Type type) {
        Assert.notNull(type);

        if (type instanceof Class<?>) {
            // Type is a normal class.
            return (Class<?>) type;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            // I'm not exactly sure why getRawType() returns Type instead of Class. Neal isn't either but
            // suspects some pathological case related to nested classes exists.
            Type rawType = parameterizedType.getRawType();
            if (!(rawType instanceof Class)) throw new IllegalArgumentException();
            return (Class<?>) rawType;
        }
        if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            // We could use the variable's bounds, but that won't work if there are multiple. Having a raw
            // type that's more general than necessary is okay.
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        }

        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or "
                + "GenericArrayType, but <" + type + "> is of type " + type.getClass().getName());
    }

    public static boolean isOnMain() {
        return Looper.myLooper() == Looper.getMainLooper();
    }


    public static <T> void inBackground(@NonNull final BaseCallback<T> cb, @NonNull final Callable<T> callable) {
        Global.post(new Runnable() {
            @Override
            public void run() {
                try {
                    final T t = callable.call();
                    Global.postOnMain(new Runnable() {
                        @Override
                        public void run() {
                            cb.onSuccess(t);
                        }
                    });

                } catch (final Exception e) {
                    Global.postOnMain(new Runnable() {
                        @Override
                        public void run() {
                            cb.onFailure(e);
                        }
                    });
                }
            }
        });
    }

    public static Map singleMap(@NonNull Object key, Object value) {
        Map retVal = new HashMap(1);
        retVal.put(key, value);
        return retVal;
    }


    /**
     * 判断一个 filed 是否是 Pointer（包含字段 id 和 _table）
     * @param elem
     * @return 如果是 Pointer，则返回其 ID；否则返回 null
     */
    public static String getPointerId(JsonElement elem) {
        if (elem != null && elem.isJsonObject()) {
            JsonObject obj = (JsonObject) elem;
            for (String filed : POINTER_FEATURE) {
                if (!obj.has(filed)) {
                    return null;
                }
            }
            try {
                return obj.get(Record.ID).getAsString();
            } catch (Exception e) {
                Log.e(Const.TAG, e.getMessage(), e);
                return null;
            }
        }
        return null;
    }

    public static <T> void each(Collection<T> collection, Action<T> action) {
        if (collection != null && collection.size() > 0 && action != null) {
            for (T aCollection : collection) {
                action.on(aCollection);
            }
        }
    }

    public static <R, T> PagedListResponse<R> transform(PagedListResponse<T> response, Function<T, R> func) {
        PagedListResponse<R> data = new PagedListResponse<>();
        data.setMeta(response.getMeta());
        List<T> objects = response.getObjects();
        if (objects != null && func != null) {
            data.setObjects(new ArrayList<R>(objects.size()));
            for (T item : objects) {
                data.getObjects().add(func.on(item));
            }
        }
        return data;
    }

    /**
     * copy from okhttp3.internal.Util
     * Quick and dirty pattern to differentiate IP addresses from hostnames. This is an approximation
     * of Android's private InetAddress#isNumeric API.
     *
     * <p>This matches IPv6 addresses as a hex string containing at least one colon, and possibly
     * including dots after the first colon. It matches IPv4 addresses as strings containing only
     * decimal digits and dots. This pattern matches strings like "a:.23" and "54" that are neither IP
     * addresses nor hostnames; they will be verified as IP addresses (which is a more strict
     * verification).
     */
    private static final Pattern VERIFY_AS_IP_ADDRESS = Pattern.compile(
            "([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");

    /**
     * copy from okhttp3.internal.Util
     * Returns true if {@code host} is not a host name and might be an IP address.
     * */
    private static boolean verifyAsIpAddress(String host) {
        return VERIFY_AS_IP_ADDRESS.matcher(host).matches();
    }


    /**
     * copy from okhttp3.Cookie
     */
    public static boolean domainMatch(String urlHost, String domain) {
        if (urlHost.equals(domain)) {
            return true; // As in 'example.com' matching 'example.com'.
        }

        if (urlHost.endsWith(domain)
                && urlHost.charAt(urlHost.length() - domain.length() - 1) == '.'
                && !verifyAsIpAddress(urlHost)) {
            return true; // As in 'example.com' matching 'www.example.com'.
        }

        return false;
    }


}
