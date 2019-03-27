package com.minapp.android.example.file.list

import androidx.paging.DataSource
import com.minapp.android.example.base.BasePageKeyedDataSource
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.storage.Storage
import com.minapp.android.sdk.storage.model.UploadedFile
import com.minapp.android.sdk.util.PagedList

class FileDataSource: BasePageKeyedDataSource<UploadedFile>() {

    override fun loadInitial(query: Query): PagedList<UploadedFile>? {
        return Storage.files(query)
    }

    override fun loadAfter(query: Query): PagedList<UploadedFile>? {
        return Storage.files(query)
    }

    class Factory : DataSource.Factory<Int, UploadedFile>() {
        override fun create(): DataSource<Int, UploadedFile> {
            return FileDataSource()
        }
    }
}