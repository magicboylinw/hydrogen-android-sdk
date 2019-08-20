package com.minapp.android.sdk.auth;

import androidx.annotation.Nullable;
import com.google.gson.JsonSyntaxException;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.exception.EmptyResponseException;
import com.minapp.android.sdk.exception.HttpException;
import com.minapp.android.sdk.exception.SessionMissingException;
import com.minapp.android.sdk.model.ErrorResp;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;


public class CheckedCallAdapterFactory extends CallAdapter.Factory {

    @Nullable
    @Override
    public CallAdapter<?, ?> get(final Type returnType, Annotation[] annotations, final Retrofit retrofit) {

        // retrofit method 的返回类型（外部类型，不是 body 的类型）
        final Class returnClz = getRawType(returnType);

        // http response body 的类型
        final Type bodyType = returnType instanceof ParameterizedType ? getParameterUpperBound(0, (ParameterizedType) returnType) : null;
        final Class bodyClz = bodyType != null ? getRawType(bodyType) : null;

        if ((returnClz == Call.class || returnClz == CheckedCall.class) && bodyType != null) {
            return new CallAdapter<Object, Object>() {
                @Override
                public Type responseType() {
                    return bodyType;
                }

                @Override
                public Object adapt(Call<Object> call) {
                    return new CheckedCallImpl(call, retrofit.callbackExecutor(), returnClz == CheckedCall.class, bodyClz != null && bodyClz != Void.class);
                }
            };
        }
        return null;
    }

    private static class CheckedCallImpl implements CheckedCall {

        private Call realCall;
        private boolean checked;
        private boolean hasBody;
        private Executor callbackExecutor;

        public CheckedCallImpl(Call realCall, Executor callbackExecutor, boolean checked, boolean hasBody) {
            this.realCall = realCall;
            this.checked = checked;
            this.hasBody = hasBody;
            this.callbackExecutor = callbackExecutor;
        }

        @Override
        public Response execute() throws IOException, HttpException, EmptyResponseException, SessionMissingException {
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
        private Response postProcess(Call call, Response response) throws IOException, HttpException, EmptyResponseException, SessionMissingException {
            if (response.code() == 401) {
                Auth.logout();
            }

            if (checked) {
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
