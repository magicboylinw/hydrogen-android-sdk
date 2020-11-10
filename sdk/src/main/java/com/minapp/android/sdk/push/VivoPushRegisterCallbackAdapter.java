package com.minapp.android.sdk.push;

import com.heytap.msp.push.callback.ICallBackResultService;

public abstract class VivoPushRegisterCallbackAdapter implements ICallBackResultService {

    @Override
    public void onRegister(int code, String regId) {}

    @Override
    public void onUnRegister(int code) {}

    @Override
    public void onSetPushTime(int code, String s) {}

    @Override
    public void onGetPushStatus(int code, int i1) {}

    @Override
    public void onGetNotificationStatus(int code, int i1) {}

}
