package com.minapp.android.sdk.push;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.minapp.android.sdk.BaseActivity;
import com.minapp.android.sdk.util.BsLog;
import com.minapp.android.sdk.util.StatusBarUtil;

/**
 * hms push 采用通知栏消息推送，这样即使 app 未启动也可以收到推送
 * 打开应用，自定义 intent uri 页面，数据以 extra string 的方式传递，key 是 {@link HmsReceiverActivity#EXTRA_PUSH_HMS_DATA}，eg:
 * intent://hms/receiver#Intent;scheme=ifanr;launchFlags=0x4000000;S.extra_push_hms_data=%E9%AB%98%E5%B1%B1%E6%B5%81%E6%B0%B4;end
 * https://developer.huawei.com/consumer/cn/doc/HMSCore-Guides-V5/android-client-dev-0000001050042041-V5#ZH-CN_TOPIC_0000001050042041__section1536212191414
 */
public class HmsReceiverActivity extends BaseActivity {

    private static BsLog LOG = Log.get();

    public static final String EXTRA_PUSH_HMS_DATA = "extra_push_hms_data";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LOG.d("HmsReceiverActivity start");
        setTranslucentContent();
        handleIntent();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            String data = intent.getStringExtra(EXTRA_PUSH_HMS_DATA);
            if (data != null) {
                Message message = Message.parse(data);
                message.broadcast(this);
                LOG.d("hms receiver activity, broadcast message success");
            }
        }
        finishDelayed(1000 * 2);
    }
}
