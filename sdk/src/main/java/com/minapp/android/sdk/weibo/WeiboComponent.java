package com.minapp.android.sdk.weibo;

import android.app.Activity;
import android.content.Context;
import android.os.HandlerThread;
import android.util.Log;

import androidx.annotation.NonNull;

import com.minapp.android.sdk.Assert;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.auth.Auth;
import com.minapp.android.sdk.auth.model.ThirdPartySignInReq;
import com.minapp.android.sdk.auth.model.ThirdPartySignInResp;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class WeiboComponent {

    private static final String TAG = "WeiboComponent";

    public static void init(Context ctx, String key, String redirectUrl, String scope) {
        WbSdk.install(ctx, new AuthInfo(ctx, key, redirectUrl, scope));
    }

    /**
     * 微博登录
     * @param activity
     * @param cb
     * @return SsoHandler，需在 Activity.onActivityResult 里调用 handler.authorizeCallBack
     */
    public static SsoHandler signIn(@NonNull Activity activity, @NonNull SignInCallback cb) {
        Assert.notNull(activity, "activity");
        Assert.notNull(cb, "callback");

        SsoHandler handler = new SsoHandler(activity);
        handler.authorize(new WbAuthListener() {
            @Override
            public void onSuccess(Oauth2AccessToken token) {
                signInToBaaS(token, cb);
            }

            @Override
            public void cancel() {
                cb.onCancel();
            }

            @Override
            public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
                cb.onFailure(new WbSDKException(wbConnectErrorMessage));
            }
        });
        return handler;
    }

    /**
     * 关联到微博
     * @param activity
     * @param cb
     * @return SsoHandler，需在 Activity.onActivityResult 里调用 handler.authorizeCallBack
     */
    public static SsoHandler associationWithWeibo(
            @NonNull Activity activity, @NonNull SignInCallback cb) {
        Assert.notNull(activity, "activity");
        Assert.notNull(cb, "callback");

        SsoHandler handler = new SsoHandler(activity);
        handler.authorize(new WbAuthListener() {
            @Override
            public void onSuccess(Oauth2AccessToken token) {
                associationToBaaS(token, cb);
            }

            @Override
            public void cancel() {
                cb.onCancel();
            }

            @Override
            public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
                cb.onFailure(new WbSDKException(wbConnectErrorMessage));
            }
        });
        return handler;
    }

    /**
     * 关联到知晓云
     * @param token
     */
    private static void associationToBaaS(Oauth2AccessToken token, SignInCallback cb) {
        ThirdPartySignInReq request = new ThirdPartySignInReq();
        request.accessToken = token != null ? token.getToken() : "";
        request.uid = token != null ? token.getUid() : "";
        Global.httpApi().associationWithWeibo(request).enqueue(new Callback<ThirdPartySignInResp>() {
            @Override
            public void onResponse(Call<ThirdPartySignInResp> call, Response<ThirdPartySignInResp> response) {
                try {
                    ThirdPartySignInResp body = response.body();
                    Auth.signIn(body.token, String.valueOf(body.userId), body.expiresIn);
                    cb.onSuccess();
                } catch (Exception e) {
                    cb.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<ThirdPartySignInResp> call, Throwable t) {
                cb.onFailure(t);
            }
        });
    }

    /**
     * 知晓云登录
     * @param token
     */
    private static void signInToBaaS(Oauth2AccessToken token, SignInCallback cb) {
        ThirdPartySignInReq request = new ThirdPartySignInReq();
        request.accessToken = token != null ? token.getToken() : "";
        request.uid = token != null ? token.getUid() : "";
        Global.httpApi().signInByWeibo(request).enqueue(new Callback<ThirdPartySignInResp>() {
            @Override
            public void onResponse(Call<ThirdPartySignInResp> call, Response<ThirdPartySignInResp> response) {
                try {
                    ThirdPartySignInResp body = response.body();
                    Auth.signIn(body.token, String.valueOf(body.userId), body.expiresIn);
                    cb.onSuccess();
                } catch (Exception e) {
                    cb.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<ThirdPartySignInResp> call, Throwable t) {
                cb.onFailure(t);
            }
        });
    }
}
