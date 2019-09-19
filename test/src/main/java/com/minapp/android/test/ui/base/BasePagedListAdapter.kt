package com.minapp.android.test.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BasePagedListAdapter<T>(
    private val layout: Int,
    private val binder: ViewHolderBinder<T>
): PagedListAdapter<T, BaseViewHolder<T>>(NotEqualDiffCallback<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return BaseViewHolder.create(parent, layout, binder)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.onBind(holder.itemView, getItem(position))
    }
}

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView),
    ViewHolderBinder<T> {

    companion object {
        fun <T> create(parent: ViewGroup, layout: Int, binder: ViewHolderBinder<T>): BaseViewHolder<T> {
            val itemView = LayoutInflater.from(parent.context).inflate(layout, parent, false)
            return object : BaseViewHolder<T>(itemView) {
                override fun onBind(itemView: View, t: T?) {
                    binder.onBind(itemView, t)
                }
            }
        }
    }
}

interface ViewHolderBinder<T> {

    fun onBind(itemView: View, t: T?)

}

class NotEqualDiffCallback<T>: DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return false
    }
}