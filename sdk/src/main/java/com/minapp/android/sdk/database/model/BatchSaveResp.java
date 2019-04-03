package com.minapp.android.sdk.database.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BatchSaveResp {


    /**
     * succeed : 10
     * total_count : 10
     * operation_result : [{"success":{"id":"5bfe000ce74243582bf2979f","created_at":"1543459089"}},{"error":{"code":11000,"err_msg":"数据写入失败，具体错误信息可联系知晓云微信客服：minsupport3 获取。"}}]
     */

    @SerializedName("succeed")
    private Long succeed;                               // 成功创建记录数
    @SerializedName("total_count")
    private Long totalCount;                            // 总的待创建记录数
    @SerializedName("operation_result")
    private List<OperationResult> operationResult;      // 批量写入每一条数据的结果（按 batch save 中 records list 的顺序）

    public Long getSucceed() {
        return succeed;
    }

    public void setSucceed(Long succeed) {
        this.succeed = succeed;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<OperationResult> getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(List<OperationResult> operationResult) {
        this.operationResult = operationResult;
    }

    public static class OperationResult {
        /**
         * success : {"id":"5bfe000ce74243582bf2979f","created_at":"1543459089"}
         * error : {"code":11000,"err_msg":"数据写入失败，具体错误信息可联系知晓云微信客服：minsupport3 获取。"}
         */

        @SerializedName("success")
        private Success success;
        @SerializedName("error")
        private Error error;

        public Success getSuccess() {
            return success;
        }

        public void setSuccess(Success success) {
            this.success = success;
        }

        public Error getError() {
            return error;
        }

        public void setError(Error error) {
            this.error = error;
        }

        public static class Success {
            /**
             * id : 5bfe000ce74243582bf2979f
             * created_at : 1543459089
             */

            @SerializedName("id")
            private String id;
            @SerializedName("created_at")
            private String createdAt;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }
        }

        public static class Error {
            /**
             * code : 11000
             * err_msg : 数据写入失败，具体错误信息可联系知晓云微信客服：minsupport3 获取。
             */

            @SerializedName("code")
            private Long code;
            @SerializedName("err_msg")
            private String errMsg;

            public Long getCode() {
                return code;
            }

            public void setCode(Long code) {
                this.code = code;
            }

            public String getErrMsg() {
                return errMsg;
            }

            public void setErrMsg(String errMsg) {
                this.errMsg = errMsg;
            }
        }
    }
}
