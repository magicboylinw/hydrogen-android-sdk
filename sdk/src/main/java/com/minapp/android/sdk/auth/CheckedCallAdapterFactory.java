package com.minapp.android.sdk.auth;

import androidx.annotation.Nullable;

import com.minapp.android.sdk.exception.EmptyResponseException;
import com.minapp.android.sdk.exception.HttpException;
import com.minapp.android.sdk.exception.SessionMissingException;
import com.minapp.android.sdk.util.BaseCallAdapter;

import okhttp3.Request;
import retrofit2.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;


public class CheckedCallAdapterFactory extends CallAdapter.Factory {

    @Nullable
    @Override
    public CallAdapter<?, ?> get(final Type returnType, Annotation[] annotations, final Retrofit retrofit) {
        return new CheckedCallAdapter(returnType, retrofit);
    }

    private static class CheckedCallAdapter extends BaseCallAdapter {

        private final Retrofit retrofit;

        public CheckedCallAdapter(Type returnType, Retrofit retrofit) {
            super(returnType);
            this.retrofit = retrofit;
        }

        @Override
        public Object adapt(Call call) {
            return new CheckedCall(call, retrofit.callbackExecutor(),
                    responseType() != null && responseType() != Void.class);
        }
    }

    private static class CheckedCall implements Call {

        private Call realCall;
        private boolean hasBody;
        private Executor callbackExecutor;

        public CheckedCall(Call realCall, Executor callbackExecutor, boolean hasBody) {
            this.realCall = realCall;
            this.hasBody = hasBody;
            this.callbackExecutor = callbackExecutor;
        }

        @Override
        public Response execute() throws IOException {
            return postProcess(realCall, realCall.execute());
        }

        @Override
        public void enqueue(final Callback callback) {
            realCall.enqueue(new Callback() {
                @Override
                public void onResponse(final Call call, Response response) {
                    try {
                        final Response resp = postProcess(call, response);
                        callbackExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                callback.onResponse(call, resp);
                            }
                        });
                    } catch (final Exception e) {
                        callbackExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailure(call, e);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(final Call call, final Throwable t) {
                    callbackExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(call, t);
                        }
                    });
                }
            });
        }

        /**
         * 处理 401 和其他异常的 status code，检查 body
         * @param call
         * @param response
         * @return
         * @throws IOException
         * @throws HttpException
         * @throws EmptyResponseException
         */
        private Response postProcess(Call call, Response response) throws IOException {
            if (!response.isSuccessful()) {
                if (response.code() == 401) {
                    throw new SessionMissingException();
                } else {
                    throw HttpException.valueOf(response);
                }
            }
            if (hasBody && response.body() == null) {
                throw new EmptyResponseException();
            }
            return response;
        }

        @Override
        public boolean isExecuted() {
            return realCall.isExecuted();
        }

        @Override
        public void cancel() {
            realCall.cancel();
        }

        @Override
        public boolean isCanceled() {
            return realCall.isCanceled();
        }

        @Override
        public Call clone() {
            return realCall.clone();
        }

        @Override
        public Request request() {
            return realCall.request();
        }
    }
}
