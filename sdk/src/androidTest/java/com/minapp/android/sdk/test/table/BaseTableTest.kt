package com.minapp.android.sdk.test.table

import com.minapp.android.sdk.database.Table
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.test.BaseTest
import org.junit.Before
import org.junit.BeforeClass

open class BaseTableTest: BaseTest() {

    companion object {
        lateinit var table: Table

        @BeforeClass
        @JvmStatic
        fun prepare() {
            table = Table(TableContract.TABLE_NAME)
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

}

/**
 * 所有 table 相关的测试都依赖于这个表
 */
object TableContract {
    /**
     * 需添加一个名为 [TABLE_NAME] 的表
     */
    const val TABLE_NAME = "android_test_table"

    // 此表需包含下述字段

    const val NAME = "name"                 // string
    const val AGE = "age"                   // int
    const val LOCATION = "location"         // GeoPolygon
    const val SUPERPOWER = "super_power"    // Boolean
    const val NEIGHBORHOOD = "neighborhood" // pointer to _userprofile
    const val AVATAR = "avatar"             // file, CloudFile
    const val BIRTHDATE = "birthdate"       // date
    const val MESSAGE = "message"           // object
    const val CHILDREN = "children"         // string array
}