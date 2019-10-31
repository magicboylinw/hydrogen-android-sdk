package com.minapp.android.example.util

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.minapp.android.example.base.BaseDialog
import com.minapp.android.test.R
import com.minapp.android.test.ext.dp2pxInt
import kotlinx.android.synthetic.main.dialog_text_panel.*

class TextPanelDialogFragment: AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val content = arguments?.getString(ARG_CONTENT) ?: ""
        return TextPanelDialog(content, context!!)
    }

    companion object {

        const val ARG_CONTENT = "ARG_CONTENT"

        fun create(content: String): TextPanelDialogFragment {
            return TextPanelDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CONTENT, content)
                }
            }
        }
    }

    private class TextPanelDialog(content: String, context: Context) : BaseDialog(context) {

        init {
            setContentView(R.layout.dialog_text_panel)
            contentTv.text = content
        }

        override fun show() {
            super.show()
            window?.attributes?.apply {
                width = context.resources.displayMetrics.widthPixels - context.dp2pxInt(24f)
                height = (context.resources.displayMetrics.heightPixels * 0.8).toInt()
                gravity = Gravity.CENTER
            }
        }
    }
}