package com.minapp.android.sdk.typeadapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.minapp.android.sdk.database.query.WithinRegion;

import java.lang.reflect.Type;

public class WithinRegionSerializer implements JsonSerializer<WithinRegion> {
    @Override
    public JsonElement serialize(WithinRegion src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();

        json.addProperty("max_distance", src.getMaxDistance());
        json.addProperty("min_distance", src.getMinDistance());
        json.add("geometry", context.serialize(src.getCenter()));

        return json;
    }
}
