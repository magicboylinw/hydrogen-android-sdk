package com.minapp.android.test.ui.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginTop
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.minapp.android.sdk.database.Record
import com.minapp.android.test.ext.dp2pxInt

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT)
class RecordLayout: LinearLayout {

    var position = 0
    @ModelProp set

    var record = Record()
    @ModelProp set

    constructor(context: Context) : super(context) { init(context) }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { init(context) }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) { init(context) }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) { init(context) }

    private fun init(ctx: Context) {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
    }

    @AfterPropsSet
    fun onStateChanged() {
        removeAllViews()
        addView(buildTextView(position.toString()).apply {
            setTypeface(Typeface.DEFAULT, Typeface.BOLD)
        })
        record._getJson().entrySet()
            .filter { it.key !in INNER_FIELDS }
            .map { it.value.toString() }
            .forEach { addText(it) }
    }

    private fun addText(text: CharSequence) {
        addView(buildTextView(text))
    }

    private fun buildTextView(text: CharSequence) = TextView(context).apply {
        setTextColor(Color.WHITE)
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            context.dp2pxInt(5f).also {
                topMargin = it
                leftMargin = it
                rightMargin = it
            }
        }
        this.text = text
        maxWidth = context.dp2pxInt(120f)
    }

    companion object {
        val INNER_FIELDS = arrayOf(Record.CREATED_AT, Record.CREATED_BY,
            Record.UPDATED_AT, Record.WRITE_PERM, Record.READ_PERM, Record.TABLE)
    }
}