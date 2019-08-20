package com.minapp.android.sdk.util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Retrofit2CallbackAdapter<T> implements Callback<T> {

    private BaseCallback<T> cb;

    public Retrofit2CallbackAdapter(BaseCallback<T> cb) {
        this.cb = cb;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (cb != null) {
            cb.onSuccess(response.body());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (cb != null) {
            cb.onFailure(t);
        }
    }
}
