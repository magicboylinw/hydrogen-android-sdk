package com.minapp.android.test.ui.query

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.chip.Chip
import com.minapp.android.example.util.TextPanelDialogFragment
import com.minapp.android.sdk.Global
import com.minapp.android.test.R
import com.minapp.android.test.ext.autoDisposable
import com.minapp.android.test.ext.onTextChanged
import com.minapp.android.test.ext.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_query.*

class QueryFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_query, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vm = ViewModelProviders.of(this)[QueryViewModel::class.java]

        vm.table = tableEt.text.toString()
        tableEt.onTextChanged { vm.table = it }
        totalCount.setOnCheckedChangeListener { _, isChecked ->
            vm.withTotalCount = isChecked }

        queryBtn.setOnClickListener {
            vm.query()
                .map { Global.gsonPrint().toJson(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(this)
                .subscribe({
                    TextPanelDialogFragment.create(it).show(childFragmentManager, null)

                }, { toast(it.message) })
        }
    }
}