package com.minapp.android.sdk.model;

import com.google.gson.annotations.SerializedName;

public class PushConfig {

    @SerializedName("miAppId")
    public String miAppId = null;

    @SerializedName("miAppKey")
    public String miAppKey = null;

    @SerializedName("flymeAppId")
    public String flymeAppId;

    @SerializedName("flymeAppKey")
    public String flymeAppKey;

    @SerializedName("oppoAppKey")
    public String oppoAppKey;

    @SerializedName("oppoAppSecret")
    public String oppoAppSecret;

}
