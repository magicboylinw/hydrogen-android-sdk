package com.minapp.android.sdk.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class AlipayOrderResp extends BaseOrderResp implements Parcelable {

    /**
     * SDK 发起支付所需 Query String
     */
    @SerializedName("payment_url")
    private String paymentUrl;

    public AlipayOrderResp() {}

    protected AlipayOrderResp(Parcel in) {
        super(in);
        paymentUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(paymentUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AlipayOrderResp> CREATOR = new Creator<AlipayOrderResp>() {
        @Override
        public AlipayOrderResp createFromParcel(Parcel in) {
            return new AlipayOrderResp(in);
        }

        @Override
        public AlipayOrderResp[] newArray(int size) {
            return new AlipayOrderResp[size];
        }
    };

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }
}
