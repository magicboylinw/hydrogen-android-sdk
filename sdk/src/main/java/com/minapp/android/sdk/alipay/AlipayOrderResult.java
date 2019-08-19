package com.minapp.android.sdk.alipay;

import android.os.Parcel;
import android.os.Parcelable;
import com.minapp.android.sdk.model.AlipayOrderResp;
import com.minapp.android.sdk.wechat.WechatOrderResp;
import com.tencent.mm.opensdk.modelpay.PayResp;

public class AlipayOrderResult implements Parcelable {

    /**
     * 如果后台成功创建预付单，相关信息保存在这里
     */
    private AlipayOrderResp orderInfo;

    /**
     * 如果alipay sdk 返回了信息，则保存在这里
     */
    private AlipaySdkResult result;

    /**
     * 如果发生了异常，则保存在这里
     */
    private Exception exception;

    public AlipayOrderResult() {
    }


    protected AlipayOrderResult(Parcel in) {
        orderInfo = in.readParcelable(AlipayOrderResp.class.getClassLoader());
        result = in.readParcelable(AlipaySdkResult.class.getClassLoader());
        exception = (Exception) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(orderInfo, flags);
        dest.writeParcelable(result, flags);
        dest.writeSerializable(exception);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AlipayOrderResult> CREATOR = new Creator<AlipayOrderResult>() {
        @Override
        public AlipayOrderResult createFromParcel(Parcel in) {
            return new AlipayOrderResult(in);
        }

        @Override
        public AlipayOrderResult[] newArray(int size) {
            return new AlipayOrderResult[size];
        }
    };

    public AlipayOrderResp getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(AlipayOrderResp orderInfo) {
        this.orderInfo = orderInfo;
    }

    public AlipaySdkResult getResult() {
        return result;
    }

    public void setResult(AlipaySdkResult result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
