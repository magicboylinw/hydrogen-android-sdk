package com.minapp.android.example.file.list

import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.minapp.android.example.R
import com.minapp.android.example.base.BasePagedListAdapter
import com.minapp.android.example.base.ViewHolderBinder
import com.minapp.android.sdk.storage.model.UploadedFile

class Adapter: BasePagedListAdapter<UploadedFile>(R.layout.item_file, FileViewHolderBinder())

class FileViewHolderBinder: ViewHolderBinder<UploadedFile> {

    override fun onBind(itemView: View, t: UploadedFile?) {
        itemView.findViewById<TextView>(R.id.nameTv).text = t?.name
        itemView.findViewById<TextView>(R.id.typeTv).text = t?.mimeType

        var img = ""
        if (t?.mimeType?.startsWith("image") == true) {
            img = t.path ?: ""
        }
        Glide.with(itemView).load(img).into(itemView.findViewById(R.id.coverIv))
    }

}