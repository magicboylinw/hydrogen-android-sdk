package com.minapp.android.example.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Config
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class DBViewModel: ViewModel() {

    val horses: LiveData<PagedList<Horse>> = LivePagedListBuilder(HorseDataSourceFactory(), Config(
        pageSize = 15,
        prefetchDistance = 5,
        enablePlaceholders = false
    )).build()

    fun onRefresh() {}
}