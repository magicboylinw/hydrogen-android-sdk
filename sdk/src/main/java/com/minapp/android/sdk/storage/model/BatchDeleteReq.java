package com.minapp.android.sdk.storage.model;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;

public class BatchDeleteReq {

    @SerializedName("id__in")
    private Collection<String> ids;

    public BatchDeleteReq(Collection<String> ids) {
        this.ids = ids;
    }

    public Collection<String> getIds() {
        return ids;
    }

    public void setIds(Collection<String> ids) {
        this.ids = ids;
    }
}
