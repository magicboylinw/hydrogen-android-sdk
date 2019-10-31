package com.minapp.android.test.ui.base

import android.app.Dialog
import android.app.ProgressDialog
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

open class BaseFragment: Fragment() {

    private var loadingDialog: Dialog? = null

    protected fun <T : BaseViewModel> provideViewModel(clz: Class<T>): T {
        return ViewModelProviders.of(this)[clz].apply {
            this.loadingDialog.observe(this@BaseFragment, Observer {
                if (it == true) {
                    showLoadingDialog()
                } else {
                    dismissLoadingDialog()
                }
            })
            this.toast.observe(this@BaseFragment, Observer {
                if (it != null) {
                    Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                }
            })
            this.opToast.observe(this@BaseFragment, Observer {
                if (it == true) {
                    Toast.makeText(requireActivity(), "操作成功", Toast.LENGTH_SHORT).show()
                } else if (it == false) {
                    Toast.makeText(requireActivity(), "操作失败", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = ProgressDialog(requireActivity()).apply {
                setMessage("加载中，请稍等...")
                setCanceledOnTouchOutside(false)
                show()
            }
        }
    }

    private fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }
}