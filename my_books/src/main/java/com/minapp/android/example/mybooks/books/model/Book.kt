package com.minapp.android.example.mybooks.books.model

import com.minapp.android.sdk.database.Record

class Book: Record {

    constructor(record: Record) : super(record._getTable(), record._getJson())
    constructor() : super()


    var name: String?
    get() = getString(NAME)
    set(value) {
        put(NAME, value)
    }

    companion object {
        private const val NAME = "book_name"
        val EMPTY_PLACEHOLDER = Book()
    }
}