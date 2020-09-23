package com.minapp.android.sdk.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

/**
 * app 接收 BaaS SDK 推送的基类
 * 用户继承此类
 */
public abstract class BaseBsPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Message message = intent.getParcelableExtra(PushUtil.EXTRA_MESSAGE);
        if (message != null) {
            onReceiveMessage(message);
        }
    }

    protected abstract void onReceiveMessage(@NonNull Message message);
}
