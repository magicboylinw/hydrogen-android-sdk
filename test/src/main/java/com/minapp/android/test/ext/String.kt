package com.minapp.android.test.ext

import androidx.lifecycle.MutableLiveData
import com.minapp.android.sdk.content.ContentCategory

fun String.trimToNull(): String? {
    val str = trim()
    return if (str.isNotEmpty()) str else null
}

private fun flatInto(level: Int, node: ContentCategory, list: MutableList<ContentCategory>) {
    node.name = "${Array(level) { "-" }.joinToString(separator = "")} ${node.name}"
    list.add(node)
    node.children?.forEach { flatInto(level + 1, it, list) }
}

/**
 * 递归、深度优先地遍历，最后 flat 为 list
 */
fun List<ContentCategory>.flat(): List<ContentCategory> {
    val list = mutableListOf<ContentCategory>()
    forEach { flatInto(0, it, list) }
    return list
}