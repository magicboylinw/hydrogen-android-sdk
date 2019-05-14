package com.minapp.android.sdk.model;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class CloudFuncReq {

    /**
     * 要运行的云函数名
     */
    @SerializedName("function_name")
    private String funcName;

    /**
     * 可以是任何合法格式
     */
    @SerializedName("data")
    private JsonElement data;

    /**
     * 云函数的运行是否同步, true 表示同步，false 表示异步
     */
    @SerializedName("sync")
    private Boolean sync;

    public CloudFuncReq() {}

    public CloudFuncReq(String funcName, JsonElement data, Boolean sync) {
        this.funcName = funcName;
        this.data = data;
        this.sync = sync;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public Boolean getSync() {
        return sync;
    }

    public void setSync(Boolean sync) {
        this.sync = sync;
    }
}
