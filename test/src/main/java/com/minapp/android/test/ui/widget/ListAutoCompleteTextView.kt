package com.minapp.android.test.ui.widget

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.AttributeSet
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import com.minapp.android.test.ui.base.FixedSizeStringAdapter
import kotlin.properties.Delegates

class ListAutoCompleteTextView: AutoCompleteTextView {

    var items: List<String> by Delegates.observable(listOf()) { _, _, newVal ->
        adapter.clear()
        adapter.addAll(newVal)
    }
    private lateinit var adapter: ArrayAdapter<String>

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

    @RequiresApi(Build.VERSION_CODES.N)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int,
        popupTheme: Resources.Theme?
    ) : super(context, attrs, defStyleAttr, defStyleRes, popupTheme) { init(context) }


    private fun init(ctx: Context) {
        adapter = FixedSizeStringAdapter(ctx, items.toMutableList())
        setAdapter(adapter)
        setSingleLine(true)
        maxLines = 1
    }

}