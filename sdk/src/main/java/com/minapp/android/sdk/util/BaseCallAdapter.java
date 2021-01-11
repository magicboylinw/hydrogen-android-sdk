package com.minapp.android.sdk.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;

public abstract class BaseCallAdapter<R, T> implements CallAdapter<R, T> {

    private final Type responseType;

    /**
     *
     * @param returnType first param at retrofit2.CallAdapter.Factory#get(java.lang.reflect.Type,
     *                   java.lang.annotation.Annotation[], retrofit2.Retrofit)
     */
    public BaseCallAdapter(Type returnType) {
        responseType = returnType instanceof ParameterizedType ?
                Util.getParameterUpperBound(0, (ParameterizedType) returnType) : null;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

}
