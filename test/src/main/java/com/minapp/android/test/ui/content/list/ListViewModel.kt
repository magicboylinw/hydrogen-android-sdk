package com.minapp.android.test.ui.content.list

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import com.minapp.android.sdk.content.Content
import com.minapp.android.sdk.content.ContentGroup
import com.minapp.android.sdk.content.Contents
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.test.Const
import com.minapp.android.test.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListViewModel: BaseViewModel() {

    val data = LivePagedListBuilder(DataSource.Factory(this), Const.DATA_SOURCE_CONFIG).build()
    val selectedItems = hashSetOf<String>()
    val query = Query()

    val contentGroup = object : MutableLiveData<List<ContentGroup>>() {
        override fun onActive() {
            ioScope.launch {
                repeat(10) {
                    try {
                        Contents.contentGroups(Query().apply {
                            put(Query.OFFSET, "0")
                            put(Query.LIMIT, Int.MAX_VALUE.toString())
                        }).objects?.also {
                            postValue(it)
                            it.getOrNull(0)?.also { query.put(Content.QUERY_CONTENT_GROUP_ID, it.id) }
                        }
                        data.value?.dataSource?.invalidate()
                        return@launch
                    } catch (e: Exception) { delay(200) }
                }
            }
        }
    }

    fun onGroupSelected(position: Int) {
        ioScope.launch {
            contentGroup.value?.getOrNull(position)?.also {
                query.put(Content.QUERY_CONTENT_GROUP_ID, it.id)
            }
            data.value?.dataSource?.invalidate()
        }
    }

}