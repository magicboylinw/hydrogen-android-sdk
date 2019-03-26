package com.minapp.android.example.database.dao

import android.util.Log
import com.minapp.android.example.Const
import com.minapp.android.sdk.database.RecordObject
import com.minapp.android.sdk.database.TableObject
import com.minapp.android.sdk.database.query.Query

open class Horse {

    constructor(id: String) {
        record = TABLE.fetchRecord(id)
    }

    constructor(record: RecordObject) {
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
    get() = record.id()

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
        val TABLE = TableObject("my_horses")

        fun query(condition: Horse, query: Query): Horses {
            query.apply {
                if (condition.age != null) {
                    eq(AGE, condition.age)
                }
                if (condition.name != null) {
                    eq(NAME, condition.name)
                }
            }
            return Horses(
                TABLE.query(
                    query
                )
            )
        }

        fun batchDelete(ids: List<String>) {
            TABLE.batchDelete(Query().inString(RecordObject.ID, ids))
        }
    }
}