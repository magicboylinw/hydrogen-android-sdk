package com.minapp.android.example.file.list

import androidx.paging.LivePagedListBuilder
import com.minapp.android.example.Const
import com.minapp.android.example.base.BaseViewModel

class ListViewModel: BaseViewModel() {

    val data = LivePagedListBuilder(FileDataSource.Factory(), Const.DATA_SOURCE_CONFIG).build()

}