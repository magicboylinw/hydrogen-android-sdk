package com.minapp.android.sdk.weibo;

import com.sina.weibo.sdk.auth.WbConnectErrorMessage;

public class WbSDKException extends Exception {

    public WbConnectErrorMessage source;

    public WbSDKException(WbConnectErrorMessage source) {
        super(String.format("code:%s, message:%s",
                source != null ? source.getErrorCode() : "",
                source != null ? source.getErrorMessage() : ""));
        this.source = source;
    }
}
