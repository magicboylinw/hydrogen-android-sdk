package com.minapp.android.sdk.content;

import androidx.annotation.Nullable;
import com.minapp.android.sdk.database.Record;

public class Content extends Record {

    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String COVER = "cover";
    public static final String GROUP_ID = "group_id";
    public static final String CONTENT = "content";
    public static final String VISIT_COUNT = "visit_count"; // 阅读数，开启阅读数统计功能就会返回此字段
    public static final String CATEGORIES = "categories";

    public static final String QUERY_CATEGORY_ID = "category_id";               // 分类 ID
    public static final String QUERY_CONTENT_GROUP_ID = "content_group_id";     // 内容库 ID

    public @Nullable String getTitle() {
        return getString(TITLE);
    }

    public Content setTitle(String title) {
        put(TITLE, title);
        return this;
    }

    public @Nullable String getDescription() {
        return getString(DESCRIPTION);
    }

    public Content setDescription(String description) {
        put(DESCRIPTION, description);
        return this;
    }

    public @Nullable String getCover() {
        return getString(COVER);
    }

    public Content setCover(String cover) {
        put(COVER, cover);
        return this;
    }

    public @Nullable Long getGroupId() {
        return getLong(GROUP_ID);
    }

    public Content setGroupId(Long groupId) {
        put(GROUP_ID, groupId);
        return this;
    }

    public @Nullable String getContent() {
        return getString(CONTENT);
    }

    public Content setContent(String content) {
        put(CONTENT, content);
        return this;
    }

    public @Nullable Long getVisitCount() {
        return getLong(VISIT_COUNT);
    }

    public Content setVisitCount(Long count) {
        put(VISIT_COUNT, count);
        return this;
    }

}
