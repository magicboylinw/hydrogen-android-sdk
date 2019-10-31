package com.minapp.android.test.ui.signinup

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import com.minapp.android.test.R

/**
 * 登录/注册方式选择器
 */
class SignTypeSpinner: Spinner {

    var signType: SignType = SignType.EMAIL
    private set

    private val itemSelectedListener = object: OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>) {}

        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            signType = OPTIONS[position].signType
        }
    }

    constructor(context: Context) : super(context) { init(context) }
    constructor(context: Context, mode: Int) : super(context, mode) { init(context) }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { init(context) }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) { init(context) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, mode: Int) : super(
        context,
        attrs,
        defStyleAttr,
        mode
    ) { init(context) }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int,
        mode: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes, mode) { init(context) }

    @RequiresApi(Build.VERSION_CODES.M)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int,
        mode: Int,
        popupTheme: Resources.Theme?
    ) : super(context, attrs, defStyleAttr, defStyleRes, mode, popupTheme) { init(context) }


    private fun init(ctx: Context) {
        adapter = Adapter(ctx)
        onItemSelectedListener = itemSelectedListener
    }

    companion object {
        private val OPTIONS = arrayOf(
            Option(signType = SignType.EMAIL, text = "邮箱"),
            Option(signType = SignType.USERNAME, text = "用户名"),
            Option(signType = SignType.ANONYMOUS, text = "匿名")
        )
    }

    private class Adapter(
        ctx: Context
    ): ArrayAdapter<Option>(ctx, R.layout.sign_type_spinner_option, OPTIONS)
}

private data class Option(
    val signType: SignType,
    val text: String
) {
    override fun toString(): String = text
}

enum class SignType {
    EMAIL, USERNAME, ANONYMOUS
}