package com.minapp.android.example;

import android.util.Log;
import com.minapp.android.sdk.BaaS;
import com.minapp.android.sdk.database.*;
import com.minapp.android.sdk.database.query.Query;
import com.minapp.android.sdk.database.query.Where;
import com.minapp.android.sdk.model.CloudFuncResp;
import com.minapp.android.sdk.model.StatusResp;
import com.minapp.android.sdk.util.BaseCallback;
import com.minapp.android.sdk.util.PagedList;

import java.util.regex.Pattern;

public class Example {

    private static final String TAG = "Example";

    public static void main(String[] args) {
        Table table = new Table("my_horses");
        Query query = new Query();

        table.countInBackground(query, new BaseCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                Log.d(TAG, integer.toString());
                // success
            }

            @Override
            public void onFailure(Throwable e) {
                // fail
            }
        });

    }
}
