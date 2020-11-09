package com.minapp.android.sdk.push;

import android.content.Context;

import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.exception.FlymePushRegisterException;
import com.minapp.android.sdk.util.BsLog;

/**
 * 接收 Flyme 推送
 */
public class BsFlymeMessageReceiver extends MzPushMessageReceiver {

    private BsLog log = Log.get();

    /**
     * 当用户点击通知栏消息后会在此方法回调
     * @param context
     * @param msg
     */
    @Override
    public void onNotificationClicked(Context context, MzPushMessage msg) {
        String customContent = msg != null ? msg.getSelfDefineContentString() : null;
        if (customContent != null && context != null) {
            PushUtil.broadcastMessage(customContent, context);
        }
    }

    @Override
    public void onRegisterStatus(Context context, RegisterStatus status) {
        if (status == null) return;
        if (RegisterStatus.SUCCESS_CODE.equalsIgnoreCase(status.code)) {
            log.d("flyme pushId: %s", status.getPushId());
        } else {
            log.e(new FlymePushRegisterException(status));
        }
    }

    @Override
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {}

    @Override
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {}

    @Override
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {}

    @Override
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {}


}
