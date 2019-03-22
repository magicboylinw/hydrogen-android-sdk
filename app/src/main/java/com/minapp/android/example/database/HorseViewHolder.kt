package com.minapp.android.example.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.minapp.android.example.R

class HorseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var data: Horse? = null

    fun onBind(data: Horse?) {
        this.data = data

        itemView.findViewById<CheckBox>(R.id.checkbox).apply {
            isChecked = this@HorseViewHolder.data?.checked ?: false
            setOnCheckedChangeListener { v, isChecked ->
                this@HorseViewHolder.data?.checked = isChecked
            }
        }

        itemView.findViewById<TextView>(R.id.nameTv).apply {
            text = this@HorseViewHolder.data?.name
        }
    }

    companion object {
        fun create(parent: ViewGroup): HorseViewHolder {
            return HorseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_horse, parent, false))
        }
    }
}