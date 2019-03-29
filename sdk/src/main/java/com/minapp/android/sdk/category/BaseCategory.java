package com.minapp.android.sdk.category;

import androidx.annotation.Nullable;
import com.minapp.android.sdk.database.Record;

public class BaseCategory extends Record {

    public static final String NAME = "name";

    public @Nullable String getName() {
        return getString(BaseCategory.NAME);
    }

    public void setName(String name) {
        put(BaseCategory.NAME, name);
    }
}
