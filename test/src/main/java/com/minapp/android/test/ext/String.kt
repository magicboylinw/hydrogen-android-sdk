package com.minapp.android.test.ext

fun String.trimToNull(): String? {
    val str = trim()
    return if (str.isNotEmpty()) str else null
}