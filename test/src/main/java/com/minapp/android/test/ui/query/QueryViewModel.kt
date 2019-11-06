package com.minapp.android.test.ui.query

import androidx.lifecycle.ViewModel
import com.minapp.android.sdk.database.Table
import com.minapp.android.sdk.database.query.Query
import io.reactivex.Single

class QueryViewModel: ViewModel() {

    var table = ""
    var withTotalCount = false

    fun query() = Single.fromCallable {
        val query = Query().apply {
            returnTotalCount(withTotalCount)
        }
        Table(table).query(query)
    }

}