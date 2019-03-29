package com.minapp.android.example.file.list

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.minapp.android.example.Const
import com.minapp.android.example.R
import com.minapp.android.example.base.BaseActivity
import com.minapp.android.example.util.Glide4Engine
import com.minapp.android.example.util.TextPanelDialogFragment
import com.minapp.android.example.util.Util
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.activity_file_list.*

class FileListActivity: BaseActivity() {

    private var viewModel: ListViewModel? = null
    private var pickMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_list)

        if (intent.action == ACTION_PICK) {
            pickMode = true
        }
        init()
    }

    fun init() {
        val viewModel = provideViewModel(ListViewModel::class.java)
        val adapter = Adapter(viewModel)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        val categoryAdapter = CategoryAdapter(this).apply {
            add(CategoryAdapter.NONE_CATEGORY)
        }
        categorySpinner.adapter = categoryAdapter
        categorySpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.onCategorySelected(position)
            }
        }


        viewModel.apply {
            data.observe(this@FileListActivity, Observer { adapter.submitList(it) })
            fileSelected.observe(this@FileListActivity, Observer {
                if (pickMode && it != null) {
                    setResult(Activity.RESULT_OK, Intent().apply {
                        putExtra(FILE_SELECTED, it.id)
                    })
                    finish()
                }
            })
            showTextPanel.observe(this@FileListActivity, Observer {
                if (it != null) {
                    TextPanelDialogFragment.create(it).show(supportFragmentManager, null)
                }
            })
            categories.observe(this@FileListActivity, Observer {
                if (it != null) {
                    categoryAdapter.clear()
                    categoryAdapter.addAll(it)
                }
            })
            this@FileListActivity.viewModel = this
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (!pickMode) {
            menuInflater.inflate(R.menu.activity_file_list, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.add -> {
                if (Util.checkPermission(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))) {
                    openImgPicker()
                } else {
                    Util.requestPermission(this, REQ_PICK_IMG, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
                }
            }

            R.id.delete -> viewModel?.delete()

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQ_PICK_IMG -> {
                if (Util.permissionGranted(grantResults)) {
                    openImgPicker()
                }
            }

            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    fun openImgPicker() {
        if (Util.checkPermission(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))) {
            Matisse.from(this)
                .choose(mutableSetOf(MimeType.JPEG, MimeType.PNG, MimeType.GIF, MimeType.BMP))
                .imageEngine(Glide4Engine())
                .maxSelectable(1)
                .showSingleMediaType(true)
                .forResult(REQ_PICK_IMG)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_PICK_IMG -> {
                if (resultCode == Activity.RESULT_OK) {
                    Matisse.obtainPathResult(data)?.takeIf { it.isNotEmpty() }?.let { it[0] }?.also { viewModel?.upload(it) }
                }
            }

            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    companion object {
        const val FILE_SELECTED = "FILE_SELECTED"

        const val ACTION_PICK = "minapp.example.file.pick"

        const val REQ_PICK_IMG = 9

        fun getFileIdOnResult(intent: Intent?): String? {
            return intent?.let { it.getStringExtra(FILE_SELECTED) }
        }

        fun pickFile(requestCode: Int, activity: Activity) {
            activity.startActivityForResult(Intent().apply {
                action = ACTION_PICK
                addCategory(Const.CATEGORY_APP)
            }, requestCode)
        }
    }
}