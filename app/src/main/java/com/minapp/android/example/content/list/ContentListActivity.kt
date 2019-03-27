package com.minapp.android.example.content.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.minapp.android.example.R
import com.minapp.android.example.base.BaseActivity
import com.minapp.android.example.content.edit.EditActivity
import kotlinx.android.synthetic.main.activity_db.*

class ContentListActivity: BaseActivity() {

    private var viewModel: ListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_list)
        initList()
    }

    fun initList() {
        val viewModel = provideViewModel(ListViewModel::class.java)
        val adapter = Adapter(viewModel)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        viewModel.apply {
            groupId.value = "1553654358303852"
            data.observe(this@ContentListActivity, Observer { adapter.submitList(it) })
        }
        this.viewModel = viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_content_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> {
                viewModel?.selectedItems?.firstOrNull()?.also {
                    EditActivity.startActivity(it, this)
                }
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return true
    }
}