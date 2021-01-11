package com.minapp.android.sdk.mock;

import retrofit2.Call;
import retrofit2.Response;

public interface MockCall<T> {

    /**
     *
     * @param call
     * @return {@link Response#body()} 直接返回模型即可
     */
    Response<T> execute(Call<T> call);

}
