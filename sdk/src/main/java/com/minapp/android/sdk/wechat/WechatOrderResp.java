package com.minapp.android.sdk.wechat;

import com.google.gson.annotations.SerializedName;
import com.minapp.android.sdk.model.BaseOrderResp;

public class WechatOrderResp extends BaseOrderResp {

    @SerializedName("appid")
    private String appId;

    @SerializedName("partnerid")
    private String partnerId;

    @SerializedName("prepayid")
    private String prepayId;

    @SerializedName("package")
    private String packageValue = "Sign=WXPay";

    @SerializedName("timeStamp")
    private String timestamp;

    @SerializedName("nonceStr")
    private String nonceStr;

    @SerializedName("sign")
    private String sign;

    @SerializedName("transaction_no")
    private String transactionNo;

    @SerializedName("trade_no")
    private String tradeNo;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

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
