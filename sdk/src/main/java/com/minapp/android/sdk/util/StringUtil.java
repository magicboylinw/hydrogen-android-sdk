package com.minapp.android.sdk.util;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.minapp.android.sdk.Const;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

public abstract class StringUtil {

    private static boolean contains(String source, String match, Boolean ignoreCase) {
        if (source == null || match == null) {
            return false;
        } else {
            String s = ignoreCase ? source.toLowerCase() : source;
            String m = ignoreCase ? match.toLowerCase() : match;
            return s.contains(m);
        }
    }

    public static boolean containsIgnoreCase(String source, String match) {
        return contains(source, match, true);
    }

    public static boolean containsIgnoreCase(String source, String[] matchs) {
        if (matchs == null || matchs.length == 0 || source == null)
            return false;
        for (String match : matchs) {
            if (containsIgnoreCase(source, match))
                return true;
        }
        return false;
    }

    public static boolean contains(String source, String match) {
        return contains(source, match, false);
    }

    public static @Nullable String toLowerCase(String orign) {
        if (orign == null)
            return orign;
        else
            return orign.toLowerCase();
    }


    public static @Nullable String readString(InputStream input) {
        return readString(input, "utf-8");
    }

    public static @Nullable String readString(InputStream input, String charset) {
        try {
            byte[] bytes = new byte[input.available()];
            input.read(bytes);
            return new String(bytes, charset);
        } catch (Exception e) {
            try {
                input.close();
            } catch (Exception ignored) {}
            return null;
        }
    }

    public static @NonNull String join(Collection data, String separator) {
        if (data != null && !data.isEmpty()) {
            if (separator == null) {
                separator = ",";
            }
            StringBuilder sb = new StringBuilder();
            Iterator iterator = data.iterator();
            Object item = null;
            while (iterator.hasNext()) {
                item = iterator.next();
                if (item != null) {
                    sb.append(item.toString()).append(separator);
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
        return "";
    }

    public static @NonNull String join(Collection data) {
        return join(data, Const.COMMA);
    }

    /**
     * 如果集合为 null or empty，返回 null
     * @param data
     * @return
     */
    public static @Nullable String joinToNull(Collection data) {
        String result = join(data);
        return TextUtils.isEmpty(result) ? null : result;
    }

    public static @Nullable String trimToNull(String source) {
        if (source == null)
            return null;

        source = source.trim();
        return source.isEmpty() ? null : source;
    }
}
