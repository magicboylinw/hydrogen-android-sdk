package com.minapp.android.sdk.database.model;

import com.google.gson.annotations.SerializedName;

public class BatchDeleteResp {

    /**
     * succeed : 8
     * total_count : 10
     * offset : 0
     * limit : 10
     * next : null
     */

    @SerializedName("succeed")
    private Integer succeed;        // 成功删除记录数
    @SerializedName("total_count")
    private Integer totalCount;     // where 匹配的记录数，包括无权限操作记录
    @SerializedName("offset")
    private Integer offset;         // 与传入参数 offset 一致
    @SerializedName("limit")
    private Integer limit;          // 与传入参数 limit 一致
    @SerializedName("next")
    private String next;            // 下一次的更新链接，若待更新记录数超过上限，可通过该链接继续更新

    public Integer getSucceed() {
        return succeed;
    }

    public void setSucceed(Integer succeed) {
        this.succeed = succeed;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
