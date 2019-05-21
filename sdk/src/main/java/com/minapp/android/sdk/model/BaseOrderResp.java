package com.minapp.android.sdk.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class BaseOrderResp implements Parcelable {

    @SerializedName("transaction_no")
    private String transactionNo;

    @SerializedName("trade_no")
    private String tradeNo;

    public BaseOrderResp() {}

    protected BaseOrderResp(Parcel in) {
        transactionNo = in.readString();
        tradeNo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(transactionNo);
        dest.writeString(tradeNo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseOrderResp> CREATOR = new Creator<BaseOrderResp>() {
        @Override
        public BaseOrderResp createFromParcel(Parcel in) {
            return new BaseOrderResp(in);
        }

        @Override
        public BaseOrderResp[] newArray(int size) {
            return new BaseOrderResp[size];
        }
    };

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
