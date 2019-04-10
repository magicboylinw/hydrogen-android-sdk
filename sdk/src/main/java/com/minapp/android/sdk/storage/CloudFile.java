package com.minapp.android.sdk.storage;

import androidx.annotation.Nullable;
import com.google.gson.JsonObject;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.database.Record;


/**
 * categories : [{"id":"5a1ba7b708443e7fc5f2fb18","name":"Category"}]
 * cdn_path : 1eJCS1MFGdvaaBoV.png
 * created_at : 1511762369
 * id : 5a1ba9c1fff1d651135e5ff1
 * media_type : image
 * mime_type : image/png
 * name : box_close.png
 * path : https://cloud-minapp-287.cloud.ifanrusercontent.com/1eJCS1MFGdvaaBoV.png
 * size : 3652
 * status : success
 */
public class CloudFile extends Record {

    public static final String STATUS_SUCCESS = "success";

    public static final String CDN_PATH = "cdn_path";
    public static final String MEDIA_TYPE = "media_type";
    public static final String MIME_TYPE = "mime_type";
    public static final String NAME = "name";
    public static final String PATH = "path";
    public static final String SIZE = "size";
    public static final String STATUS = "status";
    public static final String CATEGORY = "category";

    // 用于查询
    public static final String QUERY_CATEGORY_ID = "category_id";
    public static final String QUERY_CATEGORY_NAME = "category_name";

    public boolean isUploadSuccess() {
        return STATUS_SUCCESS.equalsIgnoreCase(getStatus());
    }

    public @Nullable String getCdnPath() {
        return getString(CDN_PATH);
    }

    public CloudFile setCdnPath(String path) {
        put(CDN_PATH, path);
        return this;
    }

    public @Nullable String getMediaType() {
        return getString(MEDIA_TYPE);
    }

    public CloudFile setMediaType(String type) {
        put(MEDIA_TYPE, type);
        return this;
    }

    public @Nullable String getMimeType() {
        return getString(MIME_TYPE);
    }

    public CloudFile setMimeType(String type) {
        put(MIME_TYPE, type);
        return this;
    }

    public @Nullable String getName() {
        return getString(NAME);
    }

    public CloudFile setName(String name) {
        put(NAME, name);
        return this;
    }

    public @Nullable String getPath() {
        return getString(PATH);
    }

    public CloudFile setPath(String path) {
        put(PATH, path);
        return this;
    }

    public @Nullable Long getSize() {
        return getLong(SIZE);
    }

    public CloudFile setSize(Long size) {
        put(SIZE, size);
        return this;
    }

    public @Nullable String getStatus() {
        return getString(STATUS);
    }

    public CloudFile setStatus(String status) {
        put(STATUS, status);
        return this;
    }

    public @Nullable
    FileCategory getCategory() {
        JsonObject json = getJsonObject(CATEGORY);
        if (json != null) {
            FileCategory category = new FileCategory();
            category._setJson(json);
            return category;
        }
        return null;
    }

    public CloudFile setCategory(FileCategory category) {
        if (category == null) {
            remove(CATEGORY);
        } else {
            put(CATEGORY, Global.gson().toJsonTree(category));
        }
        return this;
    }

}
