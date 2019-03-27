package com.minapp.android.example.content.list

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import com.minapp.android.example.Const
import com.minapp.android.example.base.BaseViewModel
import kotlinx.coroutines.launch

class ListViewModel: BaseViewModel() {

    val categoryId = MutableLiveData<String>()
    val groupId = MutableLiveData<String>()
    val data = LivePagedListBuilder(DataSource.Factory(this), Const.DATA_SOURCE_CONFIG).build()
    val selectedItems = hashSetOf<String>()

}