package com.minapp.android.test

import androidx.paging.Config
import androidx.paging.PagedList

object Const {
    val DATA_SOURCE_CONFIG: PagedList.Config = Config (
        pageSize = 15,
        initialLoadSizeHint = 15,
        prefetchDistance = 5,
        enablePlaceholders = false
    )
    const val BAAS_CLIENT_ID = "a4d2d62965ddb57fa4d6"   // ifanrx -> SDK 测试应用 - 知晓程序
    const val TAG = "minapp_test"
}