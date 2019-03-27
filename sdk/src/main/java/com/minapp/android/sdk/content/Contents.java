package com.minapp.android.sdk.content;

import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.database.query.Query;
import com.minapp.android.sdk.util.PagedList;

import java.util.HashMap;

public abstract class Contents {

    /**
     * 内容列表
     * 分类 ID 和内容库 ID 至少填一个
     * @param categoryId 分类 ID
     * @param groupId    内容库 ID
     * @param query
     * @return
     * @throws Exception
     */
    public static PagedList<Content> contentList(String categoryId, String groupId, Query query) throws Exception {
        return Global.httpApi().contentList(
                categoryId,
                groupId,
                query != null ? query._toQueryMap() : new HashMap<String, String>()
        ).execute().body().readonly();
    }

    /**
     * 内容明细
     * @param id
     * @return
     * @throws Exception
     */
    public static Content content(String id) throws Exception {
        return Global.httpApi().content(id).execute().body();
    }
}
