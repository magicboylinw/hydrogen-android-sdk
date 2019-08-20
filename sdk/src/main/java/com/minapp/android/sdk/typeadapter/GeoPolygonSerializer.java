package com.minapp.android.sdk.typeadapter;

import com.google.gson.*;
import com.minapp.android.sdk.database.GeoPoint;
import com.minapp.android.sdk.database.GeoPolygon;

import java.lang.reflect.Type;
import java.util.List;

/**
 * {
 *     coordinates: [[[10.123, 10], [20.12453, 10], [30.564654, 20], [20.654, 30], [10.123, 10]]],
 *     type: "Polygon"
 * }
 */
public class GeoPolygonSerializer implements JsonSerializer<GeoPolygon> {
    @Override
    public JsonElement serialize(GeoPolygon src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "Polygon");

        List<GeoPoint> points = src.getPoints();
        JsonArray coordinates = new JsonArray();
        if (points != null && points.size() > 0) {
            for (GeoPoint point : points) {
                JsonArray coordinate = new JsonArray();
                coordinate.add(point.getLongitude());
                coordinate.add(point.getLatitude());
                coordinates.add(coordinate);
            }
        }

        JsonArray array = new JsonArray();
        array.add(coordinates);
        json.add("coordinates", array);
        return json;
    }
}
