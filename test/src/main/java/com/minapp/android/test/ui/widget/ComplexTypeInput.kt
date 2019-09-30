package com.minapp.android.test.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import com.minapp.android.test.ext.onTextChanged
import com.minapp.android.test.ui.base.FixedSizeStringAdapter
import com.minapp.android.test.ui.base.StringAdapter
import kotlin.reflect.KClass

class ComplexTypeInput: LinearLayout {

    private var state = State()
    private var listener: (KClass<*>, Any) -> Unit = {_, _ -> }

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
        gravity = Gravity.CENTER_VERTICAL
        Spinner(ctx).apply {
            adapter = StringAdapter(MAPPING.map { it.first }, ctx)
            onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    state = state.copy(clz = MAPPING[position].second)
                }
            }
            this@ComplexTypeInput.addView(this,
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
        }

        EditText(ctx).apply {
            onTextChanged {
                state = state.copy(content = it.trim())

                try {
                    val value = when (state.clz) {
                        Int::class -> state.content.toInt()
                        Float::class -> state.content.toFloat()
                        Boolean::class -> state.content.toBoolean()
                        else -> state.content
                    }
                    listener.invoke(state.clz, value)
                } catch (e: Exception) {}
            }
            hint = "value"
            setSingleLine(true)
            maxLines = 1
            this@ComplexTypeInput.addView(this,
                LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f))
        }
    }

    fun onContentChanged(block: (KClass<*>, Any) -> Unit) {
        listener = block
    }

    private data class State(
        val clz: KClass<*> = Unit::class,
        val content: String = ""
    )

    companion object {
        private val MAPPING = listOf(
            "String" to String::class,
            "Integer" to Int::class,
            "Float" to Float::class,
            "Boolean" to Boolean::class
        )
    }
}