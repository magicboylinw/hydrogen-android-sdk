package com.minapp.android.example.wechat.pay.auth

import android.content.Context
import com.minapp.android.example.wechat.pay.BuildConfig
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

private var WX_API: IWXAPI? = null

fun wxApi(ctx: Context): IWXAPI {
    if (WX_API == null) {
        WX_API = WXAPIFactory.createWXAPI(ctx, null)
        WX_API?.registerApp(BuildConfig.APP_ID)
    }
    return WX_API!!
}