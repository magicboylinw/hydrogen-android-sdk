package com.minapp.android.sdk.model;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

public class ServerDateResp {

    @SerializedName("time")
    private Calendar time;

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }
}
