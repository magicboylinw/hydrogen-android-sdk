package com.minapp.android.sdk.database;

import android.text.TextUtils;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.database.query.Config;
import com.minapp.android.sdk.database.query.Query;
import com.minapp.android.sdk.util.PagedList;

public class Table {

    private String tableName;


    public Table(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 通过此 api 创建一条新记录
     * @return
     */
    public Record createRecord() {
        return new Record(this);
    }

    /**
     * 通过此 api 获取一条记录
     * @return
     */
    public Record fetchRecord(String recordId, Config config) throws Exception {
        return Database.fetch(this, recordId, config);
    }

    public void fetchRecordInBackground(final String recordId, final Callback callback) {
        Global.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Record record = fetchRecord(recordId, null);
                    if (callback != null) {
                        callback.onSuccess(record);
                    }
                } catch (Exception e) {
                    if (callback != null) {
                        callback.onFailure(null, e);
                    }
                }
            }
        });
    }

    /**
     * 查询
     * @param query
     * @return
     * @throws Exception
     */
    public PagedList<Record> query(Query query) throws Exception {
        return Database.query(this, query);
    }

    public BatchDeleteResp batchDelete(Query query) throws Exception {
        return Database.batchDelete(this, query);
    }

    public void queryInBackground(final Query query, final QueryCallback callback) {
        Global.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    PagedList<Record> list = Database.query(Table.this, query);
                    if (callback != null) {
                        callback.onSuccess(list);
                    }
                } catch (Exception e) {
                    if (callback != null) {
                        callback.onFailure(Table.this, e);
                    }
                }
            }
        });
    }

    String getTableName() {
        return tableName;
    }

    @Override
    public boolean equals( Object obj) {
        if (obj instanceof Table) {
            Table other = (Table) obj;
            return TextUtils.equals(this.tableName, other.tableName);
        }
        return false;
    }

}
