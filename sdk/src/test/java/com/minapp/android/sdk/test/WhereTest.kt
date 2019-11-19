package com.minapp.android.sdk.test

import android.provider.ContactsContract
import com.minapp.android.sdk.Global
import com.minapp.android.sdk.database.GeoPoint
import com.minapp.android.sdk.database.GeoPolygon
import com.minapp.android.sdk.database.query.Relation
import com.minapp.android.sdk.database.query.Where
import com.minapp.android.sdk.database.query.WhereOperator
import org.junit.Assert.*
import org.junit.Test

class WhereTest: BaseUnitTest() {

    companion object {
        private val GSON = Global.gson()
        private fun toJson(obj: Any) = GSON.toJson(obj)
    }

    @Test
    fun matchTest() {
        val lvalue = "name"
        val rvalue = "[a-z]+"
        val json = """
            {"where":{"$lvalue":{"${WhereOperator.REGEX.value}":"$rvalue"}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().matchs(lvalue, rvalue)))
    }

    @Test
    fun arrayContainsTest() {
        val lvalue = "children"
        val rvalue = listOf("Harry", "James", "Petter")
        val json = """
            {"where":{"$lvalue":{"${WhereOperator.ALL.value}":["${rvalue[0]}","${rvalue[1]}","${rvalue[2]}"]}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().arrayContains(lvalue, rvalue)))
    }

    @Test
    fun hasKeyTest() {
        val lvalue = "info"
        val rvalue = "age"
        val json = """
            {"where":{"$lvalue":{"${WhereOperator.HAS_KEY.value}":"$rvalue"}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().hasKey(lvalue, rvalue)))
    }

    @Test
    fun existTest() {
        val lvalue = "address"
        val json = """
            {"where":{"$lvalue":{"${WhereOperator.EXISTS.value}":true}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().exists(lvalue)))
    }

    @Test
    fun notExistTest() {
        val lvalue = "address"
        val json = """
            {"where":{"$lvalue":{"${WhereOperator.EXISTS.value}":false}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().notExists(lvalue)))
    }

    @Test
    fun nullTest() {
        val lvalue = "friends"
        val json = """
            {"where":{"$lvalue":{"${WhereOperator.IS_NULL.value}":true}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().isNull(lvalue)))
    }

    @Test
    fun notNullTest() {
        val lvalue = "friends"
        val json = """
            {"where":{"$lvalue":{"${WhereOperator.IS_NULL.value}":false}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().isNotNull(lvalue)))
    }

    @Test
    fun containedInTest() {
        val lvalue = "friends"
        val rvalue = listOf("Harry", "James", "Petter")
        val json = """
            {"where":{"$lvalue":{"${WhereOperator.IN.value}":["${rvalue[0]}","${rvalue[1]}","${rvalue[2]}"]}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().containedIn(lvalue, rvalue)))
    }

    @Test
    fun notContainedInTest() {
        val lvalue = "friends"
        val rvalue = listOf("Harry", "James", "Petter")
        val json = """
            {"where":{"$lvalue":{"${WhereOperator.NIN.value}":["${rvalue[0]}","${rvalue[1]}","${rvalue[2]}"]}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().notContainedIn(lvalue, rvalue)))
    }

    @Test
    fun containsTest() {
        val lvalue = "address"
        val rvalue = "street"
        val json = """
            {"where":{"$lvalue":{"${WhereOperator.CONTAINS.value}":"$rvalue"}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().contains(lvalue, rvalue)))
    }

    @Test
    fun equalToTest() {
        val lvalue = "age"
        val rvalue = 30
        val json = """
            {"where":{"$lvalue":{"${WhereOperator.EQ.value}":$rvalue}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().equalTo(lvalue, rvalue)))
    }

    @Test
    fun notEqualToTest() {
        val lvalue = "age"
        val rvalue = 30
        val json = """
            {"where":{"$lvalue":{"${WhereOperator.NE.value}":$rvalue}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().notEqualTo(lvalue, rvalue)))
    }

    @Test
    fun lessThanTest() {
        val lvalue = "age"
        val rvalue = 30
        val json = """
            {"where":{"$lvalue":{"${WhereOperator.LT.value}":$rvalue}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().lessThan(lvalue, rvalue)))
    }

    @Test
    fun lessThanOrEqualToTest() {
        val lvalue = "age"
        val rvalue = 30
        val json = """
            {"where":{"$lvalue":{"${WhereOperator.LTE.value}":$rvalue}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().lessThanOrEqualTo(lvalue, rvalue)))
    }

    @Test
    fun greaterThanTest() {
        val lvalue = "age"
        val rvalue = 30
        val json = """
            {"where":{"$lvalue":{"${WhereOperator.GT.value}":$rvalue}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().greaterThan(lvalue, rvalue)))
    }

    @Test
    fun greaterThanOrEqualToTest() {
        val lvalue = "age"
        val rvalue = 30
        val json = """
            {"where":{"$lvalue":{"${WhereOperator.GTE.value}":$rvalue}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().greaterThanOrEqualTo(lvalue, rvalue)))
    }

    @Test
    fun andTest() {
        val lvalue = "age"
        val min = 30
        val max = 60
        val json = """
            {"where":{"${Relation.AND.value}":[{"age":{"${WhereOperator.GT.value}":$min}},{"age":{"${WhereOperator.LT.value}":$max}}]}}
        """.trimIndent()
        assertEquals(json,
            toJson(Where.and(Where().greaterThan(lvalue, min), Where().lessThan(lvalue, max))))
    }

    @Test
    fun orTest() {
        val lvalue = "age"
        val min = 30
        val max = 60
        val json = """
            {"where":{"${Relation.OR.value}":[{"age":{"${WhereOperator.LT.value}":$min}},{"age":{"${WhereOperator.GT.value}":$max}}]}}
        """.trimIndent()
        assertEquals(json,
            toJson(Where.or(Where().lessThan(lvalue, min), Where().greaterThan(lvalue, max))))
    }

    @Test
    fun withInTest() {
        val lvalue = "region"
        val polygon = GeoPolygon(floatArrayOf(20.0f, 39.9f), floatArrayOf(18.1f, 22.4f))
        val json = """
            {"where":{"region":{"${WhereOperator.WITHIN.value}":{"type":"Polygon","coordinates":[[[${polygon.points[0].longitude},${polygon.points[0].latitude}],[${polygon.points[1].longitude},${polygon.points[1].latitude}]]]}}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().withIn(lvalue, polygon)))
    }

    @Test
    fun withinRegionTest() {
        val lvalue = "center"
        val center = GeoPoint(31f, 42f)
        val max = 10f
        val min = 2f
        val json = """
            {"where":{"$lvalue":{"${WhereOperator.NEASPHERE.value}":{"max_distance":$max,"min_distance":$min,"geometry":{"type":"Point","coordinates":[${center.longitude},${center.latitude}]}}}}}
        """.trimIndent()
        assertEquals(json, toJson(Where().withinRegion(lvalue, center, max, min)))
    }
}