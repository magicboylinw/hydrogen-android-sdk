package com.minapp.android.sdk.typeadapter;

import com.google.gson.*;
import com.minapp.android.sdk.database.GeoPoint;
import com.minapp.android.sdk.database.query.WithinCircle;

import java.lang.reflect.Type;

public class WithinCircleSerializer implements JsonSerializer<WithinCircle> {
    @Override
    public JsonElement serialize(WithinCircle src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.addProperty("radius", src.getRadius());

        JsonArray coordinates = new JsonArray(2);
        json.add("coordinates", coordinates);
        GeoPoint center = src.getCenter();
        if (center != null) {
            coordinates.add(center.getLongitude());
            coordinates.add(center.getLatitude());
        }

        return json;
    }
}
