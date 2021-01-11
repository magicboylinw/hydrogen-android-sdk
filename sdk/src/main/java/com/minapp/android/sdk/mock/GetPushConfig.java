package com.minapp.android.sdk.mock;

import android.content.Context;

import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.model.PushConfig;
import com.minapp.android.sdk.push.DeviceVendor;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class GetPushConfig implements MockCall {

    @Override
    public Response execute(Call call) {
        PushConfig content = new PushConfig();
        String vendor = call.request().url().queryParameter("vendor");

        if (DeviceVendor.MI.name().equalsIgnoreCase(vendor)) {
            content.miAppId = "2882303761517200357";
            content.miAppKey = "5641720097357";
        } else if (DeviceVendor.FLYME.name().equalsIgnoreCase(vendor)) {
            content.flymeAppId = "117952";
            content.flymeAppKey = "5a30aa16cb6145c5b1703bd52989538e";
        } else if (DeviceVendor.OPPO.name().equalsIgnoreCase(vendor)) {
            content.oppoAppKey = "75KrbrNJzD440okgWWgk0kG88";
            content.oppoAppSecret = "347a354D0944c11565b60e154f424455";
        }
        return Response.success(content);
    }

}
