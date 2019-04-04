package com.minapp.android.example

import androidx.paging.Config
import com.minapp.android.sdk.Const
import okhttp3.MediaType

object Const {
    const val TAG = "minapp-sdk-example"
    const val CATEGORY_APP = "minapp.example"

    val DATA_SOURCE_CONFIG = Config(
        pageSize = 15,
        initialLoadSizeHint = 15,
        prefetchDistance = 5,
        enablePlaceholders = false
    )
}