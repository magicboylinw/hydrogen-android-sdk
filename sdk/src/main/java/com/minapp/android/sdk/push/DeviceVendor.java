package com.minapp.android.sdk.push;

import android.Manifest;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.minapp.android.sdk.Assert;
import com.minapp.android.sdk.util.StringUtil;

public enum DeviceVendor{

    HUAWEI(new String[]{
            Manifest.permission.READ_PHONE_STATE
    }),

    MI(null),

    FLYME(new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    }),

    VIVO(null),
    OPPO(null),
    FCM(null);

    public final String[] permissions;

    DeviceVendor(String[] permissions) {
        this.permissions = permissions;
    }

    private static DeviceVendor VENDOR = null;

    public static @Nullable DeviceVendor get(@NonNull Context ctx) {
        if (VENDOR == null) {
            synchronized (DeviceVendor.class) {
                if (VENDOR == null) {
                    Assert.notNull(ctx, "Context");
                    if (isMi()) {
                        VENDOR = MI;
                    } else if (isHuawei()) {
                        VENDOR = HUAWEI;
                    } else if (isFlyme(ctx)) {
                        VENDOR = FLYME;
                    }
                }
            }
        }
        return VENDOR;
    }

    /**
     * 判断是否为小米手机
     * copy from AVMixPushManager.isXiaomiPhone in leancloud sdk
     * @return
     */
    private static boolean isMi() {
        return StringUtil.containsIgnoreCase(Build.MANUFACTURER, "xiaomi");
    }

    /**
     * 判断是否为华为手机
     * copy from AVMixPushManager.isHuaweiPhone() in leancloud sdk
     * @return
     */
    private static boolean isHuawei() {
        return StringUtil.containsIgnoreCase(Build.BRAND, new String[]{"huawei", "honor"});
    }

    /**
     * 判断是否魅族手机
     * @return
     */
    private static boolean isFlyme(Context ctx) {
        try {
            return MzSystemUtils.isBrandMeizu(ctx);
        } catch (Throwable ignored) {
            return false;
        }
    }

    /**
     * 判断是否 Oppo 手机
     * @return
     */
    /*private static boolean isOppo() {
        try {
            return com.heytap.msp.push.HeytapPushManager.isSupportPush();
        } catch (Throwable ignored) {
            return false;
        }
    }*/


}
