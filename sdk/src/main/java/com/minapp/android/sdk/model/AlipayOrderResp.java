package com.minapp.android.sdk.model;

import com.google.gson.annotations.SerializedName;

public class AlipayOrderResp extends BaseOrderResp {

    @SerializedName("transaction_no")
    private String transactionNo;

    @SerializedName("trade_no")
    private String tradeNo;

    @SerializedName("payment_url")
    private String paymentUrl;

    @Override
    public String getTransactionNo() {
        return transactionNo;
    }

    @Override
    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    @Override
    public String getTradeNo() {
        return tradeNo;
    }

    @Override
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }
}
