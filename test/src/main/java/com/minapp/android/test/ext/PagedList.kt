package com.minapp.android.test.ext

import android.os.Handler
import android.os.Looper
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.minapp.android.test.Const
import java.util.concurrent.Executor
import java.util.concurrent.Executors

val notifyExecutor: Executor by lazy { MainThreadExecutor() }
val fetchExecutor: Executor by lazy { Executors.newSingleThreadExecutor() }

fun <KEY, VALUE> createPagedList(dataSource: DataSource<KEY, VALUE>) =
    PagedList.Builder(dataSource, Const.DATA_SOURCE_CONFIG)
        .setNotifyExecutor(notifyExecutor)
        .setFetchExecutor(fetchExecutor)
        .build()


class MainThreadExecutor: Executor {

    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override fun execute(command: Runnable?) {
        handler.post(command)
    }
}