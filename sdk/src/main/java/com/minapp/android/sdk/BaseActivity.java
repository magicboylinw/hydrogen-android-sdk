package com.minapp.android.sdk;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.minapp.android.sdk.util.StatusBarUtil;

import java.lang.ref.WeakReference;

public abstract class BaseActivity extends Activity {

    /**
     * 设置透明的 content
     */
    protected void setTranslucentContent() {
        View content = new View(this);
        content.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        content.setBackgroundColor(Color.TRANSPARENT);
        setContentView(content);
        StatusBarUtil.setStatusBar(Color.TRANSPARENT, true, false,
                this);

        Window window = getWindow();
        if (window != null) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    protected void finishDelayed(long delayMillis) {
        if (delayMillis <= 0) {
            finish();
        } else {
            Global.postDelayed(new FinishJob(this), delayMillis);
        }
    }

    private static final class FinishJob implements Runnable {

        private WeakReference<Activity> activityRef;

        public FinishJob(Activity activity) {
            activityRef = new WeakReference(activity);
        }

        @Override
        public void run() {
            if (activityRef != null) {
                Activity activity = activityRef.get();
                if (activity != null) {
                    activity.finish();
                    activityRef.clear();
                    activityRef = null;
                }
            }
        }
    }
}
