package com.minapp.android.example.database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minapp.android.example.R
import kotlinx.android.synthetic.main.activity_db.*

class DBActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db)
        initPagedList()
    }

    fun initPagedList() {
        ViewModelProviders.of(this)[DBViewModel::class.java].apply {
            refresh.setOnRefreshListener { onRefresh() }

            val adapter = HorseAdapter()
            rv.layoutManager = LinearLayoutManager(this@DBActivity, RecyclerView.VERTICAL, false)
            rv.adapter = adapter
            horses.observe(this@DBActivity, Observer {
                adapter.submitList(it)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.db_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.add -> {

            }

            R.id.edit -> {

            }

            R.id.delete -> {

            }

            R.id.filter -> {

            }
        }
        return true
    }
}
