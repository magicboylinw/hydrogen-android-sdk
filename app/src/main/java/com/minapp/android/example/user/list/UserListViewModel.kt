package com.minapp.android.example.user.list

import androidx.paging.LivePagedListBuilder
import com.minapp.android.example.Const
import com.minapp.android.example.base.BaseViewModel
import com.minapp.android.example.user.list.datasource.UserDataSourceFactory

class UserListViewModel: BaseViewModel() {

    val data = LivePagedListBuilder(UserDataSourceFactory(), Const.DATA_SOURCE_CONFIG).build()

}