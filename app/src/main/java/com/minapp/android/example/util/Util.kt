package com.minapp.android.example.util

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

object Util {

    fun loadingDialog(ctx: Context): Dialog {
        return ProgressDialog(ctx).apply {
            setMessage("加载中，请稍等...")
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    fun checkPermission(ctx: Context, permissions: Array<String>): Boolean {
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(ctx, it) == PackageManager.PERMISSION_DENIED)
                return false
        }
        return true
    }

    fun requestPermission(activity: Activity, code: Int, permissions: Array<String>) {
        ActivityCompat.requestPermissions(activity, permissions, code);
    }

    fun permissionGranted(results: IntArray): Boolean {
        results.forEach {
            if (it == PackageManager.PERMISSION_DENIED)
                return false
        }
        return true
    }

    fun toastSuccess(ctx: Context) {
        Toast.makeText(ctx, "操作成功", Toast.LENGTH_SHORT).show()
    }

    fun toastFailure(ctx: Context) {
        Toast.makeText(ctx, "操作失败", Toast.LENGTH_SHORT).show()
    }

    fun toast(ctx: Context, msg: String?) {
        Toast.makeText(ctx, msg ?: "", Toast.LENGTH_SHORT).show()
    }

    fun hideSoftInput(activity: Activity) {
        (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
    }

    fun dp2px(ctx: Context, dp: Int): Int {
        return (ctx.resources.displayMetrics.density * dp).toInt()
    }

}