package com.minapp.android.sdk.content;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.database.query.BaseQuery;
import com.minapp.android.sdk.util.PagedList;

import java.util.HashMap;

public abstract class Contents {

    /**
     * 内容列表
     * 分类 ID 和内容库 ID 至少填一个
     * @see Content#QUERY_CATEGORY_ID
     * @see Content#QUERY_CONTENT_GROUP_ID
     * @param query
     * @return
     * @throws Exception
     */
    public static PagedList<Content> contentList(@NonNull BaseQuery query) throws Exception {
        return Global.httpApi().contentList(query).execute().body().readonly();
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

    /**
     * 内容库列表
     * @param query
     * @return
     * @throws Exception
     */
    public static PagedList<ContentGroup> contentGroups(@NonNull BaseQuery query) throws Exception {
        return Global.httpApi().contentGroups(query).execute().body().readonly();
    }

    /**
     * 分类列表
     * @param query 至少包含内容库 ID {@link ContentCategory#QUERY_GROUP_ID}
     * @return
     * @throws Exception
     */
    public static PagedList<ContentCategory> contentCategories(@NonNull BaseQuery query) throws Exception {
        return Global.httpApi().contentCategories(query).execute().body().readonly();
    }

    /**
     * 分类详情（包含第一级的子分类列表{@link ContentCategory#CHILDREN}）
     * @param id
     * @return
     * @throws Exception
     */
    public static ContentCategory contentCategory(String id) throws Exception {
        return Global.httpApi().contentCategory(id).execute().body();
    }
}
