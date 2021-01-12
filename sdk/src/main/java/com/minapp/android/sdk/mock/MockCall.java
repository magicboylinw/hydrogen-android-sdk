package com.minapp.android.sdk.mock;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 实现此接口后，标注在 retrofit method 上，实现 mock
 * @param <T>
 * @see Mockable
 */
public interface MockCall<T> {

    /**
     *
     * @param call
     * @return {@link Response#body()} 直接返回模型即可
     */
    Response<T> execute(Call<T> call);

}
