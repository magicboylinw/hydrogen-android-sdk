package com.minapp.android.example.base

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.minapp.android.example.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseActivity: AppCompatActivity() {

    private val job = Job()
    protected val activityScope = CoroutineScope(job + Dispatchers.IO)
    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = Util.loadingDialog(this)
        }
    }

    fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    fun <T : BaseViewModel> provideViewModel(clz: Class<T>): T {
        return ViewModelProviders.of(this)[clz].apply {
            this.loadingDialog.observe(this@BaseActivity, Observer {
                if (it == true) {
                    showLoadingDialog()
                } else {
                    dismissLoadingDialog()
                }
            })
            this.toast.observe(this@BaseActivity, Observer {
                if (it != null) {
                    Util.toast(this@BaseActivity, it)
                }
            })
            this.opToast.observe(this@BaseActivity, Observer {
                if (it == true) {
                    Util.toastSuccess(this@BaseActivity)
                } else if (it == false) {
                    Util.toastFailure(this@BaseActivity)
                }
            })
            this.startActivity.observe(this@BaseActivity, Observer {
                it?.also {
                    startActivity(Intent(this@BaseActivity, it))
                }
            })
            this.startActivityForResult.observe(this@BaseActivity, Observer {
                it?.also {
                    startActivityForResult(Intent(this@BaseActivity, it.first), it.second)
                }
            })
            this.closeActivity.observe(this@BaseActivity, Observer {
                if (it == true) {
                    finish()
                }
            })
        }
    }
}