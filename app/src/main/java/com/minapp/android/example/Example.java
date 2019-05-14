package com.minapp.android.example;

import com.minapp.android.sdk.BaaS;
import com.minapp.android.sdk.model.StatusResp;
import com.minapp.android.sdk.util.BaseCallback;

public class Example {
    public static void main(String[] args) {
        String phone = "12345678901";
        String code = "123456";
        BaaS.verifySmsCode(phone, code, new BaseCallback<StatusResp>() {
            @Override
            public void onSuccess(StatusResp resp) {
                if (resp.isOk()) {
                    // 校验通过
                }
            }

            @Override
            public void onFailure(Throwable e) {
                // 校验不通过
            }
        });
    }
}
