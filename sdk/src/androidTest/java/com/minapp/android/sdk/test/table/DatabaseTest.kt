package com.minapp.android.sdk.test.table

import com.minapp.android.sdk.database.Record
import com.minapp.android.sdk.database.Table
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.database.query.Where
import com.minapp.android.sdk.exception.HttpException
import com.minapp.android.sdk.test.BaseTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class DatabaseTest: BaseTableTest() {

    /**
     * 简单的增删改查操作
     */
    @Test
    fun simpleCurdTest() {
        val record = table.createRecord()
        record.put(TableContract.NAME, "Jesse")
        record.put(TableContract.AGE, 30)
        record.save()
        assertNotNull(record.id)

        val query = Query().apply {
            put(Where().apply {
                equalTo(Record.ID, record.id)
            })
        }
        table.query(query).objects.also {
            assert(it.size == 1)
            assert(it[0].id == record.id)
        }

        record.put(TableContract.NAME, "Harry")
        record.save()
        assertEquals(
            table.fetchRecord(record.id).getString(
                TableContract.NAME
            ), record.getString(TableContract.NAME))

        val id = record.id!!
        record.delete()
        try {
            table.fetchRecord(id)
        } catch (e: HttpException) {
            if (e.code == 404)
                return
        }
        throw IllegalStateException()
    }

    /**
     * 测试批量操作
     */
    @Test
    fun batchTest() {
        val records = listOf(
            table.createRecord().apply {
                put(TableContract.NAME, "First")
            },

            table.createRecord().apply {
                put(TableContract.NAME, "Second")
            },

            table.createRecord().apply {
                put(TableContract.NAME, "Third")
            }
        )
        val result = table.batchSave(records)
        assertEquals(result.succeed, 3)
        assertEquals(result.totalCount, 3)
        result.operationResult.all { !it.success.id.isNullOrEmpty() }
        records.all { !it.id.isNullOrEmpty() }

        val query = Query().apply {
            put(Where().containedIn(Record.ID, records.map { it.id }))
        }
        table.batchDelete(query)
        assertEquals(table.count(Query.all()), 0)
    }
}

