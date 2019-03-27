package com.minapp.android.example.file.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.minapp.android.example.R
import com.minapp.android.example.base.BaseActivity
import kotlinx.android.synthetic.main.activity_file_list.*

class FileListActivity: BaseActivity() {

    private var viewModel: ListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_list)
        initList()
    }

    fun initList() {
        val adapter = Adapter()
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        val viewModel = provideViewModel(ListViewModel::class.java).apply {
            data.observe(this@FileListActivity, Observer { adapter.submitList(it) })
        }
        this.viewModel = viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_file_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.add -> {

            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return true
    }
}