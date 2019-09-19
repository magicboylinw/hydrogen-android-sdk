package com.minapp.android.test.ui.content.list

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.minapp.android.sdk.content.Content
import com.minapp.android.test.R
import com.minapp.android.test.ui.base.BasePagedListAdapter
import com.minapp.android.test.ui.base.ViewHolderBinder

class Adapter(
    private val viewModel: ListViewModel
): BasePagedListAdapter<Content>(R.layout.item_content_meta, ViewHolder(viewModel)) {

    class ViewHolder(
        private val viewModel: ListViewModel
    ): ViewHolderBinder<Content> {

        override fun onBind(itemView: View, t: Content?) {
            itemView.findViewById<TextView>(R.id.titleTv).text = t?.title
            itemView.findViewById<TextView>(R.id.descTv).text = t?.description
            Glide.with(itemView).load(t?.cover).into(itemView.findViewById(R.id.coverIv))
            itemView.findViewById<CheckBox>(R.id.checkbox).setOnCheckedChangeListener { v, isChecked ->
                if (isChecked) {
                    t?.id?.also { viewModel.selectedItems.add(it) }
                } else {
                    t?.id?.also { viewModel.selectedItems.remove(it) }
                }
            }
            itemView.setOnClickListener {  v ->
                t?.let { t.id }?.also {
                    v.findNavController().navigate(ContentFragmentDirections.actionNavContentToNavContentDetail(it))
                }
            }
        }
    }
}