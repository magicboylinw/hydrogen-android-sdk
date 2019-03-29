package com.minapp.android.sdk.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import com.google.gson.*;
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


    /*************************** inner method ***********************************/


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
        return getStringArray(WRITE_PERM);
    }

    public @Nullable List<String> getReadPerm() {
        return getStringArray(READ_PERM);
    }

    public @Nullable String getTableName() {
        return table != null ? table.getTableName() : null;
    }


    /*************************** json class ***********************************/


    public @Nullable JsonObject getJsonObject(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return json.getAsJsonObject(key);
        } catch (Exception e) {
            return null;
        }
    }


    /*************************** string ***********************************/

    public Record put(@NonNull String key, String value) {
        Util.assetNotNull(key);
        json.addProperty(key, value);
        return this;
    }

    public @Nullable String getString(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return json.get(key).getAsString();
        } catch (Exception e) {
            return null;
        }
    }


    /*************************** number ***********************************/



    public Record put(@NonNull String key, Number value) {
        Util.assetNotNull(key);
        json.addProperty(key, value);
        return this;
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


    /*************************** boolean ***********************************/

    public Record put(@NonNull String key, boolean value) {
        Util.assetNotNull(key);
        json.addProperty(key, value);
        return this;
    }

    public @Nullable Boolean getBoolean(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return json.get(key).getAsBoolean();
        } catch (Exception e) {
            return null;
        }
    }


    /*************************** file ***********************************/

    public Record put(@NonNull String key, UploadedFile value) {
        Util.assetNotNull(key);
        json.add(key, Global.gson().toJsonTree(value));
        return this;
    }

    public @Nullable UploadedFile getFile(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return Global.gson().fromJson(json.getAsJsonObject(key), UploadedFile.class);
        } catch (Exception e) {
            return null;
        }
    }


    /*************************** object ***********************************/

    /**
     * 这里通过 {@link Gson} 序列化 object <br />
     * 所以 object 要不用 gson 注解标注 property，要不在 proguard 里排除 object 的混淆
     * @param key
     * @param obj
     * @return
     */
    public Record put(@NonNull String key, Object obj) {
        Util.assetNotNull(key);
        json.add(key, Global.gson().toJsonTree(obj));
        return this;
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


    /*************************** date（日期时间，ISO8601 格式的日期字符串，例如："2018-09-01T18:31:02.631000+08:00" ***********************************/

    public Record put(@NonNull String key, Calendar calendar) {
        Util.assetNotNull(key);
        json.add(key, Global.gson().toJsonTree(calendar));
        return this;
    }

    public @Nullable Calendar getCalendar(@NonNull String key) {
        Util.assetNotNull(key);
        try {
            return Global.gson().fromJson(json.get(key), Calendar.class);
        } catch (Exception e) {
            Log.e(Const.TAG, e.getMessage(), e);
            return null;
        }
    }


    /*************************** array ***********************************/


    private  <T> Record putArray(@NonNull String key, List<T> list) {
        Util.assetNotNull(key);
        JsonArray value = null;
        if (list != null) {
            value = new JsonArray(list.size());
            for (T item : list) {
                value.add(Global.gson().toJsonTree(item));
            }
        }
        json.add(key, value);
        return this;
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


    public Record putStringArray(@NonNull String key, List<String> list) {
        return putArray(key, list);
    }

    public Record putNumberArray(@NonNull String key, List<Number> list) {
        return putArray(key, list);
    }

    public Record putBooleanArray(@NonNull String key, List<Boolean> list) {
        return putArray(key, list);
    }

    public Record putFileArray(@NonNull String key, List<UploadedFile> list) {
        return putArray(key, list);
    }

    public Record putCalendarArray(@NonNull String key, List<Calendar> list) {
        return putArray(key, list);
    }

    public Record putObjectArray(@NonNull String key, List<Object> list) {
        return putArray(key, list);
    }

    public @Nullable List<String> getStringArray(@NonNull String key) {
        return getArray(key, new Function<JsonElement, String>() {
            @Override
            public String on(JsonElement elem) {
                return elem.getAsString();
            }
        });
    }

    public @Nullable List<Integer> getIntArray(@NonNull String key) {
        return getArray(key, new Function<JsonElement, Integer>() {
            @Override
            public Integer on(JsonElement elem) {
                return elem.getAsNumber().intValue();
            }
        });
    }

    public @Nullable List<Long> getLongArray(@NonNull String key) {
        return getArray(key, new Function<JsonElement, Long>() {
            @Override
            public Long on(JsonElement elem) {
                return elem.getAsNumber().longValue();
            }
        });
    }

    public @Nullable List<Float> getFloatArray(@NonNull String key) {
        return getArray(key, new Function<JsonElement, Float>() {
            @Override
            public Float on(JsonElement elem) {
                return elem.getAsNumber().floatValue();
            }
        });
    }

    public @Nullable List<Double> getDoubleArray(@NonNull String key) {
        return getArray(key, new Function<JsonElement, Double>() {
            @Override
            public Double on(JsonElement elem) {
                return elem.getAsNumber().doubleValue();
            }
        });
    }

    public @Nullable List<Boolean> getBooleanArray(@NonNull String key) {
        return getArray(key, new Function<JsonElement, Boolean>() {
            @Override
            public Boolean on(JsonElement elem) {
                return elem.getAsBoolean();
            }
        });
    }

    public @Nullable List<UploadedFile> getFileArray(@NonNull String key) {
        return getArray(key, new Function<JsonElement, UploadedFile>() {
            @Override
            public UploadedFile on(JsonElement elem) {
                return Global.gson().fromJson(elem, UploadedFile.class);
            }
        });
    }

    public @Nullable List<Calendar> getCalendarArray(@NonNull String key) {
        return getArray(key, new Function<JsonElement, Calendar>() {
            @Override
            public Calendar on(JsonElement elem) {
                return Global.gson().fromJson(elem, Calendar.class);
            }
        });
    }

    public @Nullable <T> List<T> getObjectArray(@NonNull String key, final Class<T> clz) {
        return getArray(key, new Function<JsonElement, T>() {
            @Override
            public T on(JsonElement elem) {
                return Global.gson().fromJson(elem, clz);
            }
        });
    }
}

