package com.minapp.android.example.database.list

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProviders
import com.minapp.android.example.R
import com.minapp.android.example.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_query.*

class QueryDialogFragment: AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val viewModel = ViewModelProviders.of(activity!!)[ListViewModel::class.java]
        return object: BaseDialog(context) {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.dialog_query)

                nameTv.setText(viewModel.query.name)
                ageTv.setText(viewModel.query.age?.toString())

                resetBtn.setOnClickListener {
                    nameTv.text = null
                    ageTv.text = null
                }

                saveBtn.setOnClickListener {
                    viewModel.query.name = nameTv.text.toString().trim()
                    viewModel.query.age = runCatching { ageTv.text.toString().toInt() }.getOrElse { viewModel.query.age }
                    viewModel.onRefresh()
                    dismiss()
                }
            }
        }
    }
}