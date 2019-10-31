package com.minapp.android.test.ui.file.list

import androidx.paging.DataSource
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.storage.Storage
import com.minapp.android.sdk.storage.CloudFile
import com.minapp.android.sdk.util.PagedList
import com.minapp.android.test.ui.base.BasePageKeyedDataSource

class FileDataSource(
    private val viewModel: ListViewModel
): BasePageKeyedDataSource<CloudFile>() {

    override fun loadInitial(query: Query): PagedList<CloudFile>? {
        query.putAll(viewModel.query)
        return Storage.files(query)
    }

    override fun loadAfter(query: Query): PagedList<CloudFile>? {
        query.putAll(viewModel.query)
        return Storage.files(query)
    }

    class Factory(
        private val viewModel: ListViewModel
    ) : DataSource.Factory<Int, CloudFile>() {

        override fun create(): DataSource<Int, CloudFile> {
            return FileDataSource(viewModel).apply {
                addInvalidatedCallback { viewModel.selected.clear() }
            }
        }
    }
}