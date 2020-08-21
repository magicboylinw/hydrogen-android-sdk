package com.minapp.android.sdk.wechat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.minapp.android.sdk.Const;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.auth.Auth;
import com.minapp.android.sdk.exception.SessionMissingException;
import com.minapp.android.sdk.model.OrderResp;
import com.minapp.android.sdk.util.BaseCallback;
import com.minapp.android.sdk.util.Retrofit2CallbackAdapter;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public final class WechatComponent {

    public static IWXAPI WECHAT_API = null;
    public static WechatSignInCallback WECHAT_CB = null;
    public static AssociationCallback ASSOCIATION_CB = null;
    public static AssociationType ASSOCIATION_TYPE = null;

    private WechatComponent() {}


    /**
     * 当前登录用户关联微信
     * @param type 更新用户信息的方式，选填
     * @param cb
     * @see AssociationType
     */
    public static void associationWithWechat(AssociationType type, AssociationCallback cb) {
        try {
            assertInit();
            if (!Auth.signedIn()) {
                throw new SessionMissingException();
            }
            sendWechatAuthReq();
            ASSOCIATION_CB = cb;
            ASSOCIATION_TYPE = type;
            WECHAT_CB = null;
        } catch (Exception e) {
            if (cb != null) {
                cb.onFailure(e);
            }
            ASSOCIATION_CB = null;
            ASSOCIATION_TYPE = null;
        }
    }


    /**
     * 微信登录
     * @param cb
     */
    public static void signIn(WechatSignInCallback cb) {
        try {
            sendWechatAuthReq();
            WECHAT_CB = cb;
            ASSOCIATION_CB = null;
            ASSOCIATION_TYPE = null;
        } catch (Exception e) {
            if (cb != null) {
                cb.onFailure(e);
            }
            WECHAT_CB = null;
        }
    }

    private static void sendWechatAuthReq() throws Exception {
        if (WechatComponent.WECHAT_API == null) {
            throw new WechatNotInitException();
        } else {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = Const.WX_OAUTH_SCOPE;
            req.state = Const.WX_OAUTH_STATE;
            if (!WechatComponent.WECHAT_API.sendReq(req)) {
                throw new Exception("fail to send auth req to wechat");
            }
        }
    }

    /**
     * 如果要调用微信相关的 api，则需要初始化微信组件
     */
    public static void initWechatComponent(String appId, Context ctx) {
        WECHAT_API = WXAPIFactory.createWXAPI(ctx, null);
        WECHAT_API.registerApp(appId);
    }

    /**
     * 获取订单详情
     * @param transactionNo
     * @param cb
     */
    public static void getOrderInfo(String transactionNo, BaseCallback<OrderResp> cb) {
        Global.httpApi().getOrderInfo(transactionNo).enqueue(new Retrofit2CallbackAdapter<OrderResp>(cb));
    }


    /**
     * 从 {@link android.app.Activity#onActivityResult(int, int, Intent)} 里获取额外的信息
     * @param data
     * @return
     */
    public static WechatOrderResult getOrderResultFromData(Intent data) {
        WechatOrderResult result = null;
        if (data != null) {
            result = data.getParcelableExtra(WXPayEntryActivity.DATA_RESULT);
        }
        return result;
    }

    /**
     * 发起微信支付
     * @param order
     */
    public static void pay(WechatOrder order, int requestCode, Activity activity) throws WechatNotInitException {
        assertInit();
        WXPayEntryActivity.startActivityForResult(requestCode, order, activity);
    }

    static void assertInit() throws WechatNotInitException {
        if (WECHAT_API == null) {
            throw new WechatNotInitException();
        }
    }

    /**
     * proxy for {@link IWXAPI#handleIntent(Intent, IWXAPIEventHandler)}
     * @param intent
     * @param handler
     * @return false - 把 activity finish 掉即可
     */
    public static boolean handleIntent(Intent intent, IWXAPIEventHandler handler) throws WechatNotInitException {
        assertInit();
        return WECHAT_API.handleIntent(intent, handler);
    }
}
