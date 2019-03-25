package com.minapp.android.example.database.list

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Config
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.minapp.android.example.Const
import com.minapp.android.example.base.BaseViewModel
import com.minapp.android.example.database.dao.Horse
import com.minapp.android.example.database.edit.EditActivity
import com.minapp.android.example.database.list.datasource.HorseDataSourceFactory
import kotlinx.coroutines.launch

class ListViewModel: BaseViewModel() {

    val horses: LiveData<PagedList<Horse>> = LivePagedListBuilder(
        HorseDataSourceFactory(), Config(
            pageSize = 15,
            initialLoadSizeHint = 15,
            prefetchDistance = 5,
            enablePlaceholders = false
    )).build()

    val editAction = MutableLiveData<String>()
    val addAction = MutableLiveData<Boolean>()

    fun onRefresh() {
        horses.value?.dataSource?.invalidate()
    }

    fun onAdd() {
        addAction.value = true
    }

    fun onEdit() {
        ioScope.launch {
            horses.value?.snapshot()?.firstOrNull() { it.checked }?.also { editAction.postValue(it.id) }
        }
    }

    fun onDelete() {
        ioScope.launch {
            horses.value?.snapshot()?.filter { it.checked }?.map { it.id!! }?.takeIf { it.isNotEmpty() }?.also {
                runCatching {
                    Horse.batchDelete(it)
                    opToast.postValue(true)
                    onRefresh()
                }.onFailure {
                    toast.postValue(it.message)
                }
            }
        }
    }

    fun onFilter() {

    }
}