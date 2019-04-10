package com.minapp.android.sdk.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import com.minapp.android.sdk.Const;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.storage.UploadedFile;
import com.minapp.android.sdk.util.Function;
import com.minapp.android.sdk.util.Util;

import java.lang.reflect.Type;
import java.util.*;

public class Record {

    public static final String ID = "id";
    public static final String CREATED_AT = "created_at";
    public static final String CREATED_BY = "created_by";
    public static final String UPDATED_AT = "updated_at";
    public static final String WRITE_PERM = "write_perm";
    public static final String READ_PERM = "read_perm";
    public static final String TABLE = "_table";

    public static final String SPECIAL_UNSET = "$unset";

    private @Nullable Table table;
    private @NonNull JsonObject json;

    public Record(Table table) {
        this(table, null);
    }

    public Record() {
        this(null, null);
    }

    public Record(Table table, JsonObject json) {
        this.table = table;
        this.json = json;
        if (this.json == null) {
            this.json = new JsonObject();
        }
    }

    @Override
    public String toString() {
        return new StringBuilder("tableName : ").append(getTableName())
                .append("\n").append(Global.gson().toJson(json))
                .toString();
    }


    /*************************** CURD ***********************************/


    public Record save() throws Exception {
        Database.save(this);
        return this;
    }

    public void saveInBackground() {
        saveInBackground(null);
    }

    public void saveInBackground(final Callback callback) {
        Global.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    save();
                    if (callback != null) {
                        callback.onSuccess(Record.this);
                    }
                } catch (Exception e) {
                    if (callback != null) {
                        callback.onFailure(Record.this, e);
                    }
                }
            }
        });
    }

    public void delete() throws Exception {
        Database.delete(this);
    }

    public void deleteInBackground() {
        deleteInBackground(null);
    }

    public void deleteInBackground(final Callback callback) {
        Global.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    delete();
                    if (callback != null) {
                        callback.onSuccess(Record.this);
                    }
                } catch (Exception e) {
                    if (callback != null) {
                        callback.onFailure(Record.this, e);
                    }
                }
            }
        });
    }


    /*************************** private method ***********************************/


    public JsonObject _getJson() {
        return json;
    }


    public void _setJson(JsonObject json) {
        if (json == null) {
            json = new JsonObject();
        }
        this.json = json;
    }

    public Table _getTable() {
        return table;
    }

    public void _setTable(Table table) {
        this.table = table;
    }


    public Record _deepClone() {
        Record clone = new Record();
        clone._setTable(table);
        clone._setJson(json.deepCopy());
        return clone;
    }

    private @Nullable <T> List<T> getArray(@NonNull String key, Function<JsonElement, T> transform) {
        Util.assetNotNull(key);
        try {
            JsonArray array = json.get(key).getAsJsonArray();
            List<T> list = new ArrayList<>(array.size());
            for (JsonElement elem : array) {
                list.add(transform.on(elem));
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }


    /*************************** misc ***********************************/


    /**
     * 删除字段
     * @param fields
     * @return
     */
    public Record unset(Collection<String> fields) {
        if (fields == null || fields.isEmpty()) {
            json.remove(SPECIAL_UNSET);
        } else {
            JsonObject map = new JsonObject();
            for (String field: fields) {
                map.addProperty(field, "");
            }
            json.add(SPECIAL_UNSET, map);
        }
        return this;
    }

    public Record remove(@NonNull String key) {
        json.remove(key);
        return this;
    }


    /*************************** meta info ***********************************/



    public @Nullable String getId() {
        return getString(ID);
    }

    public @Nullable Long getCreatedBy() {
        return getLong(CREATED_BY);
    }

    public @Nullable Long getCreatedAt() {
        return getLong(CREATED_AT);
    }

    public @Nullable Long getUpdatedAt() {
        return getLong(UPDATED_AT);
    }

    public @Nullable List<String> getWritePerm() {
        return getArray(WRITE_PERM, String.class);
    }

    public @Nullable List<String> getReadPerm() {
        return getArray(READ_PERM, String.class);
    }

    public @Nullable String getTableName() {
        return table != null ? table.getTableName() : null;
    }




    /*************************** public setter ***********************************/


    /**
     * put json field
     * @param key
     * @param value 可以是：
     *              1，基本类型及其包装类，{@link String}
     *              2，{@link JsonObject}, {@link JsonArray}, {@link JsonNull} 等 Gson 类型
     *              3，集合
     *              4，时间日期用 {@link Calendar}
     *              5，自定义类型，注意不要混淆 properties，或者加上 {@link SerializedName}
     *              6，{@link UploadedFile}
     *              7，如果列类型是 pointer，还可以是 {@link Record} 及其子类
     * @return
     */
    public Record put(@NonNull String key, @Nullable Object value) {
        Util.assetNotNull(key);
        json.add(key, Global.gson().toJsonTree(value));
        return this;
    }



    /*************************** public getter ***********************************/


    /**
     * 直接作为 {@link JsonObject} 返回
     * @param key
     * @return
     */
    public @Nullable JsonObject getJsonObject(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return json.getAsJsonObject(key);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 1，对应 date 列类型（日期时间，ISO8601 格式的日期字符串，例如："2018-09-01T18:31:02.631000+08:00"）
     * 2，或者它本身就是 {@link Calendar}
     * @param key
     * @return
     */
    public @Nullable Calendar getCalendar(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return Global.gson().fromJson(json.get(key), Calendar.class);
        } catch (Exception e) {
            Log.e(Const.TAG, e.getMessage(), e);
            return null;
        }
    }


    /**
     * 对应 file 列类型
     * @param key
     * @return
     */
    public @Nullable UploadedFile getFile(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return Global.gson().fromJson(json.getAsJsonObject(key), UploadedFile.class);
        } catch (Exception e) {
            return null;
        }
    }


    public @Nullable <T> List<T> getArray(@NonNull String key, final Class<T> clz) {
        return getArray(key, new Function<JsonElement, T>() {
            @Override
            public T on(JsonElement elem) {
                return Global.gson().fromJson(elem, clz);
            }
        });
    }

    public <T> T getObject(@NonNull String key, Class<T> clz) {
        Util.assetNotNull(key);
        try {
            return Global.gson().fromJson(json.get(key), clz);
        } catch (Exception e) {
            return null;
        }
    }

    public <T> T getObject(@NonNull String key, Type type) {
        Util.assetNotNull(key);
        try {
            return Global.gson().fromJson(json.get(key), type);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public @Nullable Boolean getBoolean(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return json.get(key).getAsBoolean();
        } catch (Exception e) {
            return null;
        }
    }

    public @Nullable Number getNumber(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return json.get(key).getAsNumber();
        } catch (Exception e) {
            return null;
        }
    }

    public @Nullable Integer getInt(@NonNull String key) {
        try {
            return getNumber(key).intValue();
        } catch (Exception e) {
            return null;
        }
    }

    public @Nullable Long getLong(@NonNull String key) {
        try {
            return getNumber(key).longValue();
        } catch (Exception e) {
            return null;
        }
    }

    public @Nullable Float getFloat(@NonNull String key) {
        try {
            return getNumber(key).floatValue();
        } catch (Exception e) {
            return null;
        }
    }

    public @Nullable Double getDouble(@NonNull String key) {
        try {
            return getNumber(key).doubleValue();
        } catch (Exception e) {
            return null;
        }
    }

    public @Nullable String getString(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return json.get(key).getAsString();
        } catch (Exception e) {
            return null;
        }
    }

}

