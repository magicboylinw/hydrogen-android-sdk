package com.minapp.android.sdk.wechat;

import android.os.Parcel;
import com.minapp.android.sdk.model.OrderReq;

public class WechatOrder extends OrderReq {

    public WechatOrder(float totalCost, String merchandiseDescription) {
        setGatewayType(GATEWAY_WECHAT);
        setTotalCost(totalCost);
        setMerchandiseDescription(merchandiseDescription);
    }

    protected WechatOrder(Parcel in) {
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

    public static final Creator<WechatOrder> CREATOR = new Creator<WechatOrder>() {
        @Override
        public WechatOrder createFromParcel(Parcel in) {
            return new WechatOrder(in);
        }

        @Override
        public WechatOrder[] newArray(int size) {
            return new WechatOrder[size];
        }
    };
}
