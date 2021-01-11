package com.minapp.android.sdk.util;

import android.os.Handler;

import java.util.concurrent.Executor;

import javax.annotation.Nonnull;

public class HandlerExecutor implements Executor {

    private final Handler handler;

    public HandlerExecutor(@Nonnull Handler handler) {
        this.handler = handler;
    }

    @Override
    public void execute(Runnable command) {
        handler.post(command);
    }
}
