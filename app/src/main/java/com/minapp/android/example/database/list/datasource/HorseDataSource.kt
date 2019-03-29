package com.minapp.android.example.database.list.datasource

import com.minapp.android.example.base.BasePageKeyedDataSource
import com.minapp.android.example.database.dao.Horse
import com.minapp.android.example.database.list.ListViewModel
import com.minapp.android.sdk.database.query.BaseQuery
import com.minapp.android.sdk.database.query.Where
import com.minapp.android.sdk.util.PagedList

class HorseDataSource(
    private val viewModel: ListViewModel
): BasePageKeyedDataSource<Horse>() {

    override fun loadInitial(query: BaseQuery): PagedList<Horse> {
        val where = Where().apply {
            viewModel.query.name?.takeIf { !it.isNullOrEmpty() }?.also { eq(Horse.NAME, it) }
            viewModel.query.age?.also { eq(Horse.AGE, it) }
        }
        query.put(where)
        return Horse.query(Horse(), query)
    }

    override fun loadAfter(query: BaseQuery): PagedList<Horse> {
        val where = Where().apply {
            viewModel.query.name?.takeIf { !it.isNullOrEmpty() }?.also { eq(Horse.NAME, it) }
            viewModel.query.age?.also { eq(Horse.AGE, it) }
        }
        query.put(where)
        return Horse.query(Horse(), query)
    }
}