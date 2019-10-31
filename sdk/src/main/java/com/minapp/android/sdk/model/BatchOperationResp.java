package com.minapp.android.sdk.model;

import com.google.gson.annotations.SerializedName;

public class BatchOperationResp {

    public static final String OPERATION_UPDATE = "update";
    public static final String OPERATION_DELETE = "delete";

    /**
     * id : 1
     * schema_id : 1
     * schema_name : test
     * operation : delete
     * status : success
     * created_at : 1571047763
     * updated_at : 1571047763
     * deleted_count : 1
     * matched_count : 1
     * modified_count : 1
     */

    @SerializedName("id")
    private int id;                 // id
    @SerializedName("schema_id")
    private int schemaId;           // 数据表 id
    @SerializedName("schema_name")
    private String schemaName;      // 数据表名称
    @SerializedName("operation")
    private String operation;       // 批量操作：`update`（更新）/ `delete`（删除）
    @SerializedName("status")
    private String status;          // 批量操作状态：`pending`（等待执行）/ `success`（已完成）
    @SerializedName("created_at")
    private int createdAt;          // 创建时间
    @SerializedName("updated_at")
    private int updatedAt;          // 更新时间

    // 批量删除时返回
    @SerializedName("deleted_count")
    private int deletedCount;       // 删除记录行数量（operation=delete 时返回）

    // 批量更新时返回
    @SerializedName("matched_count")
    private int matchedCount;       // 符合更新查询条件数量（operation=update 时返回）
    @SerializedName("modified_count")
    private int modifiedCount;      // 已更新记录行数量（operation=update 时返回）

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(int schemaId) {
        this.schemaId = schemaId;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public int getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(int updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getDeletedCount() {
        return deletedCount;
    }

    public void setDeletedCount(int deletedCount) {
        this.deletedCount = deletedCount;
    }

    public int getMatchedCount() {
        return matchedCount;
    }

    public void setMatchedCount(int matchedCount) {
        this.matchedCount = matchedCount;
    }

    public int getModifiedCount() {
        return modifiedCount;
    }

    public void setModifiedCount(int modifiedCount) {
        this.modifiedCount = modifiedCount;
    }
}
