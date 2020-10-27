package com.minapp.android.sdk.ws;

import androidx.annotation.NonNull;

import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.database.query.Query;

import io.crossbar.autobahn.wamp.types.SubscribeOptions;

public class WampSubscribeOptions extends SubscribeOptions {

    public WampSubscribeOptions(@NonNull Query query) {
        put("where", query);
    }

}
