package com.minapp.android.test.ui.base

import android.content.Context
import android.widget.ArrayAdapter
import com.minapp.android.test.R

class StringAdapter(objects: List<String>, ctx: Context) : ArrayAdapter<String>(
    ctx,
    R.layout.string_adapter_dropdown,
    R.id.textView,
    objects
)