package com.minapp.android.sdk.typeadapter;

import android.util.Log;
import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.minapp.android.sdk.Const;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.database.Record;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RecordTypeAdapterFactory implements TypeAdapterFactory {

    private Map<Class, Info> cache = new HashMap<>();

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class clz = type.getRawType();
        Info info = cache.get(clz);
        if (info != null) {
            return new InstanceTypeAdapter(info.constructor, info.setter, info.getter);
        }

        if (Record.class.isAssignableFrom(clz)) {
            try {
                Log.d(Const.TAG, clz.toString());
                Constructor constructor = clz.getDeclaredConstructor();
                Method setter = clz.getMethod("_setJson", JsonObject.class);
                Method getter = clz.getMethod("_getJson");
                if (constructor != null && setter != null && getter != null) {
                    info =  new Info(constructor, setter, getter);
                    cache.put(clz, info);
                    return new InstanceTypeAdapter(info.constructor, info.setter, info.getter);
                }
            } catch (Exception e) {
                Log.e(Const.TAG, e.getMessage(), e);
            }
        }

        return null;
    }

    private static class InstanceTypeAdapter extends TypeAdapter {

        private Constructor constructor;
        private Method setter;
        private Method getter;

        InstanceTypeAdapter(Constructor constructor, Method setter, Method getter) {
            this.constructor = constructor;
            this.setter = setter;
            this.getter = getter;
        }

        @Override
        public void write(JsonWriter out, Object value) throws IOException {
            try {
                JsonObject json = (JsonObject) getter.invoke(value);
                Streams.write(json, out);
            } catch (Exception e) {
                Log.e(Const.TAG, e.getMessage(), e);
                throw new IOException(e);
            }
        }

        @Override
        public Object read(JsonReader in) throws IOException {
            try {
                JsonObject json = Streams.parse(in).getAsJsonObject();
                Object object = constructor.newInstance();
                setter.invoke(object, json);
                return object;
            } catch (Exception e) {
                Log.e(Const.TAG, e.getMessage(), e);
                throw new IOException(e);
            }
        }
    }

    private static class Info {
        Constructor constructor;
        Method setter;
        Method getter;

        Info(Constructor constructor, Method setter, Method getter) {
            this.constructor = constructor;
            this.setter = setter;
            this.getter = getter;
        }
    }
}
