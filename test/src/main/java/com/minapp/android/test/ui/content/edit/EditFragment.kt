package com.minapp.android.test.ui.content.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.minapp.android.test.R
import com.minapp.android.test.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_content_edit.*

class EditFragment: BaseFragment() {

    private val args: EditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_content_edit, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideViewModel(EditViewModel::class.java).apply {
            data.observe(this@EditFragment, Observer {
                titleTv.text = it.title
                descTv.text = it.description
                contentTv.text = it.content
                Glide.with(this@EditFragment).load(it.cover).into(coverIv)
            })

            init(args.id)
        }
    }
}