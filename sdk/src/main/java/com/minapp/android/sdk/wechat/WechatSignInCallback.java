package com.minapp.android.sdk.wechat;

import androidx.annotation.Nullable;

public interface WechatSignInCallback {

    void onSuccess();

    void onFailure(@Nullable Exception ex);

}
