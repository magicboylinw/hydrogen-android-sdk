package com.minapp.android.sdk.storage;

import androidx.annotation.Nullable;
import com.minapp.android.sdk.category.BaseCategory;
import com.minapp.android.sdk.database.Record;

public class FileCategory extends BaseCategory {

    public static final String FILES = "files";

    public @Nullable Long getFiles() {
        return getLong(FILES);
    }

    public void setFiles(Long files) {
        put(FILES, files);
    }

}
