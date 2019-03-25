package com.minapp.android.example.database.list.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.minapp.android.example.R
import com.minapp.android.example.database.dao.Horse

class HorseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var data: Horse? = null

    fun onBind(data: Horse?) {
        this.data = data

        itemView.findViewById<CheckBox>(R.id.checkbox).apply {
            isChecked = this@HorseViewHolder.data?.checked ?: false
        }

        itemView.findViewById<TextView>(R.id.nameTv).apply {
            text = this@HorseViewHolder.data?.name
        }
    }

    companion object {
        fun create(parent: ViewGroup): HorseViewHolder {
            return HorseViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_horse,
                parent,
                false)
            ).apply {
                itemView.findViewById<CheckBox>(R.id.checkbox).setOnCheckedChangeListener { v, isChecked ->
                    data?.checked = isChecked
                }
            }
        }
    }
}