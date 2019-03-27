package com.minapp.android.example.user.list.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minapp.android.example.R
import com.minapp.android.sdk.user.User

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var data: User? = null

    fun onBind(user: User?) {
        data = user

        itemView.findViewById<CheckBox>(R.id.checkbox).apply {
            isChecked = false
        }
        itemView.findViewById<TextView>(R.id.nameTv).apply {
            text = data?.userName ?: data?.email
        }
        Glide.with(itemView).load(data?.avatar).into(itemView.findViewById(R.id.avatarIv))
    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder {
            return LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false).let { UserViewHolder(it) }
        }
    }
}