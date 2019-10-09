package com.minapp.android.sdk.test.table

import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.minapp.android.sdk.R
import com.minapp.android.sdk.auth.Auth
import com.minapp.android.sdk.storage.Storage
import com.minapp.android.sdk.test.Util
import com.minapp.android.sdk.user.User
import org.junit.Assert.*
import org.junit.Test
import java.util.*

/**
 * 复杂数据类型测试
 */
class FieldTypeTest: BaseTableTest() {

    /**
     * 文件类型
     */
    @Test
    fun fileTypeTest() {
        val avatar = Storage.uploadFile("cloud.jpg",
            ctx.resources.openRawResource(R.raw.cloud).use { it.readBytes() })
        val record = table.createRecord().put(TableContract.AVATAR, avatar).save()
        assertEquals(table.fetchRecord(record.id).getFile(TableContract.AVATAR)!!.id, avatar.id)

        val newAvatar = Storage.uploadFile("cloud.jpg",
            ctx.resources.openRawResource(R.raw.cloud).use { it.readBytes() })
        record.put(TableContract.AVATAR, newAvatar).save()
        assertEquals(table.fetchRecord(record.id).getFile(TableContract.AVATAR)!!.id, newAvatar.id)
    }

    /**
     * 日期时间类型
     */
    @Test
    fun dateTypeTest() {
        val now = Calendar.getInstance()
        val record = table.createRecord().put(TableContract.BIRTHDATE, now).save()
        assertEquals(table.fetchRecord(record.id).getCalendar(TableContract.BIRTHDATE)!!.timeInMillis, now.timeInMillis)

        val birth = Calendar.getInstance()
        birth.set(2019, 1, 13, 14, 30, 40)
        record.put(TableContract.BIRTHDATE, birth).save()
        assertEquals(table.fetchRecord(record.id).getCalendar(TableContract.BIRTHDATE)!!.timeInMillis, birth.timeInMillis)
    }

    /**
     * pointer 类型
     */
    @Test
    fun pointerTypeTest() {
        var email = Util.randomEmail()
        var pwd = Util.randomString()
        try {
            Auth.signUpByEmail(email, pwd)
        } catch (e: Exception) {}
        val neighborhood = Auth.signInByEmail(email, pwd)
        val record = table.createRecord().put(TableContract.NEIGHBORHOOD, neighborhood).save()
        assertEquals(table.fetchRecord(record.id).getObject(TableContract.NEIGHBORHOOD, User::class.java), neighborhood)

        email = Util.randomEmail()
        pwd = Util.randomString()
        try {
            Auth.signUpByEmail(email, pwd)
        } catch (e: Exception) {}
        val newNeighborhood = Auth.signInByEmail(email, pwd)
        record.put(TableContract.NEIGHBORHOOD, newNeighborhood).save()
        assertEquals(table.fetchRecord(record.id).getObject(TableContract.NEIGHBORHOOD, User::class.java), newNeighborhood)
    }

    /**
     * Boolean 类型
     */
    @Test
    fun booleanTypeTest() {
        val record = table.createRecord().put(TableContract.SUPERPOWER, true).save()
        assertEquals(table.fetchRecord(record.id).getBoolean(TableContract.SUPERPOWER), true)

        record.put(TableContract.SUPERPOWER, false).save()
        assertEquals(table.fetchRecord(record.id).getBoolean(TableContract.SUPERPOWER), false)
    }

    /**
     * object 类型
     */
    @Test
    fun objectTypeTest() {
        val json = JsonObject().apply {
            addProperty("color", "red")
        }
        val record = table.createRecord().put(TableContract.MESSAGE, json).save()
        assertEquals(table.fetchRecord(record.id).getObject(TableContract.MESSAGE, JsonObject::class.java), json)

        val newMsg = JsonObject().apply {
            addProperty("age", 20)
        }
        record.put(TableContract.MESSAGE, newMsg).save()
        assertEquals(table.fetchRecord(record.id).getObject(TableContract.MESSAGE, JsonObject::class.java), newMsg)
    }

    /**
     * Collection（在管理后台描述为 array）类型
     */
    @Test
    fun stringArrayTypeTest() {
        val list = listOf("one", "apple", "banana")
        val type = object : TypeToken<List<String>>() {}.type
        val record = table.createRecord().put(TableContract.CHILDREN, list).save()
        assertEquals(table.fetchRecord(record.id).getObject(TableContract.CHILDREN, type), list)

        val newList = listOf("android", "pie")
        record.put(TableContract.CHILDREN, newList).save()
        assertEquals(table.fetchRecord(record.id).getObject(TableContract.CHILDREN, type), newList)
    }



}