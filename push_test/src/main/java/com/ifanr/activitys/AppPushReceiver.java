package com.ifanr.activitys;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.minapp.android.sdk.push.BaseBsPushReceiver;
import com.minapp.android.sdk.push.Log;
import com.minapp.android.sdk.push.Message;
import com.minapp.android.sdk.util.BsLog;

public class AppPushReceiver extends BaseBsPushReceiver {

    private static BsLog LOG = Log.get();

    @Override
    protected void onReceiveMessage(@NonNull Message message, @NonNull Context ctx) {
        LOG.d("receive push:%s", message.body.content);
        MessageActivity.start(message.body.content, ctx);
    }
}
