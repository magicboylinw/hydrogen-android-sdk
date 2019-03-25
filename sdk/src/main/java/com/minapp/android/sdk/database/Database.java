package com.minapp.android.sdk.database;

import androidx.annotation.NonNull;
import com.google.gson.JsonObject;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.util.PagedListResponse;
import com.minapp.android.sdk.database.query.*;
import com.minapp.android.sdk.util.Util;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public abstract class Database {

    /**
     * 新增 or 更新
     * @param record
     */
    static void save(RecordObject record) throws Exception {
        if (record != null) {

            // 新增
            if (record.id() == null) {
                Response<JsonObject> response = Global.httpApi().saveRecord(record.tableName(), record.toJsonObject()).execute();
                record.updateByServer(response.body());

            } else {

                // 更新
                Response<JsonObject> response = Global.httpApi().updateRecord(
                        record.tableName(), record.id(), record.toJsonObject()).execute();
                record.updateByServer(response.body());
            }
        }
    }

    /**
     * 删除
     * @param record
     * @throws Exception
     */
    static void delete(RecordObject record) throws Exception {
        if (record != null && record.id() != null) {
            Global.httpApi().deleteRecord(record.tableName(), record.id()).execute();
            record.updateByServer(null);
        }
    }

    /**
     * 获取数据
     * @return
     * @throws Exception
     */
    static @NonNull RecordObject fetch(@NonNull TableObject table, String recordId) throws Exception {
        Util.assetNotNull(table);
        Response<JsonObject> response = Global.httpApi().fetchRecord(table.getTableName(), recordId).execute();
        RecordObject record = table.createRecord();
        record.updateByServer(response.body());
        return record;
    }


    /**
     * 查询
     * @param table
     * @param query
     * @return
     * @throws Exception
     */
    static Result query(TableObject table, Query query) throws Exception {
        Result result = new Result();
        if (table != null) {
            String where = query != null ? query.getWhereJson() : null;
            String orderBy = query != null ? query.getOrderBy() : null;
            Long limit = query != null ? query.getLimit() : null;
            Long offset = query != null ? query.getOffset() : null;
            PagedListResponse<JsonObject> body = Global.httpApi().queryRecord(table.getTableName(), where, orderBy, limit, offset).execute().body();

            PagedListResponse.Meta meta = body.getMeta();
            if (meta != null) {
                result.setNext(meta.getNext());
                result.setPrevious(meta.getPrevious());
                result.setLimit(meta.getLimit());
                result.setOffset(meta.getOffset());
                result.setTotalCount(meta.getTotalCount());
            }

            List<JsonObject> objects = body.getObjects();
            if (objects != null) {
                List<RecordObject> records = new ArrayList<>(objects.size());
                result.setRecords(records);

                for (JsonObject object : objects) {
                    RecordObject record = new RecordObject(table);
                    record.updateByServer(object);
                    records.add(record);
                }
            } else {
                result.setRecords(new ArrayList<RecordObject>(0));
            }

        }
        return result;
    }

    /**
     * 批量删除
     * @param table
     * @param query
     * @return
     * @throws Exception
     */
    static BatchDeleteResp batchDelete(TableObject table, Query query) throws Exception {
        String where = query != null ? query.getWhereJson() : null;
        Long limit = query != null ? query.getLimit() : null;
        Long offset = query != null ? query.getOffset() : null;
        return Global.httpApi().batchDelete(table.getTableName(), where, offset, limit).execute().body();
    }

}
