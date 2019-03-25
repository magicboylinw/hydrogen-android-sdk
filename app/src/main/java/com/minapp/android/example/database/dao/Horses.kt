package com.minapp.android.example.database.dao

import com.minapp.android.sdk.database.query.Result

class Horses (result: Result) {

    var next: String? = null
    var previous: String? = null
    var totalCount: Long = 0
    var limit: Long = 0
    var offset: Long = 0
    var records: List<Horse>


    init {
        next = result.next
        previous = result.previous
        totalCount = result.totalCount
        limit = result.limit
        offset = result.offset
        records = result.records?.map { Horse(it) } ?: arrayListOf()
    }

}