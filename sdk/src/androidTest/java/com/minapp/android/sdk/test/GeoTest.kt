package com.minapp.android.sdk.test

import android.util.Log
import com.minapp.android.sdk.database.GeoPoint
import com.minapp.android.sdk.database.GeoPolygon
import com.minapp.android.sdk.database.Table
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.database.query.Where
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class GeoTest: BaseTest() {

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
        table.batchDelete(Query.all())
    }

    /**
     * 腾讯地图
     * 复杂查询测试
     */
    @Test
    fun queryTest() {
        table.createRecord().apply {
            put(Contract.NAME, "动物园")
            put(Contract.LOCATION, GeoPoint(113.3072f, 23.1347f))
            save()
        }

        table.createRecord().apply {
            put(Contract.NAME, "烈士陵园")
            put(Contract.LOCATION, GeoPoint(113.2855f, 23.1264f))
            save()
        }

        table.createRecord().apply {
            put(Contract.NAME, "广州塔")
            put(Contract.LOCATION, GeoPoint(113.3232f, 23.1065f))
            save()
        }

        table.createRecord().apply {
            put(Contract.NAME, "中大")
            put(Contract.LOCATION, GeoPoint(113.2931f, 23.0921f))
            save()
        }

        var result = table.query(Query().apply {
            put(Where().apply {
                withinCircle(Contract.LOCATION, GeoPoint(113.2953f, 23.1239f), 2f)
            })
        })
        result.objects?.map { it.getString(Contract.NAME) }?.joinToString(separator = ",")?.also {
            /*Log.d(Const.TAG, "东山口附近 2km 内包含：{$it}")*/
        }


        result = table.query(Query().apply {
            put(Where().apply {
                withinRegion(Contract.LOCATION, GeoPoint(113.3144f, 23.1199f), 5f, 2f)
            })
        })
        result.objects?.map { it.getString(Contract.NAME) }?.joinToString(separator = ",")?.also {
            /*Log.d(Const.TAG, "五羊邨附近 (2, 5) 内包含：{$it}")*/
        }

        result = table.query(Query().apply {
            put(Where().apply {
                // 晓港 - 江泰路 - 鹭江
                withIn(Contract.LOCATION, GeoPolygon(listOf(
                    GeoPoint(113.2817f, 23.0927f),
                    GeoPoint(113.2806f, 23.0825f),
                    GeoPoint(113.3080f, 23.0949f),
                    GeoPoint(113.2817f, 23.0927f)
                )))
            })
        })
        result.objects?.map { it.getString(Contract.NAME) }?.joinToString(separator = ",")?.also {
            /*Log.d(Const.TAG, "晓港 - 江泰路 - 鹭江 之间包含：{$it}")*/
        }
    }

    /**
     * 复杂查询测试: include
     * 腾讯地图
     */
    @Test
    fun includeTest() {
        table.batchDelete(Query())

        // 广州塔 - 客村 - 赤岗
        val tit = table.createRecord().apply {
            put(Contract.NAME, "T.I.T")
            put(Contract.LOCATION, GeoPolygon(listOf(
                GeoPoint(113.3236f, 23.1063f),
                GeoPoint(113.3201f, 23.0964f),
                GeoPoint(113.3347f, 23.0966f),
                GeoPoint(113.3236f, 23.1063f)
            ))
            )
            save()
        }

        // 黄埔大道 - 五羊邨 - 海心沙 - 猎德
        val cbd = table.createRecord().apply {
            put(Contract.NAME, "CBD")
            put(Contract.LOCATION, GeoPolygon(listOf(
                GeoPoint(113.3244f, 23.1273f),
                GeoPoint(113.3143f, 23.1201f),
                GeoPoint(113.3242f, 23.1113f),
                GeoPoint(113.3326f, 23.1188f),
                GeoPoint(113.3244f, 23.1273f)
            ))
            )
            save()
        }

        // 丽园雅庭
        val housing = GeoPoint(113.3261f, 23.0977f)

        // 花城大道
        val centerStreet = GeoPoint(113.3247f, 23.1193f)

        var result = table.query(Query().apply {
            put(Where().apply {
                include(Contract.LOCATION, housing)
            })
        })
        assertEquals(result.objects.size, 1)
        assertEquals(result.objects.first().id, tit.id)

        result = table.query(Query().apply {
            put(Where().apply {
                include(Contract.LOCATION, centerStreet)
            })
        })
        assertEquals(result.objects.size, 1)
        assertEquals(result.objects.first().id, cbd.id)
    }
}