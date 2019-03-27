package com.minapp.android.sdk.database;

import androidx.annotation.Nullable;

public interface Callback {

    void onSuccess(Record record);

    void onFailure(@Nullable Record record, Exception e);
}
