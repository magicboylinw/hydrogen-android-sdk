package com.minapp.android.sdk.typeadapter;

import com.google.gson.*;
import com.minapp.android.sdk.database.GeoPoint;

import java.lang.reflect.Type;

/**
 * {
 *      coordinates: [10.123, 8.543],
 *      type: "Point"
 * }
 */
public class GeoPointSerializer implements JsonSerializer<GeoPoint> {
    @Override
    public JsonElement serialize(GeoPoint src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "Point");
        JsonArray coordinates = new JsonArray(2);
        coordinates.add(src.getLongitude());
        coordinates.add(src.getLatitude());
        json.add("coordinates", coordinates);
        return json;
    }
}
