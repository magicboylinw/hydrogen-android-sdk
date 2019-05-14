package com.minapp.android.sdk;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import com.minapp.android.sdk.auth.Auth;
import com.minapp.android.sdk.model.SendSmsCodeReq;
import com.minapp.android.sdk.model.StatusResp;
import com.minapp.android.sdk.model.VerifySmsCodeReq;
import com.minapp.android.sdk.util.BaseCallback;
import com.minapp.android.sdk.util.Retrofit2CallbackAdapter;
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


    /**
     * 发送短信验证码
     * http status code：
     * 200:	成功
     * 400:	失败（rate limit 或参数错误）
     * 402:	当前应用已欠费
     * 500:	服务错误
     * @param phone
     * @param cb
     */
    public static void sendSmsCode(String phone, BaseCallback<StatusResp> cb) {
        Global.httpApi().sendSmsCode(new SendSmsCodeReq(phone)).enqueue(new Retrofit2CallbackAdapter<StatusResp>(cb));
    }

    /**
     * 验证短信验证码
     * http status code:
     * 200:	成功
     * 400:	验证码错误 / 参数错误
     * @param phone
     * @param code
     * @param cb
     */
    public static void verifySmsCode(String phone, String code, BaseCallback<StatusResp> cb) {
        Global.httpApi().verifySmsCode(new VerifySmsCodeReq(phone, code)).enqueue(new Retrofit2CallbackAdapter<StatusResp>(cb));
    }

}
