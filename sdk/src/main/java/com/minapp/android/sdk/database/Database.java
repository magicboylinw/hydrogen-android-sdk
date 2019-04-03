package com.minapp.android.sdk.database;

import androidx.annotation.NonNull;
import com.google.gson.JsonObject;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.util.Action;
import com.minapp.android.sdk.util.PagedList;
import com.minapp.android.sdk.util.PagedListResponse;
import com.minapp.android.sdk.database.query.*;
import com.minapp.android.sdk.util.Util;

import java.util.List;

public abstract class Database {

    /**
     * 批量保存
     * @param table
     * @param records
     * @return
     * @throws Exception
     */
    static BatchResult batchSave(Table table, List<Record> records) throws Exception {
        return Global.httpApi().batchSaveRecord(table.getTableName(), records).execute().body();
    }

    /**
     * 新增 or 更新
     * @param record
     */
    static void save(Record record) throws Exception {
        if (record != null && record.getTableName() != null) {

            // 新增
            if (record.getId() == null) {
                Record response = Global.httpApi().saveRecord(record.getTableName(), record).execute().body();
                record._setJson(response._getJson());

            } else {

                // 更新
                Record clone = record._deepClone();
                JsonObject json = clone._getJson();
                for (String field : json.keySet()) {
                    String id = Util.getPointerId(json.get(field));
                    if (id != null) {
                        json.addProperty(field, id);
                    }
                }
                Record response = Global.httpApi().updateRecord(clone.getTableName(), clone.getId(), clone).execute().body();
                record._setJson(response._getJson());
            }
        }
    }

    /**
     * 删除
     * @param record
     * @throws Exception
     */
    static void delete(Record record) throws Exception {
        if (record != null && record.getId() != null) {
            Global.httpApi().deleteRecord(record.getTableName(), record.getId()).execute();
            record._setJson(null);
        }
    }

    /**
     * 获取数据
     * @return
     * @throws Exception
     */
    static @NonNull Record fetch(@NonNull Table table, String recordId, BaseQuery query) throws Exception {
        Util.assetNotNull(table);
        Record response = Global.httpApi().fetchRecord(
                table.getTableName(),
                recordId, query != null ? query : new BaseQuery()
        ).execute().body();
        response._setTable(table);
        return response;
    }


    /**
     * 查询
     * @param table
     * @param query
     * @return
     * @throws Exception
     */
    static PagedList<Record> query(final Table table, BaseQuery query) throws Exception {
        if (table != null) {
            PagedListResponse<Record> body = Global.httpApi().queryRecord(
                    table.getTableName(),
                    query != null ? query : new BaseQuery()
            ).execute().body();
            Util.each(body.getObjects(), new Action<Record>() {
                @Override
                public void on(Record record) {
                    record._setTable(table);
                }
            });
            return body.readonly();
        }
        return new PagedList<>(null);
    }

    /**
     * 批量删除
     * @param table
     * @param query
     * @return
     * @throws Exception
     */
    static BatchResult batchDelete(Table table, BaseQuery query) throws Exception {
        return Global.httpApi().batchDelete(
                table.getTableName(),
                query != null ? query : new BaseQuery()
        ).execute().body();
    }

    /**
     * 批量更新
     * @param table
     * @param query
     * @param update
     * @return
     * @throws Exception
     */
    static BatchResult batchUpdate(Table table, BaseQuery query, Record update) throws Exception {
        return Global.httpApi().batchUpdate(table.getTableName(), query, update).execute().body();
    }

}
