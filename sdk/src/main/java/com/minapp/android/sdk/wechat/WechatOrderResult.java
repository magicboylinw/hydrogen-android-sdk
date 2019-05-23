package com.minapp.android.sdk.wechat;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.mm.opensdk.modelpay.PayResp;

public class WechatOrderResult implements Parcelable {

    /**
     * 如果后台成功创建预付单，相关信息保存在这里
     */
    private WechatOrderResp orderInfo;

    /**
     * 如果微信 sdk 返回了信息，则保存在这里
     */
    private PayResp payResp;

    /**
     * 如果发生了异常，则保存在这里
     */
    private Exception exception;

    public WechatOrderResult() {}

    protected WechatOrderResult(Parcel in) {
        Bundle payResp = in.readBundle();
        if (payResp != null) {
            this.payResp = new PayResp();
            this.payResp.fromBundle(payResp);
        }
        exception = (Exception) in.readSerializable();
        orderInfo = in.readParcelable(WechatOrderResp.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle payResp = null;
        if (this.payResp != null) {
            payResp = new Bundle();
            this.payResp.toBundle(payResp);
        }
        dest.writeBundle(payResp);
        dest.writeSerializable(exception);
        dest.writeParcelable(orderInfo, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WechatOrderResult> CREATOR = new Creator<WechatOrderResult>() {
        @Override
        public WechatOrderResult createFromParcel(Parcel in) {
            return new WechatOrderResult(in);
        }

        @Override
        public WechatOrderResult[] newArray(int size) {
            return new WechatOrderResult[size];
        }
    };

    public PayResp getPayResp() {
        return payResp;
    }

    public void setPayResp(PayResp payResp) {
        this.payResp = payResp;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public WechatOrderResp getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(WechatOrderResp orderInfo) {
        this.orderInfo = orderInfo;
    }
}
