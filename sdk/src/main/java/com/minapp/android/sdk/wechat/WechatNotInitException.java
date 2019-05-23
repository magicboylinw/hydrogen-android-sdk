package com.minapp.android.sdk.wechat;

public class WechatNotInitException extends Exception {

    private static final String MSG = "you should invoke BaaS.initWechatComponent first";

    public WechatNotInitException() {
        super(MSG);
    }

    public WechatNotInitException(Throwable cause) {
        super(MSG, cause);
    }
}
