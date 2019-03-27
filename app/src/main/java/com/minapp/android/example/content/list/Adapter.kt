package com.minapp.android.example.content.list

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.bumptech.glide.Glide
import com.minapp.android.example.R
import com.minapp.android.example.base.BasePagedListAdapter
import com.minapp.android.example.base.ViewHolderBinder
import com.minapp.android.sdk.content.Content

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
        }
    }
}