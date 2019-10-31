package com.minapp.android.test.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.onTextChanged(listener: (String) -> Unit) {
    addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable) {
            listener.invoke(s.toString().trim())
        }

        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}