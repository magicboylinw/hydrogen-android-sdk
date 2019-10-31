package com.minapp.android.test.ui.userlist.datasource

import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.user.User
import com.minapp.android.sdk.user.Users
import com.minapp.android.sdk.util.PagedList
import com.minapp.android.test.ui.base.BasePageKeyedDataSource

class UserDataSource: BasePageKeyedDataSource<User>() {

    override fun loadInitial(query: Query): PagedList<User>? {
        return Users.users(query)
    }

    override fun loadAfter(query: Query): PagedList<User>? {
        return Users.users(query)
    }
}