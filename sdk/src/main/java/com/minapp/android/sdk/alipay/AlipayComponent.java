package com.minapp.android.sdk.alipay;

import android.app.Activity;
import android.content.Intent;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.model.OrderResp;
import com.minapp.android.sdk.util.BaseCallback;
import com.minapp.android.sdk.util.Retrofit2CallbackAdapter;
import com.minapp.android.sdk.wechat.WXPayEntryActivity;
import com.minapp.android.sdk.wechat.WechatOrderResult;

public abstract class AlipayComponent {

    /**
     * 发起支付宝支付
     * @param order
     * @param requestCode
     * @param activity
     */
    public static void pay(AlipayOrder order, int requestCode, Activity activity) {
        AlipayActivity.startActivityForResult(order, requestCode, activity);
    }


    /**
     * 获取订单详情
     * @param transactionNo
     * @param cb
     */
    public static void getOrderInfo(String transactionNo, BaseCallback<OrderResp> cb) {
        Global.httpApi().getOrderInfo(transactionNo).enqueue(new Retrofit2CallbackAdapter<OrderResp>(cb));
    }


    /**
     * 从 {@link android.app.Activity#onActivityResult(int, int, Intent)} 里获取额外的信息
     * @param data
     * @return
     */
    public static AlipayOrderResult getOrderResultFromData(Intent data) {
        return AlipayActivity.getOrderResultFromData(data);
    }

}
