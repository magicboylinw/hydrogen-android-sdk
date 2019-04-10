package com.minapp.android.example.file.list

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.minapp.android.example.R
import com.minapp.android.example.base.BasePagedListAdapter
import com.minapp.android.example.base.ViewHolderBinder
import com.minapp.android.sdk.storage.CloudFile

class Adapter(
    private val viewModel: ListViewModel
): BasePagedListAdapter<CloudFile>(R.layout.item_file, FileViewHolderBinder(viewModel))

class FileViewHolderBinder(
    private val viewModel: ListViewModel
): ViewHolderBinder<CloudFile> {

    override fun onBind(itemView: View, t: CloudFile?) {

        val view = View.OnClickListener { t?.let { t.id }?.also { viewModel.view(it) } }

        itemView.findViewById<TextView>(R.id.nameTv).apply {
            text = t?.name
            setOnClickListener(view)
        }
        itemView.findViewById<TextView>(R.id.typeTv).apply {
            text = t?.mimeType
            setOnClickListener(view)
        }

        var img = ""
        if (t?.mimeType?.startsWith("image") == true) {
            img = t.path ?: ""
        }
        itemView.findViewById<ImageView>(R.id.coverIv).apply {
            Glide.with(itemView).load(img).into(this)
            setOnClickListener(view)
        }

        itemView.findViewById<CheckBox>(R.id.checkbox).apply {
            isChecked = viewModel.selected.contains(t?.id)
            setOnCheckedChangeListener { v, isChecked ->
                if (isChecked)
                    viewModel.selected.add(t?.id)
                else
                    viewModel.selected.remove(t?.id)
            }
        }
    }

}