package com.minapp.android.sdk.weibo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.minapp.android.sdk.Global;
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

    public static void signIn(Activity activity) {
        SsoHandler handler = new SsoHandler(activity);
        handler.authorize(new WbAuthListener() {
            @Override
            public void onSuccess(Oauth2AccessToken token) {
                Global.httpApi().signInByWeibo(new ThirdPartySignInReq(token.getToken())).enqueue(new Callback<ThirdPartySignInResp>() {
                    @Override
                    public void onResponse(Call<ThirdPartySignInResp> call, Response<ThirdPartySignInResp> response) {
                        Log.d(TAG, String.valueOf(response.body().userId));
                    }

                    @Override
                    public void onFailure(Call<ThirdPartySignInResp> call, Throwable t) {

                    }
                });
            }

            @Override
            public void cancel() {

            }

            @Override
            public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {

            }
        });
    }

}
