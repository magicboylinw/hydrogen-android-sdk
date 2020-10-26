package com.minapp.android.sdk.ws;

import com.google.gson.JsonObject;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.database.Record;
import com.minapp.android.sdk.database.Table;
import com.minapp.android.sdk.util.Util;

import java.util.Map;

public class SchemeEvent {

    public static final String KEY_SCHEME_NAME = "schema_name";

    public static final String KEY_SCHEME_ID = "schema_id";

    public static final String KEY_ID = "id";

    public static final String KEY_AFTER = "after";

    public static final String KEY_BEFORE = "before";

    public static final String KEY_EVENT = "event";

    public static final String EVENT_ON_CREATE = "on_create";

    public static final String EVENT_ON_UPDATE = "on_update";

    public static final String EVENT_ON_DELETE = "on_delete";

    public final String schemaName;

    public final Integer schemaId;

    public final String id;

    /**
     * @see #EVENT_ON_CREATE
     * @see #EVENT_ON_UPDATE
     * @see #EVENT_ON_DELETE
     */
    public final String event;

    public final Record after;

    public final Record before;

    public SchemeEvent(Map argumentKw) {
        if (argumentKw != null) {
            schemaName = Util.parseString(argumentKw.get(KEY_SCHEME_NAME));
            schemaId = Util.parseInteger(argumentKw.get(KEY_SCHEME_ID));
            id = Util.parseString(argumentKw.get(KEY_ID));
            after = valueOf(argumentKw.get(KEY_AFTER));
            before = valueOf(argumentKw.get(KEY_BEFORE));
            event = Util.parseString(argumentKw.get(KEY_EVENT));
        } else {
            schemaName = null;
            schemaId = null;
            id = null;
            after = null;
            before = null;
            event = null;
        }
    }

    private Record valueOf(Object obj) {
        if (obj instanceof Map) {
            return new Record(new Table(schemaName),
                    Global.gson().fromJson(Global.gson().toJsonTree(obj), JsonObject.class));
        }
        return null;
    }
}
