package com.minapp.android.sdk.wechat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.auth.Auth;
import com.minapp.android.sdk.auth.model.ThirdPartySignInReq;
import com.minapp.android.sdk.auth.model.ThirdPartySignInResp;
import com.minapp.android.sdk.exception.EmptyResponseException;
import com.minapp.android.sdk.util.StatusBarUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * 这里要区分是微信登录 or 关联微信
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View content = new View(this);
        content.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        content.setBackgroundColor(Color.TRANSPARENT);
        setContentView(content);
        StatusBarUtil.setStatusBar(Color.TRANSPARENT, true, false, this);

        try {
            if (!WechatComponent.handleIntent(getIntent(), this)) {
                onResult(false, null);
            }
        } catch (Exception e) {
            onResult(false, e);
        }
    }

    /**
     * 服务端微信注册
     * @param tokenFromWechat
     */
    private void sendServerAuth(String tokenFromWechat) {
        Global.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    ThirdPartySignInResp resp = Global.httpApi()
                            .signInWithWechat(new ThirdPartySignInReq(tokenFromWechat)).execute().body();
                    if (resp == null)
                        throw new EmptyResponseException();
                    if (!resp.isOk())
                        throw new Exception(new StringBuilder()
                                .append("sign in error: ")
                                .append(resp.message).toString()
                        );

                    Auth.signIn(resp.token, String.valueOf(resp.userId), resp.expiresIn);
                    onResult(true, null);

                } catch (Exception e) {
                    if (!isDestroyed()) {
                        onResult(false, e);
                    }
                }
            }
        });
    }

    /**
     * 绑定微信
     * @param token
     */
    private void associationWechat(String token) {
        Global.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    String updateType = WechatComponent.ASSOCIATION_TYPE != null ?
                            WechatComponent.ASSOCIATION_TYPE.value : null;
                    ThirdPartySignInResp response = Global.httpApi().associationWithWechat(
                            new ThirdPartySignInReq(token, updateType)).execute().body();

                    if (response == null)
                        throw new EmptyResponseException();
                    if (!response.isOk())
                        throw new Exception(new StringBuilder()
                                .append("sign in error: ")
                                .append(response.message).toString()
                        );
                    onResult(true, null);

                } catch (Exception e) {
                    if (!isDestroyed()) {
                        onResult(false, e);
                    }
                }
            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        try {
            if (!WechatComponent.handleIntent(intent, this)) {
                onResult(false, null);
            }
        } catch (Exception e) {
            onResult(false, e);
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
        onResult(false, null);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp instanceof SendAuth.Resp) {
            SendAuth.Resp authResp = (SendAuth.Resp) baseResp;
            if (BaseResp.ErrCode.ERR_OK != authResp.errCode) {
                onResult(false, new WechatException(authResp));
            } else {
                if (WechatComponent.WECHAT_CB != null) {
                    sendServerAuth(authResp.code);
                } else {
                    associationWechat(authResp.code);
                }
            }
        } else {
            onResult(false, null);
        }
    }

    @Override
    public void onBackPressed() {
        onResult(false, null);
    }

    private void onResult(boolean success, Exception ex) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            onResultAtMain(success, ex);
        } else {
            Global.postOnMain(new Runnable() {
                @Override
                public void run() {
                    onResult(success, ex);
                }
            });
        }
    }

    private void onResultAtMain(boolean success, Exception ex) {
        if (!isDestroyed())
            finish();

        WechatSignInCallback cb = WechatComponent.WECHAT_CB;
        if (cb != null) {
            if (success) {
                cb.onSuccess();
            } else {
                cb.onFailure(ex);
            }
            WechatComponent.WECHAT_CB = null;
        }

        AssociationCallback acb = WechatComponent.ASSOCIATION_CB;
        if (acb != null) {
            if (success) {
                acb.onSuccess();
            } else {
                acb.onFailure(ex);
            }
            WechatComponent.ASSOCIATION_CB = null;
            WechatComponent.ASSOCIATION_TYPE = null;
        }
    }

}
