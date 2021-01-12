package com.minapp.android.sdk.mock;

import android.util.Log;

import com.google.common.io.ByteSink;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.model.PushMetaData;
import com.minapp.android.sdk.util.BsLog;
import com.minapp.android.sdk.util.MediaTypes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Call;
import retrofit2.Response;

public class UploadPushMetaData implements MockCall {

    @Override
    public Response execute(Call call) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BufferedSink sink = Okio.buffer(Okio.sink(out));
            call.request().body().writeTo(sink);
            sink.flush();
            String body = new String(out.toByteArray());
            PushMetaData meta = Global.gson().fromJson(body, PushMetaData.class);
            if (meta.installationId != null && meta.regId != null && meta.vendor != null) {
                return Response.success(null);
            }
        } catch (Exception ignored) {}
        return Response.error(400, ResponseBody.create(MediaTypes.textPlain(), ""));
    }
}
