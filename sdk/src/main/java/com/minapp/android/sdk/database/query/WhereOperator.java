package com.minapp.android.sdk.database.query;

import android.text.TextUtils;

public enum WhereOperator {

    CENTER("$center"),         // geo，以某点为圆心，指定半径（km）的圆形范围查询
    INTERSECTS("$intersects"), // geo，交集区域查询
    WITHIN("$within"),         // geo，多边形范围查询
    NEASPHERE("$nearsphere"),   // geo，给定坐标，查询周围的点（由近到远）
    EQ("$eq"),                 // 等于
    NE("$ne"),                 // 不等于
    LT("$lt"),                 // 小于
    LTE("$lte"),               // 小于等于
    GT("$gt"),                 // 大于
    GTE("$gte"),               // 大于等于
    CONTAINS("$contains"),     // string 字段是否包含子串 str
    NIN("$nin"),               // value 是否不存在于 list 字段中
    IN("$in"),                 // value 是否存在于 list 字段中
    IS_NULL("$isnull"),        // 某个字段的值是否为 null
    EXISTS("$exists"),         // 某个字段是否存在于记录中
    HAS_KEY("$has_key"),       // key 是否存在 object 字段中
    ALL("$all"),               // 是否所有 filed 均存在于 list 字段中；list 是右值，它一个数组"[value1, value2]"；filed 必须是数组类型
    REGEX("$regex"),           // string 字段是否匹配正则表达式
    RANGE("$range");           // 包含数组值区间的值

    public final String value;

    WhereOperator(String value) {
        this.value = value;
    }
}
