package com.minapp.android.sdk.test

import android.content.ContentResolver
import android.net.Uri
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.exception.HttpException
import com.minapp.android.sdk.storage.CloudFile
import com.minapp.android.sdk.storage.FileCategory
import com.minapp.android.sdk.storage.Storage
import org.junit.Assert.*
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

/**
 * 文件模块测试
 * requirement:
 * 1）需要有个名为 [CATEGORY_NAME] 的文件目录
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class StorageTest: BaseTest() {

    companion object {
        private const val CATEGORY_NAME = "android_test_category"
        private const val FILE_NAME = "cloud.jpg"

        private val FILE_DATA: ByteArray
        get() {
            val uri = Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(ctx.packageName)
                .path(R.raw.cloud.toString())
                .build()
            return ctx.contentResolver.openInputStream(uri).let { it.readBytes() }
        }

        private lateinit var category: FileCategory
        private lateinit var cloudFile: CloudFile
    }

    /**
     * 测试目录列表
     */
    @Test
    fun o1CategoryListTest() {
        category = Storage.categories(Query()).objects.find { it.name == CATEGORY_NAME }!!
    }

    /**
     * 根据 id 获取目录
     */
    @Test
    fun o2CategorybyIdTest() {
        assertEquals(Storage.category(category.id).id, category.id)
    }

    /**
     * 上传文件
     */
    @Test
    fun o3UploadFileTest() {
        cloudFile = Storage.uploadFile(FILE_NAME, category.id, FILE_DATA)!!
    }

    /**
     * 文件列表
     */
    @Test
    fun o4FileListTest() {
        val query = Query().apply {
            put(CloudFile.QUERY_CATEGORY_ID, category.id)
        }
        assertNotNull(Storage.files(query).objects.find { it.name == FILE_NAME })
    }

    /**
     * 根据 id 获取文件
     */
    @Test
    fun o5FileByIdTest() {
        assertEquals(Storage.file(cloudFile.id).name, cloudFile.name)
    }

    /**
     * 删除文件
     */
    @Test
    fun o6DeleteFileTest() {
        Storage.deleteFiles(listOf(cloudFile.id))

        // 删除后，应该返回 404 才正常
        try {
            Storage.file(cloudFile.id)
        } catch (e: HttpException) {
            if (e.code == 404)
                return
        }
        throw IllegalStateException()
    }
}
