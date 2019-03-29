package com.minapp.android.example.content.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.minapp.android.example.R
import com.minapp.android.example.base.BaseActivity
import com.minapp.android.example.base.StringArrayAdapter
import com.minapp.android.example.content.edit.EditActivity
import kotlinx.android.synthetic.main.activity_content_list.*

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
            data.observe(this@ContentListActivity, Observer { adapter.submitList(it) })
            contentGroup.observe(this@ContentListActivity, Observer {
                if (it != null) {
                    groupSpinner.adapter = StringArrayAdapter(this@ContentListActivity, it.map { it.name!! }.toMutableList())
                    groupSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {}

                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            viewModel.onGroupSelected(position)
                        }
                    }
                }
            })
            openContent.observe(this@ContentListActivity, Observer {
                it?.also { EditActivity.startActivity(it, this@ContentListActivity) }
            })
        }
        this.viewModel = viewModel

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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