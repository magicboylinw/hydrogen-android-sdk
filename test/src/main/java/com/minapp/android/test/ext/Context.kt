package com.minapp.android.test.ext

import android.content.Context

fun Context.dp2px(dp: Float) =
    resources.displayMetrics.density.times(dp)


fun Context.dp2pxInt(dp: Float) =
    resources.displayMetrics.density.times(dp).toInt()
