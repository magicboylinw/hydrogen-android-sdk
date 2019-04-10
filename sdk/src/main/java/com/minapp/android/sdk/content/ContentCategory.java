package com.minapp.android.sdk.content;

import androidx.annotation.Nullable;
import com.minapp.android.sdk.category.BaseCategory;

import java.util.List;

public class ContentCategory extends BaseCategory {

    /**
     * 为 true 时，可通过 {@link Contents#contentCategory(String)} 获取子分类列表
     */
    public static final String HAVE_CHILDREN = "have_children";
    public static final String CHILDREN = "children";

    public static final String QUERY_GROUP_ID = "content_group_id";

    public @Nullable Boolean getHaveChildren() {
        return getBoolean(HAVE_CHILDREN);
    }

    public @Nullable List<ContentCategory> getChildren() {
        return getArray(CHILDREN, ContentCategory.class);
    }

}
