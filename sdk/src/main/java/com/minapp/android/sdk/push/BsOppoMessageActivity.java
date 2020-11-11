package com.minapp.android.sdk.push;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.minapp.android.sdk.util.BsLog;

/**
 * oppo push 在通知栏点击后，只支持三种情况：打开首页，打开指定页面，打开网址
 * 那么只能通过打开指定页面来启动 push receiver
 */
public class BsOppoMessageActivity extends AppCompatActivity {

    private BsLog log = Log.get();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processMessage();
    }

    private void processMessage() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }

        String message = extras.getString("baas_message");
        if (message == null) {
            finish();
            return;
        }

        if (!PushUtil.broadcastMessage(message, this)) {
            finish();
            return;
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isDestroyed()) {
                    finish();
                }
            }
        }, 2 * 1000);
    }

    @Override
    public void onBackPressed() {}
}
