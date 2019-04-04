package com.minapp.android.example

import android.Manifest
import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.minapp.android.example.auth.AuthActivity
import com.minapp.android.example.base.BaseActivity
import com.minapp.android.example.content.list.ContentListActivity
import com.minapp.android.example.database.list.Query
import com.minapp.android.example.database.list.RecordListActivity
import com.minapp.android.example.file.list.FileListActivity
import com.minapp.android.example.user.list.UserListActivity
import com.minapp.android.example.util.Glide4Engine
import com.minapp.android.example.util.Util
import com.minapp.android.sdk.Global
import com.minapp.android.sdk.auth.Auth
import com.minapp.android.sdk.database.*
import com.minapp.android.sdk.database.query.BaseQuery
import com.minapp.android.sdk.storage.Storage
import com.minapp.android.sdk.user.Users
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import kotlin.Exception

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            activityScope.launch {
                try {
                } catch (e : Exception) {
                    Log.e(Const.TAG, e.message, e)
                }
            }
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        init()
    }

    fun init() {
        authBtn.setOnClickListener { startActivity(Intent(this, AuthActivity::class.java)) }
        dbBtn.setOnClickListener { startActivity(Intent(this, RecordListActivity::class.java)) }
        userBtn.setOnClickListener { startActivity(Intent(this, UserListActivity::class.java)) }
        contentBtn.setOnClickListener { startActivity(Intent(this, ContentListActivity::class.java)) }
        fileBtn.setOnClickListener { startActivity(Intent(this, FileListActivity::class.java)) }
    }

    fun checkClientId() {
        if (Auth.clientId().isNullOrEmpty()) {
            notificationTv.apply {
                visibility = View.VISIBLE
                text = "请先初始化 sdk：BaaS.init(...)"
            }
        } else {
            notificationTv.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        checkClientId()
    }

    fun openImagePicker() {
        Matisse.from(this)
            .choose(IMAGE_PICKER_TYPES)
            .maxSelectable(1)
            .imageEngine(Glide4Engine())
            .forResult(IMAGE_PICKER_RESULT)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            OPEN_IMAGE_PICKER -> {
                if (Util.permissionGranted(grantResults)) {
                    openImagePicker()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            IMAGE_PICKER_RESULT -> {
                if (resultCode == RESULT_OK) {
                    GlobalScope.launch {
                        runCatching {

                            val file = File(Matisse.obtainPathResult(data)[0])
                            val uploaded = Storage.uploadFile(file.name, file.readBytes())
                            val horses = Table("my_horses")
                            val qrCode = horses.createRecord().put("horse_name", "二维马").put("attachment", uploaded).save()
                            Log.d(Const.TAG, "${qrCode.getFile("attachment")?.path}")

                        }.onFailure { Log.d(Const.TAG, it.message, it) }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity inString AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    companion object {
        val TAG = "MainActivity"
        const val OPEN_IMAGE_PICKER = 9
        const val IMAGE_PICKER_RESULT = 10
        val IMAGE_PICKER_PERMISSIONS = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val IMAGE_PICKER_TYPES = setOf(MimeType.JPEG, MimeType.PNG, MimeType.GIF)
    }
}
