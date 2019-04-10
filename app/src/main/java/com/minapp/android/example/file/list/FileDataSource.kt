package com.minapp.android.example.file.list

import androidx.paging.DataSource
import com.minapp.android.example.base.BasePageKeyedDataSource
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.storage.Storage
import com.minapp.android.sdk.storage.UploadedFile
import com.minapp.android.sdk.util.PagedList

class FileDataSource(
    private val viewModel: ListViewModel
): BasePageKeyedDataSource<UploadedFile>() {

    override fun loadInitial(query: Query): PagedList<UploadedFile>? {
        query.putAll(viewModel.query)
        return Storage.files(query)
    }

    override fun loadAfter(query: Query): PagedList<UploadedFile>? {
        query.putAll(viewModel.query)
        return Storage.files(query)
    }

    class Factory(
        private val viewModel: ListViewModel
    ) : DataSource.Factory<Int, UploadedFile>() {

        override fun create(): DataSource<Int, UploadedFile> {
            return FileDataSource(viewModel).apply {
                addInvalidatedCallback { viewModel.selected.clear() }
            }
        }
    }
}