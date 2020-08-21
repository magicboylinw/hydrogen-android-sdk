package com.minapp.android.sdk.weibo;

import androidx.annotation.NonNull;

public interface SignInCallback {
    void onSuccess();

    /**
     * 用户取消微博登录
     */
    void onCancel();

    /**
     * 登录失败（包括微博 sdk 异常、知晓云异常等）
     */
    void onFailure(@NonNull Throwable tr);
}
