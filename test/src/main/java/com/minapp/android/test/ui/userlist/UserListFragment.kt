package com.minapp.android.test.ui.userlist

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ifanr.activitys.core.arch.Action
import com.minapp.android.example.user.list.UserListViewModel
import com.minapp.android.example.user.list.rv.UserListAdapter
import com.minapp.android.example.util.TextPanelDialogFragment
import com.minapp.android.test.R
import com.minapp.android.test.ui.base.BaseFragment
import com.minapp.android.test.ui.widget.InputTextDialog
import kotlinx.android.synthetic.main.fragment_userlist.*

class UserListFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_userlist, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
    }

    private fun initList() {
        val viewModel = provideViewModel(UserListViewModel::class.java)
        val adapter = UserListAdapter(viewModel)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter
        viewModel.apply {
            data.observe(this@UserListFragment, Observer { adapter.submitList(it) })
            userDetail.observe(this@UserListFragment, Observer {
                it?.also { TextPanelDialogFragment.create(it).show(childFragmentManager, null) }
            })
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.fragment_user_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filterById -> {
                InputTextDialog(requireContext(), "123") {
                    Log.d(TAG, it)
                }.show()
                true
            }

            else -> false
        }
    }

    companion object {
        private const val TAG = "UserListFragment"
    }
}