package com.minapp.android.sdk.alipay;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.minapp.android.sdk.Global;

import java.util.Map;

/**
 * {@link com.alipay.sdk.app.PayTask#payV2(String, boolean)} 返回的是 {@link java.util.Map<String, String>}
 * 这里构造成 pojo 方便用户使用
 */
public class AlipaySdkResult implements Parcelable {

    /**
     * 订单支付成功
     */
    public static final String STATUS_9000 = "9000";

    /**
     * 正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
     */
    public static final String STATUS_8000 = "8000";

    /**
     * 订单支付失败
     */
    public static final String STATUS_4000 = "4000";

    /**
     * 重复请求
     */
    public static final String STATUS_5000 = "5000";

    /**
     * 用户中途取消
     */
    public static final String STATUS_6001 = "6001";

    /**
     * 网络连接出错
     */
    public static final String STATUS_6002 = "6002";

    /**
     * 支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
     */
    public static final String STATUS_6004 = "6004";


    public AlipaySdkResult() {}

    /**
     * @param map 来自 {@link com.alipay.sdk.app.PayTask#payV2(String, boolean)}
     */
    public AlipaySdkResult(Map<String, String> map) {
        if (map != null) {
            memo = map.get("memo");
            resultStatus = map.get("resultStatus");
            String resultStr = map.get("result");
            if (resultStr != null) {
                try {
                    result = Global.gson().fromJson(resultStr, Result.class);
                } catch (Exception e) {}
            }
        }
    }


    @SerializedName("memo")
    private String memo;
    @SerializedName("result")
    private Result result;

    /**
     * @see #STATUS_9000
     * @see #STATUS_8000
     * @see #STATUS_4000
     * @see #STATUS_5000
     * @see #STATUS_6001
     * @see #STATUS_6002
     * @see #STATUS_6004
     */
    @SerializedName("resultStatus")
    private String resultStatus;

    /**
     * 订单支付成功
     * @return
     */
    public boolean isSuccess() {
        return STATUS_9000.equals(resultStatus) && result != null && result.isSuccess();
    }

    /**
     * 支付结果未知，需要进一步查询订单状态以确定支付结果
     * @return
     */
    public boolean isPending() {
        return STATUS_8000.equals(resultStatus) || STATUS_6004.equals(resultStatus);
    }

    protected AlipaySdkResult(Parcel in) {
        memo = in.readString();
        result = in.readParcelable(Result.class.getClassLoader());
        resultStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(memo);
        dest.writeParcelable(result, flags);
        dest.writeString(resultStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AlipaySdkResult> CREATOR = new Creator<AlipaySdkResult>() {
        @Override
        public AlipaySdkResult createFromParcel(Parcel in) {
            return new AlipaySdkResult(in);
        }

        @Override
        public AlipaySdkResult[] newArray(int size) {
            return new AlipaySdkResult[size];
        }
    };

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public static class Result implements Parcelable {

        @SerializedName("alipay_trade_app_pay_response")
        private AlipayTradeAppPayResponse alipayTradeAppPayResponse;
        @SerializedName("sign")
        private String sign;
        @SerializedName("sign_type")
        private String signType;

        public Result() {
        }

        protected Result(Parcel in) {
            alipayTradeAppPayResponse = in.readParcelable(AlipayTradeAppPayResponse.class.getClassLoader());
            sign = in.readString();
            signType = in.readString();
        }

        public boolean isSuccess() {
            return alipayTradeAppPayResponse != null && AlipayTradeAppPayResponse.CODE_SUCCESS.equals(alipayTradeAppPayResponse.getCode());
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(alipayTradeAppPayResponse, flags);
            dest.writeString(sign);
            dest.writeString(signType);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Result> CREATOR = new Creator<Result>() {
            @Override
            public Result createFromParcel(Parcel in) {
                return new Result(in);
            }

            @Override
            public Result[] newArray(int size) {
                return new Result[size];
            }
        };

        public AlipayTradeAppPayResponse getAlipayTradeAppPayResponse() {
            return alipayTradeAppPayResponse;
        }

        public void setAlipayTradeAppPayResponse(AlipayTradeAppPayResponse alipayTradeAppPayResponse) {
            this.alipayTradeAppPayResponse = alipayTradeAppPayResponse;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getSignType() {
            return signType;
        }

        public void setSignType(String signType) {
            this.signType = signType;
        }

        public static class AlipayTradeAppPayResponse implements Parcelable {

            public static final String CODE_SUCCESS = "10000";

            /**
             * 结果码（参考：https://docs.open.alipay.com/common/105806）
             */
            @SerializedName("code")
            private String code;

            /**
             * 处理结果的描述，信息来自于code返回结果的描述，如：success
             */
            @SerializedName("msg")
            private String msg;

            /**
             * 支付宝分配给开发者的应用Id。如：2014072300007148
             */
            @SerializedName("app_id")
            private String appId;

            /**
             * 商户网站唯一订单号，如：70501111111S001111119
             */
            @SerializedName("out_trade_no")
            private String outTradeNo;

            /**
             * 该交易在支付宝系统中的交易流水号。最长64位。如：2014112400001000340011111118
             */
            @SerializedName("trade_no")
            private String tradeNo;

            /**
             * 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01,100000000.00]，精确到小数点后两位。如：9.00
             */
            @SerializedName("total_amount")
            private String totalAmount;

            /**
             * 收款支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字，如：20886894
             */
            @SerializedName("seller_id")
            private String sellerId;

            /**
             * 编码格式，如：utf-8
             */
            @SerializedName("charset")
            private String charset;

            /**
             * 时间，如：2016-10-11 17:43:36
             */
            @SerializedName("timestamp")
            private String timestamp;

            public AlipayTradeAppPayResponse() {
            }

            protected AlipayTradeAppPayResponse(Parcel in) {
                code = in.readString();
                msg = in.readString();
                appId = in.readString();
                outTradeNo = in.readString();
                tradeNo = in.readString();
                totalAmount = in.readString();
                sellerId = in.readString();
                charset = in.readString();
                timestamp = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(code);
                dest.writeString(msg);
                dest.writeString(appId);
                dest.writeString(outTradeNo);
                dest.writeString(tradeNo);
                dest.writeString(totalAmount);
                dest.writeString(sellerId);
                dest.writeString(charset);
                dest.writeString(timestamp);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<AlipayTradeAppPayResponse> CREATOR = new Creator<AlipayTradeAppPayResponse>() {
                @Override
                public AlipayTradeAppPayResponse createFromParcel(Parcel in) {
                    return new AlipayTradeAppPayResponse(in);
                }

                @Override
                public AlipayTradeAppPayResponse[] newArray(int size) {
                    return new AlipayTradeAppPayResponse[size];
                }
            };

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getAppId() {
                return appId;
            }

            public void setAppId(String appId) {
                this.appId = appId;
            }

            public String getOutTradeNo() {
                return outTradeNo;
            }

            public void setOutTradeNo(String outTradeNo) {
                this.outTradeNo = outTradeNo;
            }

            public String getTradeNo() {
                return tradeNo;
            }

            public void setTradeNo(String tradeNo) {
                this.tradeNo = tradeNo;
            }

            public String getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(String totalAmount) {
                this.totalAmount = totalAmount;
            }

            public String getSellerId() {
                return sellerId;
            }

            public void setSellerId(String sellerId) {
                this.sellerId = sellerId;
            }

            public String getCharset() {
                return charset;
            }

            public void setCharset(String charset) {
                this.charset = charset;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }
        }
    }
}
