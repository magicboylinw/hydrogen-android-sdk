package com.minapp.android.example.user.list.datasource

import androidx.paging.DataSource
import com.minapp.android.sdk.user.User

class UserDataSourceFactory: DataSource.Factory<Int, User>() {

    override fun create(): DataSource<Int, User> {
        return UserDataSource()
    }
}