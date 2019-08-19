package com.minapp.android.sdk.alipay;

import android.os.Parcel;
import android.os.Parcelable;
import com.minapp.android.sdk.model.OrderReq;
import com.minapp.android.sdk.wechat.WechatOrder;

public class AlipayOrder extends OrderReq implements Parcelable {

    public AlipayOrder(float totalCost, String merchandiseDescription) {
        setGatewayType(GATEWAY_ALIPAY);
        setTotalCost(totalCost);
        setMerchandiseDescription(merchandiseDescription);
    }

    protected AlipayOrder(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AlipayOrder> CREATOR = new Creator<AlipayOrder>() {
        @Override
        public AlipayOrder createFromParcel(Parcel in) {
            return new AlipayOrder(in);
        }

        @Override
        public AlipayOrder[] newArray(int size) {
            return new AlipayOrder[size];
        }
    };
}
