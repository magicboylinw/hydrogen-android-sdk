package com.minapp.android.example.database.dao

import com.minapp.android.sdk.database.Record
import com.minapp.android.sdk.database.Table
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.util.PagedList

open class Horse {

    constructor(id: String) {
        record = TABLE.fetchRecord(id, null)
    }

    constructor(record: Record) {
        this.record = record
    }

    constructor()


    private var record = TABLE.createRecord()

    var name: String?
    get() = record.getString(NAME)
    set(value) { record.put(NAME, value) }

    var age: Int?
    get() = record.getInt(AGE)
    set(value) { record.put(AGE, value) }

    val id: String?
    get() = record.getId()

    var checked: Boolean = false

    fun save() {
        record.save()
    }

    fun delete() {
        record.delete()
    }



    companion object {
        const val NAME = "horse_name"
        const val AGE = "horse_age"
        val TABLE = Table("my_horses")

        fun query(condition: Horse, query: Query): PagedList<Horse> {
            query.apply {
                if (condition.age != null) {
                    eq(AGE, condition.age)
                }
                if (condition.name != null) {
                    eq(NAME, condition.name)
                }
            }
            return TABLE.query(query).transform { Horse(it) }
        }

        fun batchDelete(ids: List<String>) {
            TABLE.batchDelete(Query().inString(Record.ID, ids))
        }
    }
}