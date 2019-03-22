package com.minapp.android.example.database

import com.minapp.android.sdk.database.RecordObject

class Horse (
    private val record: RecordObject
){

    val id: String?
    get() = record.id()

    var name: String?
    get() = record.getString("horse_name")
    set(value) { record.put("horse_name", value) }

    var age: Int?
    get() = record.getInt("horse_age")
    set(value) { record.put("horse_age", age) }

    var checked: Boolean = false

}