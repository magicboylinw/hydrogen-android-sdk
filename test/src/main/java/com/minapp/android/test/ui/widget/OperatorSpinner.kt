package com.minapp.android.test.ui.widget

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import com.minapp.android.sdk.database.query.WhereOperator
import com.minapp.android.test.R
import com.minapp.android.test.ui.base.StringAdapter

class OperatorSpinner: Spinner {

    private var listener: OnOperatorSelectedListener = object : OnOperatorSelectedListener {
        override fun onOperatorSelected(operator: WhereOperator) {}
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
        adapter = StringAdapter(OPERATORS.map { it.first }, ctx)
        onItemSelectedListener = object: OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                listener.onOperatorSelected(OPERATORS[position].second)
            }
        }
    }

    fun onOperatorChanged(l: (WhereOperator) -> Unit) {
        listener = object: OnOperatorSelectedListener {
            override fun onOperatorSelected(operator: WhereOperator) {
                l.invoke(operator)
            }
        }
    }

    interface OnOperatorSelectedListener {
        fun onOperatorSelected(operator: WhereOperator)
    }

    companion object {
        private val OPERATORS = listOf(
            "  =  " to WhereOperator.EQ,
            "  !=  " to WhereOperator.NE,
            "  <  " to WhereOperator.LT,
            "  <=  " to WhereOperator.LTE,
            "  >  " to WhereOperator.GT,
            "  >=  " to WhereOperator.GTE,
            "is null" to WhereOperator.IS_NULL,
            "exists" to WhereOperator.EXISTS,
            "has key" to WhereOperator.HAS_KEY
        )
    }
}