package com.minapp.android.sdk.model;

import com.google.gson.annotations.SerializedName;

public class OrderResp {
    /**
     * created_at : 2019-05-21T15:18:27.524739
     * created_by : 49638411886970
     * currency_type : CNY
     * gateway_extra_info : {'wechat_response': {'total_fee': '1', 'result_code': 'SUCCESS', 'is_subscribe': 'N', 'return_code': 'SUCCESS', 'appid': 'wx4b3c1aff4c5389f5', 'bank_type': 'CFT', 'sign': 'DC352B555734A7E661ECD4DD56A5F6D5', 'openid': 'okJyP5vpARCks0rTj6JvhuOIvgvE', 'trade_type': 'APP', 'nonce_str': '1hSz2ZRZK9jf0S6aVjc1ExSBTYQxf7P1', 'mch_id': '1488522312', 'cash_fee': '1', 'time_end': '20190521151902', 'fee_type': 'CNY', 'out_trade_no': '1hSz2ZdARNwByqgkrSN1KGZLWu1sngCH', 'transaction_id': '4200000312201905211022577441'}, 'payment_parameters': {'timestamp': '1558423107', 'partnerid': '1488522312', 'noncestr': '1hSz2Z2eH2CBK1lfAGa6YIaGfFSoM5sA', 'package': 'Sign=WXPay', 'appid': 'wx4b3c1aff4c5389f5', 'sign': 'B40EA3419A079F02B12F2BA92F77A325', 'prepayid': 'wx211518277602056429e454e02575140158'}}
     * gateway_type : weixin_tenpay_app
     * id : 224
     * ip_address : 172.16.0.173
     * merchandise_description : 知晓云充值
     * merchandise_record_id : null
     * merchandise_schema_id : null
     * merchandise_snapshot : {}
     * paid_at : 2019-05-21T15:20:05.730733
     * refund_status : null
     * status : success
     * total_cost : 0.01
     * trade_no : 1hSz2ZdARNwByqgkrSN1KGZLWu1sngCH
     * transaction_no : TJGYHzvaiVxOqsgwRiNd9W2bvQbp6X4r
     * updated_at : 2019-05-21T15:20:05.727072
     */

    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("created_by")
    private String createdBy;
    @SerializedName("currency_type")
    private String currencyType;
    @SerializedName("gateway_type")
    private String gatewayType;
    @SerializedName("id")
    private int id;
    @SerializedName("ip_address")
    private String ipAddress;
    @SerializedName("merchandise_description")
    private String merchandiseDescription;
    @SerializedName("merchandise_record_id")
    private String merchandiseRecordId;
    @SerializedName("merchandise_schema_id")
    private String merchandiseSchemaId;
    @SerializedName("merchandise_snapshot")
    private String merchandiseSnapshot;
    @SerializedName("paid_at")
    private String paidAt;
    @SerializedName("refund_status")
    private String refundStatus;
    @SerializedName("status")
    private String status;
    @SerializedName("total_cost")
    private String totalCost;
    @SerializedName("trade_no")
    private String tradeNo;
    @SerializedName("transaction_no")
    private String transactionNo;
    @SerializedName("updated_at")
    private String updatedAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getGatewayType() {
        return gatewayType;
    }

    public void setGatewayType(String gatewayType) {
        this.gatewayType = gatewayType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMerchandiseDescription() {
        return merchandiseDescription;
    }

    public void setMerchandiseDescription(String merchandiseDescription) {
        this.merchandiseDescription = merchandiseDescription;
    }

    public String getMerchandiseRecordId() {
        return merchandiseRecordId;
    }

    public void setMerchandiseRecordId(String merchandiseRecordId) {
        this.merchandiseRecordId = merchandiseRecordId;
    }

    public String getMerchandiseSchemaId() {
        return merchandiseSchemaId;
    }

    public void setMerchandiseSchemaId(String merchandiseSchemaId) {
        this.merchandiseSchemaId = merchandiseSchemaId;
    }

    public String getMerchandiseSnapshot() {
        return merchandiseSnapshot;
    }

    public void setMerchandiseSnapshot(String merchandiseSnapshot) {
        this.merchandiseSnapshot = merchandiseSnapshot;
    }

    public String getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(String paidAt) {
        this.paidAt = paidAt;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
