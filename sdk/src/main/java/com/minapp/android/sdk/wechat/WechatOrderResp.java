package com.minapp.android.sdk.wechat;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.minapp.android.sdk.model.BaseOrderResp;

public class WechatOrderResp extends BaseOrderResp implements Parcelable {

    @SerializedName("appid")
    private String appId;

    @SerializedName("partnerid")
    private String partnerId;

    @SerializedName("prepayid")
    private String prepayId;

    @SerializedName("package")
    private String packageValue = "Sign=WXPay";

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("noncestr")
    private String nonceStr;

    @SerializedName("sign")
    private String sign;

    public WechatOrderResp() {
        super();
    }

    protected WechatOrderResp(Parcel in) {
        super(in);
        appId = in.readString();
        partnerId = in.readString();
        prepayId = in.readString();
        packageValue = in.readString();
        timestamp = in.readString();
        nonceStr = in.readString();
        sign = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(appId);
        dest.writeString(partnerId);
        dest.writeString(prepayId);
        dest.writeString(packageValue);
        dest.writeString(timestamp);
        dest.writeString(nonceStr);
        dest.writeString(sign);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WechatOrderResp> CREATOR = new Creator<WechatOrderResp>() {
        @Override
        public WechatOrderResp createFromParcel(Parcel in) {
            return new WechatOrderResp(in);
        }

        @Override
        public WechatOrderResp[] newArray(int size) {
            return new WechatOrderResp[size];
        }
    };

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
}
