package com.minapp.android.sdk.database;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BatchResult {

    @SerializedName("offset")
    private Long offset;                                // 与传入参数 offset 一致
    @SerializedName("limit")
    private Long limit;                                 // 与传入参数 limit 一致
    @SerializedName("next")
    private String next;                                // 下一次的更新链接，若待更新记录数超过上限，可通过该链接继续更新（批量删除和批量更新）
    @SerializedName("succeed")
    private Long succeed;                               // 成功创建、删除、更新的记录数（批量删除和批量更新）
    @SerializedName("total_count")
    private Long totalCount;                            // 总的待创建、待删除、待更新记录数（批量删除和批量更新）
    @SerializedName("operation_result")
    private List<OperationResult> operationResult;      // 批量插入、批量更新操作里每一条数据的结果（按 records list 的顺序）


    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

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
