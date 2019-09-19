package com.minapp.android.test.ui.widget

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.minapp.android.example.base.BaseDialog
import com.minapp.android.test.R
import kotlinx.android.synthetic.main.dialog_input_text.*

class InputTextDialog(
    context: Context,
    private val initVal: String = "",
    private val callback: (String) -> Unit = {}
) : BaseDialog(context) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_input_text)

        et.also {
            it.setText(initVal)
            it.selectAll()
            it.setOnEditorActionListener { v, actionId, event ->
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        dismiss()
                        callback.invoke(it.text.toString())
                        return@setOnEditorActionListener true
                    }

                    else -> return@setOnEditorActionListener false
                }
            }
        }
    }
}