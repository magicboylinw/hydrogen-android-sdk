package com.minapp.android.sdk.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class StatusBarUtil {

    private static final Object FAKE_STATUS_BAR_TAG = new Object();


    /**
     * 透明状态栏，无占位，白色文本
     * @param activity
     */
    public static void setTranslucent(Activity activity) {
        setStatusBar(Color.TRANSPARENT, true, false, activity);
    }

    public static void setTranslucent(Fragment f) {
        if (f != null) {
            setStatusBar(Color.TRANSPARENT, true, false, f.getActivity());
        }
    }


    /**
     * 黑色背景，有占位，白色文本
     * @param activity
     */
    public static void setDark(Activity activity) {
        setStatusBar(Color.BLACK, true, true, activity);
    }

    public static void setDark(Fragment f) {
        if (f != null && f.getView() != null) {
            setStatusBar(Color.BLACK, true, false, f.getActivity());
            f.getView().setFitsSystemWindows(true);
        }
    }


    /**
     * 白色背景，有占位，黑色文本
     * @param activity
     */
    public static void setLight(Activity activity) {
        setStatusBar(Color.WHITE, false, true, activity);
    }

    public static void setLight(Fragment f) {
        if (f != null && f.getView() != null) {
            setStatusBar(Color.WHITE, false, false, f.getActivity());
            f.getView().setFitsSystemWindows(true);
        }
    }


    /**
     *
     * @param bgColor
     * @param lightText
     * @param fitsSystemWindows
     * @param activity
     * @see #setStatusBar(int, boolean, boolean, Window)
     */
    public static void setStatusBar(
            int bgColor,
            boolean lightText,
            boolean fitsSystemWindows,
            Activity activity) {
        if (activity != null) {
            setStatusBar(bgColor, lightText, fitsSystemWindows, activity.getWindow());
        }
    }

    /**
     * @param bgColor 状态栏的背景色
     * @param lightText true - 白色文本；false - 黑色文本
     * @param fitsSystemWindows 是否要状态栏占位
     * @param window
     */
    public static void setStatusBar(
            int bgColor,
            boolean lightText,
            boolean fitsSystemWindows,
            Window window) {

        // 4.4 <= version < 6.0 的沉浸式状态栏；原生 rom 是灰色或灰色渐变，有一点透明度的状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                window != null) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // 占位
            if (window.getDecorView() != null) {
                ViewGroup content = (ViewGroup) window.findViewById(android.R.id.content);
                if (content != null && content.getChildCount() > 0) {
                    content.getChildAt(0).setFitsSystemWindows(fitsSystemWindows);
                }
            }
        }
        setBackgroundColor(bgColor, window);
        setTextColor(!lightText, window);
    }


    /**
     * 单独设置状态栏的字体为黑色
     * @param activity
     */
    public static void setTextDark(Activity activity) {
        if (activity != null) {
            setTextColor(true, activity.getWindow());
        }
    }

    public static void setTextDark(Fragment f) {
        if (f != null && f.getActivity() != null) {
            setTextColor(true, f.getActivity().getWindow());
        }
    }

    public static void setTextLight(Activity activity) {
        if (activity != null) {
            setTextColor(false, activity.getWindow());
        }
    }

    public static void setTextLight(Fragment f) {
        if (f != null && f.getActivity() != null) {
            setTextColor(false, f.getActivity().getWindow());
        }
    }

    /**
     * 单独设置状态栏的字体颜色
     * @param dark true - 黑色；false - 白色
     * @param window
     */
    private static void setTextColor(boolean dark, Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                window != null &&
                window.getDecorView() != null) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(dark ?
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR :
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }
        setTextColorMiui(dark, window);
    }

    /**
     * 单独设置状态栏的背景色
     * @param color
     * @param window
     */
    public static void setBackgroundColor(int color, Window window) {
        if (window != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
            }
        }
    }

    /**
     * 获取状态栏的高度
     * @param ctx
     * @return
     */
    public static int getHeight(Context ctx) {
        if (ctx == null) {
            return 0;
        }
        Resources res = ctx.getResources();
        int resId = res.getIdentifier("status_bar_height", "dimen", "android");
        return resId > 0 ?
                res.getDimensionPixelSize(resId) :
                (int) Math.ceil(res.getDisplayMetrics().density * 24);            //最后采用 api 25 里配置的状态栏高度 24dp
    }

    /**
     * miui 提供的设置状态栏色系的方案
     */
    private static void setTextColorMiui(boolean darkTheme, Window window) {
        if (window != null) {
            Class<? extends Window> clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                extraFlagField.invoke(window, darkTheme ? darkModeFlag : 0, darkModeFlag);
            } catch (Exception e) {}
        }
    }

    /**
     * 创建一个 status bar 的占位 view
     * @param color
     * @param parent
     */
    private static void createOrUpdateStatusBar(int color, ViewGroup parent) {
        if (parent.getChildCount() > 0 && parent.getChildAt(0).getTag() == FAKE_STATUS_BAR_TAG) {
            parent.getChildAt(0).setBackgroundColor(color);
        } else {
            View statusBar = new View(parent.getContext());
            statusBar.setBackgroundColor(color);
            statusBar.setTag(FAKE_STATUS_BAR_TAG);
            parent.addView(
                    statusBar,
                    0,
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            getHeight(parent.getContext())
                    )
            );
        }
    }

    /**
     * 移除 status bar 占位 view
     * @param parent
     */
    private static void removeStatusBar(ViewGroup parent) {
        if (parent.getChildCount() > 0 && parent.getChildAt(0).getTag() == FAKE_STATUS_BAR_TAG) {
            parent.removeViewAt(0);
        }
    }
}
