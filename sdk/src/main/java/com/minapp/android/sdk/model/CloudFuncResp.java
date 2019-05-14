package com.minapp.android.sdk.model;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class CloudFuncResp {

    @SerializedName("code")
    private Integer code;

    @SerializedName("data")
    private JsonElement data;

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
