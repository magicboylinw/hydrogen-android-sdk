package com.minapp.android.sdk.test

import com.minapp.android.sdk.content.Content
import com.minapp.android.sdk.content.ContentCategory
import com.minapp.android.sdk.content.ContentGroup
import com.minapp.android.sdk.content.Contents
import com.minapp.android.sdk.database.query.Query
import org.junit.Assert.*
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

/**
 * sdk 内容库 api 只有查询功能，所以先保证内容库中包含下述内容，然后测试查询
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ContentTest: BaseTest() {

    companion object {

        // 指定的内容库名称
        private const val GROUP_NAME = "android_test_group"

        // 内容库里包含的分类名称
        private const val CATEGORY_NAME = "favorite_article"

        // 分类里包含一个此标题的内容
        private const val CONTENT_TITLE = "title"

        private lateinit var group: ContentGroup
        private lateinit var category: ContentCategory
    }

    /**
     * 测试内容库查询接口
     */
    @Test
    fun GroupTest() {
        group = Contents.contentGroups(Query()).objects.find { it.name == GROUP_NAME }!!
    }

    /**
     * 测试分类相关接口
     */
    @Test
    fun categoryTest() {
        val query = Query().apply {
            put(ContentCategory.QUERY_GROUP_ID, group.id)
        }
        category = Contents.contentCategories(query).objects.find { it.name == CATEGORY_NAME }!!
        assertEquals(Contents.contentCategory(category.id).name, category.name)
    }

    /**
     * 测试内容相关接口
     */
    @Test
    fun contentTest() {
        val query = Query().apply {
            put(Content.QUERY_CATEGORY_ID, category.id)
        }
        val content = Contents.contents(query).objects.find { it.title == CONTENT_TITLE }!!

        val target = Contents.content(content.id)
        assertEquals(target.id, content.id)
    }

}