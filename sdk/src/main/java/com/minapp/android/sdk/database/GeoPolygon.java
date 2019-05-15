package com.minapp.android.sdk.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeoPolygon {

    private List<GeoPoint> points = new ArrayList<>();

    public List<GeoPoint> getPoints() {
        return points;
    }

    public void setPoints(List<GeoPoint> points) {
        this.points = points;
    }

    public GeoPolygon(List<GeoPoint> list) {
        if (list != null && list.size() > 0) {
            points.addAll(list);
        }
    }

    public GeoPolygon(GeoPoint... array) {
        if (array != null && array.length > 0) {
            points.addAll(Arrays.asList(array));
        }
    }

    public GeoPolygon(float[]... arrays) {
        if (arrays != null && arrays.length > 0) {
            for (float[] item : arrays) {
                if (item != null && item.length >= 2) {
                    points.add(new GeoPoint(item[0], item[1]));
                }
            }
        }
    }
}
