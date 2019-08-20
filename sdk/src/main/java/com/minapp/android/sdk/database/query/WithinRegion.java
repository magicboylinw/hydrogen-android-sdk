package com.minapp.android.sdk.database.query;

import com.minapp.android.sdk.database.GeoPoint;

public class WithinRegion {

    private GeoPoint center;
    private float maxDistance;
    private float minDistance;

    public WithinRegion(GeoPoint center, float maxDistance, float minDistance) {
        this.center = center;
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
    }

    public GeoPoint getCenter() {
        return center;
    }

    public void setCenter(GeoPoint center) {
        this.center = center;
    }

    public float getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(float maxDistance) {
        this.maxDistance = maxDistance;
    }

    public float getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(float minDistance) {
        this.minDistance = minDistance;
    }
}
