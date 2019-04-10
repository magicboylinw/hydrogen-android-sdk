package com.minapp.android.sdk.database;

import android.text.TextUtils;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.database.query.Query;
import com.minapp.android.sdk.util.PagedList;

import java.util.List;

public class Table {

    private String tableName;


    public Table(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
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


    /******************************** simple curd **************************************/

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
    public Record fetchRecord(String recordId, Query query) throws Exception {
        return Database.fetch(this, recordId, query);
    }

    /**
     * 获取一条记录
     * @see #fetchRecord(String, Query)
     * @param recordId
     * @param callback
     */
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

    /**
     * 查询
     * @see #query(Query)
     * @param query
     * @param callback
     */
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

    /**
     * 根据 id 构造一条记录，但不去抓取这条记录的内容，所以这条记录只有 table 和 id 信息
     * @param id
     * @return
     */
    public Record fetchWithoutData(String id) {
        Record record = createRecord();
        record.put(Record.ID, id);
        return record;
    }


    /******************************** batch operation **************************************/

    /**
     * 批量删除，通过 where 查询条件
     * @param query
     * @return
     * @throws Exception
     */
    public BatchResult batchDelete(Query query) throws Exception {
        return Database.batchDelete(this, query);
    }

    /**
     * 批量保存
     * @param records
     * @return
     * @throws Exception
     */
    public BatchResult batchSave(List<Record> records) throws Exception {
        return Database.batchSave(this, records);
    }

    /**
     * 批量更新
     * @param query
     * @param update
     * @return
     * @throws Exception
     */
    private BatchResult batchUpdate(Query query, Record update) throws Exception {
        return Database.batchUpdate(this, query, update);
    }

}
