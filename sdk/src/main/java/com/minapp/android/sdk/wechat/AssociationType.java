package com.minapp.android.sdk.wechat;

public enum AssociationType {

    // 覆盖
    OVERWRITE("overwrite"),

    // 值不存在时设置
    SETNX("setnx"),

    // 不更新
    FALSE("false");

    AssociationType(String value) {
        this.value = value;
    }

    public String value;
}
