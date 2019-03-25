package com.minapp.android.example.database.edit

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.minapp.android.example.base.BaseActivity
import com.minapp.android.example.R
import com.minapp.android.example.database.dao.Horse
import com.minapp.android.example.util.Util

import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A login screen that offers login via email/password.
 */
class EditActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        init()
    }

    fun init() {
        provideViewModel(EditViewModel::class.java).apply {
            name.observe(this@EditActivity, Observer { nameTv.setText(it) })
            age.observe(this@EditActivity, Observer { ageTv.setText(it?.toString() ?: null) })
            btnText.observe(this@EditActivity, Observer {
                createOrUpdateBtn.text = it
                supportActionBar?.title = it
            })
            btnEnabled.observe(this@EditActivity, Observer {
                if (it == true) {
                    createOrUpdateBtn.isEnabled = true
                } else if (it == false) {
                    createOrUpdateBtn.isEnabled = false
                }
            })
            close.observe(this@EditActivity, Observer {
                if (it == true) {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            })
            createOrUpdateBtn.setOnClickListener {
                name.value = nameTv.text.toString()
                age.value = runCatching { ageTv.text.toString().toInt() }.getOrNull()
                save()
            }
            init(intent.getStringExtra(ID))
        }
    }

    companion object {

        const val ID = "ID"

        fun createEntity(activity: AppCompatActivity, code: Int) {
            activity.startActivityForResult(Intent(activity, EditActivity::class.java), code)
        }

        fun editEntity(activity: AppCompatActivity, code: Int, id: String) {
            activity.startActivityForResult(Intent(activity, EditActivity::class.java).apply { putExtra(
                ID, id) }, code)
        }
    }
}
