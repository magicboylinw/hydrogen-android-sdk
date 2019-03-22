package com.minapp.android.example.database

import androidx.paging.DataSource

class HorseDataSourceFactory: DataSource.Factory<Long, Horse>() {

    override fun create(): DataSource<Long, Horse> {
        return HorseDataSource()
    }
}