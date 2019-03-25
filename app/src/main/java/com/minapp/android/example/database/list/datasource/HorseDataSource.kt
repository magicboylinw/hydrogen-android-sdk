package com.minapp.android.example.database.list.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.minapp.android.example.Const
import com.minapp.android.example.database.dao.Horse
import com.minapp.android.sdk.database.query.Query

class HorseDataSource: PageKeyedDataSource<Long, Horse>() {

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Horse>) {
        try {
            val offset = 0L
            val limit = params.requestedLoadSize.toLong()
            val query = Query().offset(offset).limit(limit)

            val result = Horse.query(Horse(), query)
            val totalCount = result.totalCount.toInt()
            val nextPage = if (result.totalCount > limit + offset) limit + offset else null

            callback.onResult(result.records.toMutableList(), offset.toInt(), totalCount, null, nextPage)
        } catch (e: Exception) {
            Log.e(Const.TAG, e.message, e)
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Horse>) {
        try {
            val offset = params.key
            val limit = params.requestedLoadSize.toLong()

            val query = Query().offset(offset).limit(limit)
            val result = Horse.query(Horse(), query)
            val totalCount = result.totalCount
            val nextPage = if (result.totalCount > limit + offset) limit + offset else null

            callback.onResult(result.records, nextPage)
        } catch (e: Exception) {
            Log.e(Const.TAG, e.message, e)
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Horse>) {
        callback.onResult(emptyList(), null)
    }

}