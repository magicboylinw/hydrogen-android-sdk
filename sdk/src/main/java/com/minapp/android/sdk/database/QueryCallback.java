package com.minapp.android.sdk.database;

import com.minapp.android.sdk.util.PagedList;

public interface QueryCallback {

    void onSuccess(PagedList<Record> list);

    void onFailure(Table table, Exception e);
}
