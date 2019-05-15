package com.minapp.android.sdk.database;

public class GeoPoint {

    /**
     * 经度
     */
    private float longitude;

    /**
     * 纬度
     */
    private float latitude;

    public GeoPoint() {
    }

    public GeoPoint(float longitude, float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
