package com.minapp.android.sdk.util;

import okhttp3.MediaType;

public abstract class MediaTypes {

    public static MediaType textPlain() {
        return MediaType.parse("text/plain");
    }

    public static MediaType applicationJson() {
        return MediaType.parse("application/json");
    }

}
