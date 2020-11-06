package com.minapp.android.sdk.push;

import android.content.Context;

import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.util.BsLog;
import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.sdk.OpenClientPushMessageReceiver;

/**
 * 用来接收 VIVO 推送
 */
public class BsVivoPushMessageReceiver extends OpenClientPushMessageReceiver {

    private final BsLog log = Log.get();

    /***
     * 当通知被点击时回调此方法
     * @param context 应用上下文
     * @param msg 通知详情，详细信息见API接入文档
     */
    @Override
    public void onNotificationMessageClicked(Context context, UPSNotificationMessage msg) {
        // 在这里携带文本内容
        String content = msg.getSkipContent();
        if (content != null) {
            PushUtil.broadcastMessage(content, context);
        }
    }

    /**
     * 当首次 turnOnPush 成功或 regId 发生改变时，回调此方法
     * 如需获取 regId，请使用 PushClient.getInstance(context).getRegId()
     * @param context 应用上下文
     * @param regId 注册id
     */
    @Override
    public void onReceiveRegId(Context context, String regId) {
        log.d(String.format("vivo regId: %s", regId));
    }
}
