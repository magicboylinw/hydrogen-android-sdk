package com.minapp.android.example.database.list.datasource

import androidx.paging.DataSource
import com.minapp.android.example.database.dao.Horse

class HorseDataSourceFactory: DataSource.Factory<Long, Horse>() {

    override fun create(): DataSource<Long, Horse> {
        return HorseDataSource()
    }
}