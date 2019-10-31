package com.minapp.android.test.ui.content.list

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import com.minapp.android.sdk.content.Content
import com.minapp.android.sdk.content.ContentCategory
import com.minapp.android.sdk.content.ContentGroup
import com.minapp.android.sdk.content.Contents
import com.minapp.android.sdk.database.Record
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.test.Const
import com.minapp.android.test.ext.flat
import com.minapp.android.test.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ListViewModel: BaseViewModel() {

    val data = LivePagedListBuilder(DataSource.Factory(this), Const.DATA_SOURCE_CONFIG).build()
    val selectedItems = hashSetOf<String>()
    val query = Query()

    /**
     * 内容库列表
     */
    val groups = MutableLiveData<List<ContentGroup>>()

    /**
     * 选中的内容库 id
     */
    val groupSelected = MutableLiveData<String>()

    /**
     * 分类列表
     */
    val categories = MutableLiveData<List<ContentCategory>>()

    /**
     * 选中的分类 id
     */
    val categorySelected = MutableLiveData<String>()

    init {
        groupSelected.observeForever {
            if (it != null) {
                ioScope.launch {
                    val data = mutableListOf(CATEGORY_ALL).apply {
                        try {
                            addAll(Contents.contentCategories(Query(Query()).apply { put(ContentCategory.QUERY_GROUP_ID, it) }).objects)
                        } catch (e: Exception) {}
                    }
                    categories.postValue(data)
                    categorySelected.postValue(data.firstOrNull()?.id)
                }
            }
        }

        categorySelected.observeForever {
            inalidateData(groupSelected.value, it)
        }

        // 开始数据加载
        ioScope.launch {
            try {
                val data = Contents.contentGroups(Query()).objects
                groups.postValue(data)
                groupSelected.postValue(data?.firstOrNull()?.id)
            } catch (e: Exception) {}
        }
    }

    fun onGroupSelected(position: Int) {
        groupSelected.value = groups.value?.getOrNull(position)?.id
    }

    fun onCategorySelected(position: Int) {
        categorySelected.value = categories.value?.getOrNull(position)?.id
    }

    /**
     * 刷新列表
     */
    private fun inalidateData(groupId: String?, categoryId: String?) {
        if (groupId == null)
            query.remove(Content.QUERY_CONTENT_GROUP_ID)
        else
            query[Content.QUERY_CONTENT_GROUP_ID] = groupId

        if (categoryId == null)
            query.remove(Content.QUERY_CATEGORY_ID)
        else
            query[Content.QUERY_CATEGORY_ID] = categoryId

        data.value?.dataSource?.invalidate()
    }

    companion object {
        private val CATEGORY_ALL = ContentCategory().apply {
            name = "全部分类"
        }
    }
}