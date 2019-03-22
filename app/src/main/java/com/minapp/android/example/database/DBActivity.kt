package com.minapp.android.example.database

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.minapp.android.example.R
import kotlinx.android.synthetic.main.activity_db.*

class DBActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db)
        initPagedList()
    }

    fun initPagedList() {
    }
}
