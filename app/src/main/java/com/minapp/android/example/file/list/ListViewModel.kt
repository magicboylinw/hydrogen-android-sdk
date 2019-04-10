package com.minapp.android.example.file.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import com.minapp.android.example.Const
import com.minapp.android.example.base.BaseViewModel
import com.minapp.android.sdk.Global
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.database.query.Operator
import com.minapp.android.sdk.storage.FileCategory
import com.minapp.android.sdk.storage.Storage
import com.minapp.android.sdk.storage.CloudFile
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.CopyOnWriteArraySet

class ListViewModel: BaseViewModel() {

    val data = LivePagedListBuilder(FileDataSource.Factory(this), Const.DATA_SOURCE_CONFIG).build()
    val fileSelected = MutableLiveData<CloudFile>()
    val selected = CopyOnWriteArraySet<String>()
    val showTextPanel = MutableLiveData<String>()
    val categories: LiveData<List<FileCategory>> = object : LiveData<List<FileCategory>>() {
        override fun onActive() {
            ioScope.launch {
                val query = Query().apply {
                    put(Query.OFFSET, "0")
                    put(Query.LIMIT, Int.MAX_VALUE.toString())
                }
                repeat(10) {
                    try {
                        postValue(Storage.categories(query).objects.apply {
                            add(0, CategoryAdapter.NONE_CATEGORY)
                        })
                        return@repeat
                    } catch (e: Exception) {
                        delay(200)
                    }
                }
            }
        }
    }
    val query = Query()

    fun onCategorySelected(position: Int) {
        ioScope.launch {
            categories.value?.takeIf { it.size > position }?.let { it[position] }?.also {
                query.put(Operator.IN, CloudFile.QUERY_CATEGORY_ID, it.id)
                refresh()
            }
        }
    }

    fun upload(path: String) {
        ioScope.launch {
            loadingDialog.postValue(true)
            try {
                val file = File(path)
                Storage.uploadFile(file.name, query.get(Operator.IN, CloudFile.QUERY_CATEGORY_ID), file.readBytes())
                opToast.postValue(true)
                refresh()
            } catch (e: Exception) {
                Log.e(Const.TAG, e.message, e)
                toast.postValue(e.message)
            }
            loadingDialog.postValue(false)
        }
    }

    fun view(id: String) {
        ioScope.launch {
            try {
                val json = Global.gsonPrint().toJson(Storage.file(id))
                showTextPanel.postValue(json)
            } catch (e: Exception) {
                toast.postValue(e.message)
            }
        }
    }

    fun refresh() {
        data.value?.dataSource?.invalidate()
    }

    fun delete() {
        ioScope.launch {
            try {
                Storage.deleteFiles(selected)
                opToast.postValue(true)
                refresh()
            } catch (e: Exception) {
                Log.e(Const.TAG, e.message, e)
                toast.postValue(e.message)
            }
        }
    }

}