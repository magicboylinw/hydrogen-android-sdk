package com.minapp.android.example;

import com.minapp.android.sdk.BaaS;
import com.minapp.android.sdk.model.CloudFuncResp;
import com.minapp.android.sdk.model.StatusResp;
import com.minapp.android.sdk.util.BaseCallback;

public class Example {
    public static void main(String[] args) {
        String funcName = "addFunc";
        String data = "{\"name\":\"allen\"}";
        BaaS.invokeCloudFunc(funcName, data, null, new BaseCallback<CloudFuncResp>() {
            @Override
            public void onSuccess(CloudFuncResp cloudFuncResp) {
                if (cloudFuncResp.getCode() == 0) {
                    // 调用成功
                }
            }

            @Override
            public void onFailure(Throwable e) {
                // 调用失败
            }
        });
    }
}
