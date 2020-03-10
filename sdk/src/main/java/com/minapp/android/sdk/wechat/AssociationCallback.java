package com.minapp.android.sdk.wechat;

import androidx.annotation.Nullable;

public interface AssociationCallback {

    void onSuccess();

    void onFailure(@Nullable Exception ex);
}
