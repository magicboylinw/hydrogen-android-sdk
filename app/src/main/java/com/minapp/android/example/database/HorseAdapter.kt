package com.minapp.android.example.database

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class HorseAdapter : PagedListAdapter<Horse, RecyclerView.ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HorseViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HorseViewHolder -> holder.onBind(getItem(position))
        }
    }

}

class DiffCallback: DiffUtil.ItemCallback<Horse>() {

    override fun areItemsTheSame(oldItem: Horse, newItem: Horse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Horse, newItem: Horse): Boolean {
        return true
    }

}