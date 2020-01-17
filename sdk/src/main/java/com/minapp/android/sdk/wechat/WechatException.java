package com.minapp.android.sdk.wechat;

import com.tencent.mm.opensdk.modelbase.BaseResp;

public class WechatException extends Exception {

    public WechatException(BaseResp resp) {
        super(new StringBuilder()
                .append("error from wechat, ")
                .append("code:").append(resp.errCode).append(", ")
                .append("msg:").append(resp.errStr)
                .toString());
    }

}
