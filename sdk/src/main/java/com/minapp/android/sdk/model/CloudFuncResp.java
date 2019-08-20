package com.minapp.android.sdk.model;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class CloudFuncResp {

    /**
     * code 为 0 时表示成功执行云函数，否则为执行云函数失败
     */
    @SerializedName("code")
    private Integer code;

    /**
     * 云函数的执行结果
     */
    @SerializedName("data")
    private JsonElement data;

    /**
     * 返回的错误信息，成功则返回空对象
     */
    @SerializedName("error")
    private JsonElement error;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public JsonElement getError() {
        return error;
    }

    public void setError(JsonElement error) {
        this.error = error;
    }
}
