package com.minapp.android.sdk.database;

import androidx.annotation.Nullable;
import android.text.TextUtils;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.minapp.android.sdk.Global;

import java.util.List;

class RecordMeta {

    @SerializedName(RecordObject.ID)
    private String id;

    @SerializedName(RecordObject.CREATED_AT)
    private Long createdAt;

    @SerializedName(RecordObject.CREATED_BY)
    private Long createdBy;

    @SerializedName(RecordObject.UPDATED_AT)
    private Long updatedAt;

    @SerializedName(RecordObject.WRITE_PERM)
    private List<String> writePerm;

    @SerializedName(RecordObject.READ_PERM)
    private List<String> readPerm;


    static @Nullable RecordMeta fromJson(JsonElement json) {
        RecordMeta obj = null;
        if (json != null) {
            TypeAdapter<RecordMeta> adapter = Global.gson().getAdapter(RecordMeta.class);
            if (adapter != null) {
                obj = adapter.fromJsonTree(json);
            }
        }
        return obj;
    }

    boolean check() {
        return !TextUtils.isEmpty(id);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getWritePerm() {
        return writePerm;
    }

    public void setWritePerm(List<String> writePerm) {
        this.writePerm = writePerm;
    }

    public List<String> getReadPerm() {
        return readPerm;
    }

    public void setReadPerm(List<String> readPerm) {
        this.readPerm = readPerm;
    }
}
