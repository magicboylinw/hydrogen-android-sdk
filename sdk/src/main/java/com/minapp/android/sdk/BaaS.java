package com.minapp.android.sdk;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import com.google.gson.JsonElement;
import com.minapp.android.sdk.auth.Auth;
import com.minapp.android.sdk.model.*;
import com.minapp.android.sdk.util.BaseCallback;
import com.minapp.android.sdk.util.Retrofit2CallbackAdapter;
import com.minapp.android.sdk.util.Util;
import com.minapp.android.sdk.wechat.WechatComponent;
import com.minapp.android.sdk.wechat.WechatOrder;
import com.minapp.android.sdk.wechat.WechatOrderResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

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
     * 如果要调用微信相关的 api，则需要初始化微信组件
     */
    public static void initWechatComponent(String appId, Context ctx) {
        WechatComponent.initWechatComponent(appId, ctx);
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

    /**
     * 触发云函数的执行
     * @param funcName 要运行的云函数名
     * @param data     要传进云函数的 event.data
     * @param sync     true 表示同步，false 表示异步
     * @param cb       返回云函数的执行结果
     */
    public static void invokeCloudFunc(String funcName, String data, Boolean sync, BaseCallback<CloudFuncResp> cb) {
        JsonElement json = null;
        try {
            json = Global.gson().fromJson(data, JsonElement.class);
        } catch (Exception e) {}
        Global.httpApi().invokeCloudFunc(new CloudFuncReq(funcName, json, sync)).enqueue(new Retrofit2CallbackAdapter<CloudFuncResp>(cb));
    }

}
