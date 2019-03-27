package com.minapp.android.example.util

import androidx.recyclerview.widget.DiffUtil

class NotEqualDiffCallback<T>: DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return false
    }
}