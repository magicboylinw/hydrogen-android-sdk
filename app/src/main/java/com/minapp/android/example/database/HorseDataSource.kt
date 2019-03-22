package com.minapp.android.example.database

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.minapp.android.example.Const
import com.minapp.android.sdk.database.Database
import com.minapp.android.sdk.database.TableObject
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.database.query.Result

class HorseDataSource: PageKeyedDataSource<Long, Horse>() {

    private val table = TableObject("my_horses")

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Horse>) {
        try {
            val offset = 0L
            val limit = params.requestedLoadSize.toLong()
            val query = Query().offset(offset).limit(limit)

            val result = table.query(query)
            val data = result.records.map { Horse(it) }
            val totalCount = result.totalCount.toInt()
            val nextPage = if (result.totalCount > limit) limit else null

            callback.onResult(data, offset.toInt(), totalCount, null, nextPage)
        } catch (e: Exception) {
            Log.e(Const.TAG, e.message, e)
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Horse>) {
        try {
            val offset = 0L
            val limit = params.key
            val query = Query().offset(offset).limit(limit)
            val result = table.query(query)
            callback.onResult(result.records.map { Horse(it) }, if (result.totalCount > limit) limit else null)
        } catch (e: Exception) {
            Log.e(Const.TAG, e.message, e)
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Horse>) {
        callback.onResult(emptyList(), null)
    }

}