package com.minapp.android.sdk.test.table

import com.minapp.android.sdk.database.GeoPoint
import com.minapp.android.sdk.database.GeoPolygon
import com.minapp.android.sdk.database.Table
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.database.query.Where
import com.minapp.android.sdk.test.BaseTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class GeoTest: BaseTableTest() {

    /**
     * 腾讯地图
     * 复杂查询测试: withinCircle, withIn
     */
    @Test
    fun queryTest() {
        val zoo = table.createRecord().apply {
            put(TableContract.NAME, "动物园")
            put(TableContract.LOCATION, GeoPoint(113.3072f, 23.1347f))
            save()
        }

        val martyrsPark = table.createRecord().apply {
            put(TableContract.NAME, "烈士陵园")
            put(TableContract.LOCATION, GeoPoint(113.2855f, 23.1264f))
            save()
        }

        val cantonTower = table.createRecord().apply {
            put(TableContract.NAME, "广州塔")
            put(TableContract.LOCATION, GeoPoint(113.3232f, 23.1065f))
            save()
        }

        val sysu = table.createRecord().apply {
            put(TableContract.NAME, "中大")
            put(TableContract.LOCATION, GeoPoint(113.2931f, 23.0921f))
            save()
        }

        // 东山口附近 2km 内包含「烈士陵园」，「动物园」
        var result = table.query(Query().apply {
            put(Where().apply {
                withinCircle(TableContract.LOCATION, GeoPoint(113.2953f, 23.1239f), 2f)
            })
        })
        assertTrue(result.objects!!.all { it in arrayOf(martyrsPark, zoo) })

        // 五羊邨附近 (2, 5) 内包含「无」
        result = table.query(Query().apply {
            put(Where().apply {
                withinRegion(TableContract.LOCATION, GeoPoint(113.3144f, 23.1199f), 5f, 2f)
            })
        })
        assertTrue(result.objects.isNullOrEmpty())

        // 晓港 - 江泰路 - 鹭江 之间包含「中大」
        result = table.query(Query().apply {
            put(Where().apply {
                // 晓港 - 江泰路 - 鹭江
                withIn(
                    TableContract.LOCATION, GeoPolygon(listOf(
                    GeoPoint(113.2817f, 23.0927f),
                    GeoPoint(113.2806f, 23.0825f),
                    GeoPoint(113.3080f, 23.0949f),
                    GeoPoint(113.2817f, 23.0927f)
                )))
            })
        })
        assertTrue(result.objects!!.all { it == sysu })
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
            put(TableContract.NAME, "T.I.T")
            put(
                TableContract.LOCATION, GeoPolygon(listOf(
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
            put(TableContract.NAME, "CBD")
            put(
                TableContract.LOCATION, GeoPolygon(listOf(
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
                include(TableContract.LOCATION, housing)
            })
        })
        assertEquals(result.objects.size, 1)
        assertEquals(result.objects.first().id, tit.id)

        result = table.query(Query().apply {
            put(Where().apply {
                include(TableContract.LOCATION, centerStreet)
            })
        })
        assertEquals(result.objects.size, 1)
        assertEquals(result.objects.first().id, cbd.id)
    }
}