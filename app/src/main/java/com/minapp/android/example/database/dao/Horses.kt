package com.minapp.android.example.database.dao

import com.minapp.android.sdk.database.Record
import com.minapp.android.sdk.util.PagedList

class Horses (list: PagedList<Record>) {

    var next: String? = null
    var previous: String? = null
    var totalCount: Long = 0
    var limit: Long = 0
    var offset: Long = 0
    var records: List<Horse>


    init {
        next = list.next
        previous = list.previous
        totalCount = list.totalCount
        limit = list.limit
        offset = list.offset
        records = list.objects?.map { Horse(it) } ?: arrayListOf()
    }

}