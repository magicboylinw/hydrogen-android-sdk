package com.minapp.android.sdk.typeadapter;

import com.google.gson.*;
import com.minapp.android.sdk.database.GeoPoint;
import com.minapp.android.sdk.database.GeoPolygon;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GeoPolygenDeserializer implements JsonDeserializer<GeoPolygon> {
    @Override
    public GeoPolygon deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonArray coordinates = ((JsonObject) json).getAsJsonArray("coordinates").get(0).getAsJsonArray();
            List<GeoPoint> points = new ArrayList<>(coordinates.size());

            for (int i = 0; i < coordinates.size(); i++) {
                try {
                    JsonArray coordinate = coordinates.get(i).getAsJsonArray();
                    GeoPoint point = new GeoPoint(coordinate.get(0).getAsFloat(), coordinate.get(1).getAsFloat());
                    points.add(point);
                } catch (Exception ignored) {}
            }
            return new GeoPolygon(points);
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }
}
