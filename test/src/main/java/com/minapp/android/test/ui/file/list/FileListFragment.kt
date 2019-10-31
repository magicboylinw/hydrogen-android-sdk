package com.minapp.android.test.ui.file.list

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.minapp.android.example.util.TextPanelDialogFragment
import com.minapp.android.test.R
import com.minapp.android.test.ext.requestPermissionIfNeed
import com.minapp.android.test.ui.base.BaseFragment
import com.minapp.android.test.util.MatisseHelper
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.fragment_file_list.*

class FileListFragment: BaseFragment() {

    private var viewModel: ListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_file_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = provideViewModel(ListViewModel::class.java)
        val adapter = Adapter(viewModel)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        val categoryAdapter = CategoryAdapter(requireContext()).apply {
            add(CategoryAdapter.NONE_CATEGORY)
        }
        categorySpinner.adapter = categoryAdapter
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.onCategorySelected(position)
            }
        }


        viewModel.apply {
            data.observe(this@FileListFragment, Observer { adapter.submitList(it) })
            fileSelected.observe(this@FileListFragment, Observer {})
            showTextPanel.observe(this@FileListFragment, Observer {
                if (it != null) {
                    TextPanelDialogFragment.create(it).show(childFragmentManager, null)
                }
            })
            categories.observe(this@FileListFragment, Observer {
                if (it != null) {
                    categoryAdapter.clear()
                    categoryAdapter.addAll(it)
                }
            })
            this@FileListFragment.viewModel = this
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.fragment_file_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                if (requestPermissionIfNeed(PERMISSIONS, PICK_IMG))
                    MatisseHelper.createImageSelector(PICK_IMG, this)
            }

            R.id.delete -> viewModel?.delete()

            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PICK_IMG -> {
                if (grantResults.all { it == PackageManager.PERMISSION_GRANTED })
                    MatisseHelper.createImageSelector(PICK_IMG, this)
            }

            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PICK_IMG -> {
                if (resultCode == Activity.RESULT_OK) {
                    Matisse.obtainPathResult(data)?.takeIf { it.isNotEmpty() }?.let { it[0] }?.also { viewModel?.upload(it) }
                }
            }

            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        private const val PICK_IMG = 9

        private val PERMISSIONS = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}