package com.minapp.android.example.database.list.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.minapp.android.example.Const
import com.minapp.android.example.base.BasePageKeyedDataSource
import com.minapp.android.example.database.dao.Horse
import com.minapp.android.example.database.list.ListViewModel
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.util.PagedList

class HorseDataSource(
    private val viewModel: ListViewModel
): BasePageKeyedDataSource<Horse>() {

    override fun loadInitial(query: Query): PagedList<Horse> {
        viewModel.query.name?.takeIf { !it.isNullOrEmpty() }?.also { query.eq(Horse.NAME, it) }
        viewModel.query.age?.also { query.eq(Horse.AGE, it) }
        return Horse.query(Horse(), query)
    }

    override fun loadAfter(query: Query): PagedList<Horse> {
        viewModel.query.name?.takeIf { !it.isNullOrEmpty() }?.also { query.eq(Horse.NAME, it) }
        viewModel.query.age?.also { query.eq(Horse.AGE, it) }
        return Horse.query(Horse(), query)
    }
}