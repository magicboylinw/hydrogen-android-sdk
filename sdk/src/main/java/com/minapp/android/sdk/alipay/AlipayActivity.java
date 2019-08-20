package com.minapp.android.sdk.alipay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.alipay.sdk.app.PayTask;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.exception.HttpException;
import com.minapp.android.sdk.model.AlipayOrderResp;
import com.minapp.android.sdk.util.StatusBarUtil;
import retrofit2.Response;

import java.util.Map;

public class AlipayActivity extends Activity {

    static final String ORDER = "ORDER";
    static final String RESULT = "RESULT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View content = new View(this);
        content.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        content.setBackgroundColor(Color.TRANSPARENT);
        setContentView(content);

        StatusBarUtil.setStatusBar(Color.TRANSPARENT, true, false, this);
        sendPay();
    }

    private void sendPay() {
        final AlipayOrder order = getIntent().getParcelableExtra(ORDER);
        Global.submit(new Runnable() {
            @Override
            public void run() {
                AlipayOrderResp orderInfo = null;
                try {

                    // 与 Baas 交互，后端生成预付单，前端拿到 payment url
                    Response<AlipayOrderResp> resp = Global.httpApi().requestAlipayOrder(order).execute();
                    if (isDestroyed()) return;
                    if (!resp.isSuccessful()) {
                        throw HttpException.valueOf(resp);
                    }
                    orderInfo = resp.body();

                    // 拉起支付宝
                    Map<String, String> map = new PayTask(AlipayActivity.this).payV2(orderInfo.getPaymentUrl(), true);
                    if (isDestroyed()) return;
                    AlipaySdkResult result = new AlipaySdkResult(map);
                    boolean success = result.isSuccess();
                    close(success, orderInfo, result, null);

                } catch (Exception e) {
                    if (!isDestroyed()) {
                        close(false, orderInfo, null, e);
                    }
                }
            }
        });
    }

    private void close(boolean success, AlipayOrderResp order, AlipaySdkResult result, Exception exception) {
        AlipayOrderResult output = new AlipayOrderResult();
        output.setOrderInfo(order);
        output.setResult(result);
        output.setException(exception);

        Intent data = new Intent();
        data.putExtra(RESULT, output);
        setResult(success ? RESULT_OK : RESULT_CANCELED, data);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        close(false, null, null, null);
    }

    public static void startActivityForResult(AlipayOrder order, int requestCode, Activity activity) {
        Intent intent = new Intent(activity, AlipayActivity.class);
        intent.putExtra(ORDER, order);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    public static AlipayOrderResult getOrderResultFromData(Intent data) {
        AlipayOrderResult result = null;
        if (data != null) {
            result = data.getParcelableExtra(RESULT);
        }
        return result;
    }
}
