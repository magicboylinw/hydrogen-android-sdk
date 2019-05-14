package com.minapp.android.sdk.content;

import androidx.annotation.NonNull;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.database.query.Query;
import com.minapp.android.sdk.util.BaseCallback;
import com.minapp.android.sdk.util.PagedList;
import com.minapp.android.sdk.util.Util;

import java.util.concurrent.Callable;

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
    public static PagedList<Content> contents(@NonNull Query query) throws Exception {
        return Global.httpApi().contents(query).execute().body().readonly();
    }

    public static void contentsInBackground(@NonNull final Query query, @NonNull BaseCallback<PagedList<Content>> cb) {
        Util.inBackground(cb, new Callable<PagedList<Content>>() {
            @Override
            public PagedList<Content> call() throws Exception {
                return Contents.contents(query);
            }
        });
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

    public static void contentInBackground(final String id, @NonNull BaseCallback<Content> cb) {
        Util.inBackground(cb, new Callable<Content>() {
            @Override
            public Content call() throws Exception {
                return Contents.content(id);
            }
        });
    }

    /**
     * 内容库列表
     * @param query
     * @return
     * @throws Exception
     */
    public static PagedList<ContentGroup> contentGroups(@NonNull Query query) throws Exception {
        return Global.httpApi().contentGroups(query).execute().body().readonly();
    }

    public static void contentGroupsInBackground(@NonNull final Query query, @NonNull BaseCallback<PagedList<ContentGroup>> cb) {
        Util.inBackground(cb, new Callable<PagedList<ContentGroup>>() {
            @Override
            public PagedList<ContentGroup> call() throws Exception {
                return Contents.contentGroups(query);
            }
        });
    }

    /**
     * 分类列表
     * @param query 至少包含内容库 ID {@link ContentCategory#QUERY_GROUP_ID}
     * @return
     * @throws Exception
     */
    public static PagedList<ContentCategory> contentCategories(@NonNull Query query) throws Exception {
        return Global.httpApi().contentCategories(query).execute().body().readonly();
    }

    public static void contentCategoriesInBackground(@NonNull final Query query, @NonNull BaseCallback<PagedList<ContentCategory>> cb) {
        Util.inBackground(cb, new Callable<PagedList<ContentCategory>>() {
            @Override
            public PagedList<ContentCategory> call() throws Exception {
                return Contents.contentCategories(query);
            }
        });
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

    public static void contentCategoryInBackground(final String id, @NonNull BaseCallback<ContentCategory> cb) {
        Util.inBackground(cb, new Callable<ContentCategory>() {
            @Override
            public ContentCategory call() throws Exception {
                return Contents.contentCategory(id);
            }
        });
    }
}
