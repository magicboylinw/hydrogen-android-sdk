package com.minapp.android.sdk.typeadapter;

import com.google.gson.*;
import com.minapp.android.sdk.database.GeoPoint;

import java.lang.reflect.Type;

public class GeoPointDeserializer implements JsonDeserializer<GeoPoint> {
    @Override
    public GeoPoint deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonArray coordinates = ((JsonObject) json).getAsJsonArray("coordinates");
            return new GeoPoint(coordinates.get(0).getAsFloat(), coordinates.get(1).getAsFloat());
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }
}
