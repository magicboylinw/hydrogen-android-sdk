package com.minapp.android.sdk.wechat;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.minapp.android.sdk.model.OrderReq;

public class WechatOrder extends OrderReq implements Parcelable {

    // 是否分账
    @SerializedName("profit_sharing")
    private Boolean profitSharing = null;

    public WechatOrder(float totalCost, String merchandiseDescription) {
        setGatewayType(GATEWAY_WECHAT);
        setTotalCost(totalCost);
        setMerchandiseDescription(merchandiseDescription);
    }

    /**
     * 是否分账
     * @return
     */
    public Boolean getProfitSharing() {
        return profitSharing;
    }

    /**
     * 是否分账
     * @param profitSharing
     */
    public void setProfitSharing(Boolean profitSharing) {
        this.profitSharing = profitSharing;
    }


    protected WechatOrder(Parcel in) {
        super(in);
        byte tmpProfitSharing = in.readByte();
        profitSharing = tmpProfitSharing == 0 ? null : tmpProfitSharing == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (profitSharing == null ? 0 : profitSharing ? 1 : 2));
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
