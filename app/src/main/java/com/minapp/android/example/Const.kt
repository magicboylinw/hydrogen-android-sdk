package com.minapp.android.example

import androidx.paging.Config
import com.minapp.android.sdk.Const
import okhttp3.MediaType

object Const {
    const val clientId = "20c4d2be25668e132360"
    const val TAG = "minapp-sdk-example"

    val DATA_SOURCE_CONFIG = Config(
        pageSize = 15,
        initialLoadSizeHint = 15,
        prefetchDistance = 5,
        enablePlaceholders = false
    )
}