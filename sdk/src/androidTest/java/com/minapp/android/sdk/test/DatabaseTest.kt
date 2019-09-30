package com.minapp.android.sdk.test

import com.minapp.android.sdk.database.Record
import com.minapp.android.sdk.database.Table
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.database.query.Where
import com.minapp.android.sdk.exception.HttpException
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class DatabaseTest: BaseTest() {

    companion object {
        private lateinit var table: Table

        @BeforeClass
        @JvmStatic
        fun prepare() {
            table = Table(Contract.TABLE_NAME)
        }
    }

    @Before
    fun cleanup() {
        val query = Query().apply {
            offset(0)
            limit(Long.MAX_VALUE)
        }
        table.batchDelete(query)
    }


    /**
     * 简单的增删改查操作
     */
    @Test
    fun simpleCurdTest() {
        val record = table.createRecord()
        record.put(Contract.NAME, "Jesse")
        record.put(Contract.AGE, 30)
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

        record.put(Contract.NAME, "Harry")
        record.save()
        assertEquals(table.fetchRecord(record.id).getString(Contract.NAME), record.getString(Contract.NAME))

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
                put(Contract.NAME, "First")
            },

            table.createRecord().apply {
                put(Contract.NAME, "Second")
            },

            table.createRecord().apply {
                put(Contract.NAME, "Third")
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

    /**
     * 复杂数据类型测试
     */
    @Test
    fun fieldTypeTest() {

    }

    @Test
    fun geoQueryTest() {

    }
}

object Contract {
    /**
     * 需添加一个名为 [TABLE_NAME] 的表
     */
    const val TABLE_NAME = "android_test_table"

    // 此表需包含下述字段

    const val NAME = "name"     // string
    const val AGE = "age"       // int
    const val LOCATION = "location"     // GeoPolygon
}