package com.minapp.android.sdk.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class OrderReq implements Parcelable {

    public static final String GATEWAY_WECHAT = "weixin_tenpay_app";    // 微信 APP 支付
    public static final String GATEWAY_ALIPAY = "alipay_app";           // 支付宝 APP 支付

    @SerializedName("gateway_type")
    private String gatewayType;

    @SerializedName("total_cost")
    private float totalCost;

    @SerializedName("merchandise_description")
    private String merchandiseDescription;

    @SerializedName("merchandise_schema_id")
    private String merchandiseSchemaId;

    @SerializedName("merchandise_record_id")
    private String merchandiseRecordId;

    public OrderReq() {}

    protected OrderReq(Parcel in) {
        gatewayType = in.readString();
        totalCost = in.readFloat();
        merchandiseDescription = in.readString();
        merchandiseSchemaId = in.readString();
        merchandiseRecordId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gatewayType);
        dest.writeFloat(totalCost);
        dest.writeString(merchandiseDescription);
        dest.writeString(merchandiseSchemaId);
        dest.writeString(merchandiseRecordId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderReq> CREATOR = new Creator<OrderReq>() {
        @Override
        public OrderReq createFromParcel(Parcel in) {
            return new OrderReq(in);
        }

        @Override
        public OrderReq[] newArray(int size) {
            return new OrderReq[size];
        }
    };

    public String getGatewayType() {
        return gatewayType;
    }

    public void setGatewayType(String gatewayType) {
        this.gatewayType = gatewayType;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getMerchandiseDescription() {
        return merchandiseDescription;
    }

    public void setMerchandiseDescription(String merchandiseDescription) {
        this.merchandiseDescription = merchandiseDescription;
    }

    public String getMerchandiseSchemaId() {
        return merchandiseSchemaId;
    }

    public void setMerchandiseSchemaId(String merchandiseSchemaId) {
        this.merchandiseSchemaId = merchandiseSchemaId;
    }

    public String getMerchandiseRecordId() {
        return merchandiseRecordId;
    }

    public void setMerchandiseRecordId(String merchandiseRecordId) {
        this.merchandiseRecordId = merchandiseRecordId;
    }
}
