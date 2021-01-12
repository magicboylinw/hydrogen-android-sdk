package com.minapp.android.sdk.model;

import com.google.gson.annotations.SerializedName;

public class PushMetaData {

    @SerializedName("installationId")
    public String installationId = null;

    @SerializedName("regId")
    public String regId = null;

    @SerializedName("vendor")
    public String vendor = null;

}
