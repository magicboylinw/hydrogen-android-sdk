package com.minapp.android.example.content.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.minapp.android.example.R
import com.minapp.android.example.base.BaseActivity
import kotlinx.android.synthetic.main.activity_content_edit.*

class EditActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_edit)
        init()
    }

    fun init() {
        provideViewModel(EditViewModel::class.java).apply {
            data.observe(this@EditActivity, Observer {
                titleTv.setText(it.title)
                descTv.setText(it.description)
                contentTv.setText(it.content)
                Glide.with(this@EditActivity).load(it.cover).into(coverIv)
            })
            init(intent.getStringExtra(ID))
        }
    }

    companion object {
        const val ID = "ID"

        fun startActivity(id: String, activity: Activity) {
            activity.startActivity(Intent(activity, EditActivity::class.java).apply { putExtra(ID, id) })
        }
    }
}