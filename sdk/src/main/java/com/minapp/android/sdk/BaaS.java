package com.minapp.android.sdk;

import com.minapp.android.sdk.auth.Auth;

public class BaaS {
    private BaaS() {}

    /**
     * 完成 sdk 的初始化
     * @param clientId      ID 为知晓云应用的 ClientID，可通过知晓云管理后台进行获取
     *
     */
    public static void init(String clientId) {
        Config.setClientId(clientId);
    }

}
