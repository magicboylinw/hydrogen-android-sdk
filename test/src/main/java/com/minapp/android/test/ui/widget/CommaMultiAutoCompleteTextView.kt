package com.minapp.android.test.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView
import com.minapp.android.test.ext.onTextChanged
import com.minapp.android.test.ui.base.FixedSizeStringAdapter
import kotlin.properties.Delegates

typealias OnItemChangedListener = (List<String>) -> Unit

class CommaMultiAutoCompleteTextView: AppCompatMultiAutoCompleteTextView {

    private var listener: OnItemChangedListener = {}
    private lateinit var adapter: ArrayAdapter<String>
    var items: List<String> by Delegates.observable(listOf()) {_, _, newVal ->
        adapter.clear()
        adapter.addAll(newVal)
    }

    constructor(context: Context) : super(context) { init(context) }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { init(context) }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) { init(context) }

    private fun init(ctx: Context) {
        setTokenizer(CommaTokenizer())
        adapter = FixedSizeStringAdapter(ctx, items.toMutableList())
        setAdapter(adapter)
        setSingleLine(true)
        maxLines = 1

        onTextChanged {
            listener.invoke(it.split(",").map { it.trim() }.filter { it.isNotEmpty() })
        }
    }

    fun onItemChanged(listener: OnItemChangedListener) {
        this.listener = listener
    }
}