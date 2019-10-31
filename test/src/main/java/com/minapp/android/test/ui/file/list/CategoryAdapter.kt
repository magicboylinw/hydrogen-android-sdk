package com.minapp.android.test.ui.file.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.minapp.android.sdk.storage.FileCategory
import com.minapp.android.test.R

class CategoryAdapter(context: Context) : ArrayAdapter<FileCategory>(context, 0) {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(getItem(position), convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(getItem(position), convertView, parent)
    }

    private fun createItemView(data: FileCategory?, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.file_category_dropdown, parent, false)

        view.findViewById<TextView>(R.id.categoryTv).text = data?.name

        return view
    }

    companion object {
        val NONE_CATEGORY = FileCategory().apply {
            name = "全部"
        }
    }
}
