package com.minapp.android.sdk.push;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.WorkerThread;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.push.BsPushManager;
import com.minapp.android.sdk.push.Log;
import com.minapp.android.sdk.push.Message;
import com.minapp.android.sdk.push.PushUtil;
import com.minapp.android.sdk.util.BsLog;
import com.minapp.android.sdk.util.Util;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

/**
 * 继承 {@link PushMessageReceiver}，接收 mi push event，
 * https://dev.mi.com/console/doc/detail?pId=41#_1_1
 */
public class BsMiPushMessageReceiver extends PushMessageReceiver {

    private static final BsLog LOG = Log.get();
    private static final Gson GSON = Global.gson();

    /**
     * 接收服务器发送的透传消息
     * app 未启动时，无法接收透传消息
     * @param context
     * @param miPushMessage
     */
    @Override
    @WorkerThread
    @Deprecated()
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        LOG.d("小米透传\n%s", miPushMessage != null ? miPushMessage.toString() : "null");
    }

    /**
     * 接收服务器发来的通知栏消息（用户点击通知栏时触发）
     * 无论 app 是否有启动都会触发
     *
     * 一个 {@link MiPushMessage} 的例子
     * messageId={sdm59267600846639717fv},
     * passThrough={0},
     * alias={null},
     * topic={null},
     * userAccount={null},
     * content={ABC},                 //消息内容
     * description={我的小米推送消息},  //通知栏的内容
     * title={Hello，mi push!},       //通知栏的标题
     * isNotified={false},
     * notifyId={0},
     * notifyType={7},
     * category={null},
     * extra={{
     *  channel_name=default channel,
     *  __target_name=+zemS6HV31/BgcdrQLrWQT+TMB4PYw+NnSVRzoPIvlV+w5F+OxcqoYZx47fe1xJT,
     *  high_priority_event=8,
     *  fe_ts=1600846639717,
     *  __planId__=0,
     *  source=op,
     *  notify_foreground=1,
     *  __m_ts=1600846639895,
     *  channel_id=default, __test=true
     * }}
     *
     * @param context
     * @param miPushMessage
     */
    @Override
    @WorkerThread
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        LOG.d("小米通知栏点击\n%s", miPushMessage != null ? miPushMessage.toString() : "null");

        String content = miPushMessage != null ? miPushMessage.getContent() : null;
        if (content == null)
            return;

        Class receiverClz = BsPushManager.appReceiverClz;
        if (receiverClz == null) {
            LOG.e(new NullPointerException("app push receiver is null"));
            return;
        }

        try {
            Message message = Message.parse(content);
            PushUtil.broadcastMessage(message, receiverClz, context);
        } catch (Throwable tr) {
            LOG.e(tr, "parse mi push message fail\n%s", content);
        }
    }

    /**
     * 接收服务器发来的通知栏消息（消息到达客户端时触发，并且可以接收应用在前台时不弹出通知的通知消息）
     * app 启动后（即使在后台）才会触发，而且在 {@link #onNotificationMessageClicked(Context, MiPushMessage)} 之前
     * @param context
     * @param miPushMessage
     */
    @Override
    @WorkerThread
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        LOG.d("小米通知栏消息\n%s", miPushMessage != null ? miPushMessage.toString() : "null");
    }
}
