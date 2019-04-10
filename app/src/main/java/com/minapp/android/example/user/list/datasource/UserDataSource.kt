package com.minapp.android.example.user.list.datasource

import com.minapp.android.example.base.BasePageKeyedDataSource
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.user.User
import com.minapp.android.sdk.user.Users
import com.minapp.android.sdk.util.PagedList

class UserDataSource: BasePageKeyedDataSource<User>() {

    override fun loadInitial(query: Query): PagedList<User>? {
        return Users.users(query)
    }

    override fun loadAfter(query: Query): PagedList<User>? {
        return Users.users(query)
    }
}