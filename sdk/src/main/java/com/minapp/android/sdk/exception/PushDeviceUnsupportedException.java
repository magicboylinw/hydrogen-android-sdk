package com.minapp.android.sdk.exception;

import com.minapp.android.sdk.push.DeviceVendor;

public class PushDeviceUnsupportedException extends RuntimeException {
    public PushDeviceUnsupportedException(DeviceVendor vendor) {
        super(String.format("unsupported device(%s) for push",
                vendor != null ? vendor.name() : "unknow"));
    }
}
