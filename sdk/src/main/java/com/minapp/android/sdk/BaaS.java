package com.minapp.android.sdk;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import com.minapp.android.sdk.auth.Auth;
import com.minapp.android.sdk.util.Util;

public class BaaS {
    private BaaS() {}

    /**
     * 完成 sdk 的初始化
     * @param clientId      ID 为知晓云应用的 ClientID，可通过知晓云管理后台进行获取
     *
     */
    public static void init(String clientId, @NonNull Application application) {
        Util.assetNotNull(application);
        Config.setClientId(clientId);
        Global.setApplicaiton(application);
        Auth.init();
    }

}
