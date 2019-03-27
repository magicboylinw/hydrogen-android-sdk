package com.minapp.android.example.database.list.datasource

import androidx.paging.DataSource
import com.minapp.android.example.database.dao.Horse
import com.minapp.android.example.database.list.ListViewModel

class HorseDataSourceFactory(
    private val viewModel: ListViewModel
): DataSource.Factory<Int, Horse>() {

    override fun create(): DataSource<Int, Horse> {
        return HorseDataSource(viewModel)
    }
}