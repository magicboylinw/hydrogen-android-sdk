package com.minapp.android.sdk.push;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;
import com.minapp.android.sdk.util.BsLog;

/**
 * 用以接收 hms push event
 */
public class BsHmsMessageService extends HmsMessageService {

    private static BsLog LOG = Log.get();


    /**
     * 注册 hms push 成功后，返回 push token
     * https://developer.huawei.com/consumer/cn/doc/development/HMSCore-Guides-V5/android-client-dev-0000001050042041-V5#ZH-CN_TOPIC_0000001050042041__section876955375919
     * @param token
     */
    @Override
    public void onNewToken(String token) {
        if (Strings.isNullOrEmpty(token)) {
            LOG.w("hms push token is empty");
            return;
        }
        LOG.d("hms regId: %s", token);
    }

    /**
     * 接收透传消息
     * 透传消息只有在 app 启动后才能接收，所以我们采用通知栏消息推送
     * https://developer.huawei.com/consumer/cn/doc/development/HMSCore-Guides-V5/android-client-dev-0000001050042041-V5#ZH-CN_TOPIC_0000001050042041__section161573511014
     * @param message
     */
    @Override
    public void onMessageReceived(RemoteMessage message) {
        if (message == null) {
            LOG.w("hms receive null message");
            return;
        }

        LOG.d("hms receive message: %s", message.getData());
    }
}
