package com.minapp.android.test.ui.content.list

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.minapp.android.test.ui.content.edit.EditFragment
import com.minapp.android.test.R
import com.minapp.android.test.ui.base.BaseFragment
import com.minapp.android.test.ui.base.StringArrayAdapter
import kotlinx.android.synthetic.main.fragment_content.*

class ContentFragment : BaseFragment() {

    private var viewModel: ListViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = provideViewModel(ListViewModel::class.java)
        val adapter = Adapter(viewModel)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        viewModel.apply {
            data.observe(this@ContentFragment, Observer { adapter.submitList(it) })
            contentGroup.observe(this@ContentFragment, Observer {
                if (it != null) {
                    groupSpinner.adapter = StringArrayAdapter(requireContext(), it.map { it.name!! }.toMutableList())
                    groupSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {}

                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            viewModel.onGroupSelected(position)
                        }
                    }
                }
            })
        }
        this.viewModel = viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.fragment_content_list, menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.edit -> {
                viewModel?.selectedItems?.firstOrNull()?.also {
                    findNavController().navigate(ContentFragmentDirections.actionNavContentToNavContentDetail(it))
                }
            }

            else -> return return super.onOptionsItemSelected(item)
        }
        return true
    }
}