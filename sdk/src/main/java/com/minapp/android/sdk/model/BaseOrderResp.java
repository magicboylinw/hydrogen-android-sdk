package com.minapp.android.sdk.model;

import com.google.gson.annotations.SerializedName;

public abstract class BaseOrderResp {

    @SerializedName("transaction_no")
    private String transactionNo;

    @SerializedName("trade_no")
    private String tradeNo;

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
