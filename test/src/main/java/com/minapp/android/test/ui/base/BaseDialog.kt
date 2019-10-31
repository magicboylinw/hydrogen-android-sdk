package com.minapp.android.example.base

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import com.minapp.android.test.ext.dp2px
import com.minapp.android.test.ext.dp2pxInt

abstract class BaseDialog: AppCompatDialog {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, theme: Int) : super(context, theme)
    constructor(context: Context?, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(
        context,
        cancelable,
        cancelListener
    )

    override fun show() {
        super.show()
        window?.attributes?.apply {
            width = context.resources.displayMetrics.widthPixels - context.dp2pxInt(24f)
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.CENTER
        }
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}