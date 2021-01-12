package com.minapp.android.sdk.mock;

import com.google.common.collect.Collections2;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.auth.CheckedCallAdapterFactory;
import com.minapp.android.sdk.util.BaseCallAdapter;
import com.minapp.android.sdk.util.HandlerExecutor;
import com.minapp.android.sdk.util.Util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * SDK 内部的 mock 机制，mock Retrofit，mock http
 * 把它添加到 {@link CheckedCallAdapterFactory} 前面
 *
 * 使用 {@link Mockable} 注解在 retrofit method 上标注需要 mock 的请求
 * 并实现 {@link MockCall}
 *
 */
public class HttpMockCallAdapterFactory extends CallAdapter.Factory {

    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        MockCall mock = null;
        try {
            for (Annotation annotation : annotations) {
                if (annotation instanceof Mockable) {
                    Class clz = ((Mockable) annotation).value();
                    if (MockCall.class.isAssignableFrom(clz)) {
                        mock = (MockCall) clz.newInstance();
                    }
                }
            }
        } catch (Throwable ignored) {}

        if (mock != null) {
            return new MockCallAdapter(mock, returnType, retrofit);
        }

        return null;
    }


    private class MockCallAdapter extends BaseCallAdapter {

        private final MockCall mock;
        private final Executor callbackExecutor;

        public MockCallAdapter(@Nonnull MockCall mock, Type returnType, Retrofit retrofit) {
            super(returnType);
            this.mock = mock;
            if (retrofit.callbackExecutor() == null)
                callbackExecutor = Global.getMainExecutor();
            else
                callbackExecutor = retrofit.callbackExecutor();
        }

        @Override
        public Object adapt(Call call) {
            return new RealMockCall(call, mock, callbackExecutor);
        }
    }


    private class RealMockCall implements Call {

        private final Call originCall;
        private final MockCall mock;
        private final Executor callbackExecutor;
        private final AtomicBoolean executed = new AtomicBoolean(false);
        private final AtomicBoolean canceled = new AtomicBoolean(false);

        public RealMockCall(@Nonnull Call originCall, @Nonnull MockCall mock,
                            @Nonnull Executor callbackExecutor) {
            this.originCall = originCall;
            this.mock = mock;
            this.callbackExecutor = callbackExecutor;
        }

        @Override
        public Response execute() throws IOException {
            if (canceled.get())
                throw new IOException("canceled");

            try {
                executed.set(true);
                return mock.execute(originCall);
            } catch (Throwable tr) {
                throw new IOException(tr);
            }
        }

        @Override
        public void enqueue(Callback callback) {
            Global.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response response = execute();
                        callbackExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                callback.onResponse(originCall, response);
                            }
                        });
                    } catch (IOException e) {
                        callbackExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailure(originCall, e);
                            }
                        });
                    }
                }
            });
        }

        @Override
        public boolean isExecuted() {
            return executed.get();
        }

        @Override
        public void cancel() {
            canceled.compareAndSet(false, true);
        }

        @Override
        public boolean isCanceled() {
            return canceled.get();
        }

        @Override
        public Call clone() {
            return originCall.clone();
        }

        @Override
        public Request request() {
            return originCall.request();
        }
    }

}
