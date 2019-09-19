package com.minapp.android.test.ui.base

import android.content.Context
import android.widget.ArrayAdapter
import com.minapp.android.test.R

class StringArrayAdapter(context: Context, objects: MutableList<String>) : ArrayAdapter<String>(
    context,
    R.layout.simple_array_adapter_dropdown,
    R.id.textView,
    objects
)