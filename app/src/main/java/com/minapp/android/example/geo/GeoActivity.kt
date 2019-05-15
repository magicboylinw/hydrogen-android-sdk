package com.minapp.android.example.geo

import android.os.Bundle
import android.util.Log
import com.minapp.android.example.Const
import com.minapp.android.example.R
import com.minapp.android.example.base.BaseActivity
import com.minapp.android.sdk.Global
import com.minapp.android.sdk.database.GeoPoint
import com.minapp.android.sdk.database.GeoPolygon
import com.minapp.android.sdk.database.Table
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.database.query.Where
import kotlinx.android.synthetic.main.activity_geo.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GeoActivity : BaseActivity() {

    private val table = Table("geo_test")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geo)
        testBtn.setOnClickListener {
            GlobalScope.launch {
                try {
                    complexQueryTest()
                } catch (e: Exception) {}
            }
        }
    }

    private fun curdTest() {
        val one = table.createRecord()
        one.put(LOCATION_POINT, GeoPoint(113.324850f, 23.099826f))

        val points = listOf(
            GeoPoint(113.324850f, 23.099826f),
            GeoPoint(113.297023f, 23.0818636f),
            GeoPoint(113.345003f, 23.081626f),
            GeoPoint(113.324850f, 23.099826f)
        )
        one.put(LOCATION_POLYGON, GeoPolygon(points))
        one.save()
        Log.d(Const.TAG, "save success, id: ${one.id}")

        val fetched = table.fetchRecord(one.id)
        Log.d(Const.TAG, "fetched, geo point\n${Global.gsonPrint().toJson(fetched.getGeoPoint(LOCATION_POINT))}")
        Log.d(Const.TAG, "fetched, geo polygon\n${Global.gsonPrint().toJson(fetched.getGeoPolygon(LOCATION_POLYGON))}")
    }

    /**
     * 腾讯地图
     * 复杂查询测试
     */
    private fun complexQueryTest() {
        table.batchDelete(Query())

        table.createRecord().apply {
            put(LOCATION_NAME, "动物园")
            put(LOCATION_POINT, GeoPoint(113.3072f, 23.1347f))
            save()
        }

        table.createRecord().apply {
            put(LOCATION_NAME, "烈士陵园")
            put(LOCATION_POINT, GeoPoint(113.2855f, 23.1264f))
            save()
        }

        table.createRecord().apply {
            put(LOCATION_NAME, "广州塔")
            put(LOCATION_POINT, GeoPoint(113.3232f, 23.1065f))
            save()
        }

        table.createRecord().apply {
            put(LOCATION_NAME, "中大")
            put(LOCATION_POINT, GeoPoint(113.2931f, 23.0921f))
            save()
        }

        var result = table.query(Query().apply {
            put(Where().apply {
                withinCircle(LOCATION_POINT, GeoPoint(113.2953f, 23.1239f), 2f)
            })
        })
        result.objects?.map { it.getString(LOCATION_NAME) }?.joinToString(separator = ",")?.also {
            Log.d(Const.TAG, "东山口附近 2km 内包含：{$it}")
        }


        result = table.query(Query().apply {
            put(Where().apply {
                withinRegion(LOCATION_POINT, GeoPoint(113.3144f, 23.1199f), 5f, 2f)
            })
        })
        result.objects?.map { it.getString(LOCATION_NAME) }?.joinToString(separator = ",")?.also {
            Log.d(Const.TAG, "五羊邨附近 (2, 5) 内包含：{$it}")
        }

        result = table.query(Query().apply {
            put(Where().apply {
                // 晓港 - 江泰路 - 鹭江
                withIn(LOCATION_POINT, GeoPolygon(listOf(
                    GeoPoint(113.2817f, 23.0927f),
                    GeoPoint(113.2806f, 23.0825f),
                    GeoPoint(113.3080f, 23.0949f),
                    GeoPoint(113.2817f, 23.0927f)
                )))
            })
        })
        result.objects?.map { it.getString(LOCATION_NAME) }?.joinToString(separator = ",")?.also {
            Log.d(Const.TAG, "晓港 - 江泰路 - 鹭江 之间包含：{$it}")
        }
    }

    /**
     * 复杂查询测试: include
     * 腾讯地图
     */
    private fun includeTest() {
        table.batchDelete(Query())

        // 广州塔 - 客村 - 赤岗
        val tit = table.createRecord().apply {
            put(LOCATION_NAME, "T.I.T")
            put(LOCATION_POLYGON, GeoPolygon(listOf(
                GeoPoint(113.3236f, 23.1063f),
                GeoPoint(113.3201f, 23.0964f),
                GeoPoint(113.3347f, 23.0966f),
                GeoPoint(113.3236f, 23.1063f)
            )))
            save()
        }

        // 黄埔大道 - 五羊邨 - 海心沙 - 猎德
        val cbd = table.createRecord().apply {
            put(LOCATION_NAME, "CBD")
            put(LOCATION_POLYGON, GeoPolygon(listOf(
                GeoPoint(113.3244f, 23.1273f),
                GeoPoint(113.3143f, 23.1201f),
                GeoPoint(113.3242f, 23.1113f),
                GeoPoint(113.3326f, 23.1188f),
                GeoPoint(113.3244f, 23.1273f)
            )))
            save()
        }

        // 丽园雅庭
        val housing = GeoPoint(113.3261f, 23.0977f)

        // 花城大道
        val centerStreet = GeoPoint(113.3247f, 23.1193f)

        var result = table.query(Query().apply {
            put(Where().apply {
                include(LOCATION_POLYGON, housing)
            })
        })
        result.objects?.map { it.getString(LOCATION_NAME) }?.joinToString(separator = ",")?.also {
            Log.d(Const.TAG, "丽园雅庭在 {$it}")
        }

        result = table.query(Query().apply {
            put(Where().apply {
                include(LOCATION_POLYGON, centerStreet)
            })
        })
        result.objects?.map { it.getString(LOCATION_NAME) }?.joinToString(separator = ",")?.also {
            Log.d(Const.TAG, "花城大道在 {$it}")
        }
    }


    companion object {
        private const val LOCATION_POINT = "location_point"
        private const val LOCATION_POLYGON = "location_polygon"
        private const val LOCATION_NAME = "location_name"
    }
}
