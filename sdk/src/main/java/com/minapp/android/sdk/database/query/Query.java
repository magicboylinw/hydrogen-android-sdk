package com.minapp.android.sdk.database.query;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.minapp.android.sdk.Global;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Query extends Config {

    /**
     * 查询语句的 JSON String
     */
    public static final String WHERE = "where";

    /**
     * 对资源进行排序
     * 每一个需要排序的字段以逗号分隔，如：name,age,created_at
     * 字段前面加「-」表示 desc，默认是 asc
     *
     */
    public static final String ORDER_BY = "order_by";

    /**
     * 返回资源的个数
     */
    public static final String LIMIT = "limit";

    /**
     * 返回资源的起始偏移值
     */
    public static final String OFFSET = "offset";

    /**
     * 支持 sum, group by 等聚合查询操作
     * 使用原生 MongoDB aggregation 语法
     * 若 query string 同时存在 where 与 aggregation，优先使用 aggregation
     * 当前支持的 pipeline 长度为 2
     * 当前的聚合查询超时时间为 10s
     * limit, offset, order_by 参数与其他版本 API 一致
     * 当前可使用的 stage: * match * group * project * sample * count
     * 当前可使用的 operator: * sum * avg * max * min * size(用于数组) * add * subtract * multiply * divide
     * 更多条件请参照最新的文档
     */
    public static final String AGGREGATION = "aggregation";


    private ConditionNode where = new ConditionNode();
    private String orderBy;
    private Long limit;
    private Long offset;
    private String aggregation;


    public Query() {}

    public Query(Query q) {
        super(q);
        if (q != null) {
            this.where = q.where;
            this.orderBy = q.orderBy;
            this.limit = q.limit;
            this.offset = q.offset;
        }
    }



    Query and(Operator op, String lvalue, Object rvalue) {
        where.addCondition(new Condition(op, lvalue, rvalue));
        return this;
    }

    public Map<String, String> _toQueryMap() {
        Map<String, String> query = new HashMap<>(10);
        String where = this.where.toString();
        if (!TextUtils.isEmpty(where) && !"null".equalsIgnoreCase(where)) {
            query.put(WHERE, where);
        }
        if (!TextUtils.isEmpty(orderBy)) {
            query.put(ORDER_BY, orderBy);
        }
        if (limit != null) {
            query.put(LIMIT, limit.toString());
        }
        if (offset != null) {
            query.put(OFFSET, offset.toString());
        }
        if (!TextUtils.isEmpty(aggregation)) {
            query.put(AGGREGATION, aggregation);
        }
        query.putAll(super._toQueryMap());
        return query;
    }


    /*************************  and, or  ******************************/


    public static @NonNull Query and(Query lvalue, Query rvalue) {
        if (lvalue == null && rvalue == null) {
            return new Query();
        }

        if (lvalue == null) {
            return rvalue;
        }

        if (rvalue == null) {
            return lvalue;
        }

        Query newQuery = new Query(lvalue);
        newQuery.where = ConditionNode.and(lvalue.where, rvalue.where);
        return newQuery;
    }

    public static @NonNull Query or(Query lvalue, Query rvalue) {
        if (lvalue == null && rvalue == null) {
            return new Query();
        }

        if (lvalue == null) {
            return rvalue;
        }

        if (rvalue == null) {
            return lvalue;
        }

        Query newQuery = new Query(lvalue);
        newQuery.where = ConditionNode.or(lvalue.where, rvalue.where);
        return newQuery;
    }


    /*************************  common  ******************************/


    public Query isNull(String lvalue) {
        return and(Operator.IS_NULL, lvalue, null);
    }

    /**
     * @see #ORDER_BY
     * @param column
     * @return
     */
    public Query setOrderBy(String column) {
        this.orderBy = column;
        return this;
    }

    /**
     * @see #LIMIT
     * @param limit
     * @return
     */
    public Query setLimit(long limit) {
        this.limit = limit;
        return this;
    }

    /**
     * @see #OFFSET
     * @param offset
     * @return
     */
    public Query setOffset(long offset) {
        this.offset = offset;
        return this;
    }

    /**
     * @see #AGGREGATION
     * @return
     */
    public String getAggregation() {
        return aggregation;
    }

    public void setAggregation(String aggregation) {
        this.aggregation = aggregation;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public Long getLimit() {
        return limit;
    }

    public Long getOffset() {
        return offset;
    }

    /*************************  string  ******************************/


    public Query eq(String lvalue, String rvalue) {
        return and(Operator.EQ, lvalue, rvalue);
    }

    public Query inString(String lvalue, List<String> rvalue) {
        return and(Operator.IN, lvalue, rvalue);
    }

    public Query ninString(String lvalue, List<String> rvalue) {
        return and(Operator.NIN, lvalue, rvalue);
    }

    public Query ne(String lvalue, String rvalue) {
        return and(Operator.NE, lvalue, rvalue);
    }

    public Query contains(String lvalue, String rvalue) {
        return and(Operator.CONTAINS, lvalue, rvalue);
    }


    /*************************  number  ******************************/


    public Query eq(String lvalue, Number rvalue) {
        return and(Operator.EQ, lvalue, rvalue);
    }

    public Query ne(String lvalue, Number rvalue) {
        return and(Operator.NE, lvalue, rvalue);
    }

    public Query lt(String lvalue, Number rvalue) {
        return and(Operator.LT, lvalue, rvalue);
    }

    public Query lte(String lvalue, Number rvalue) {
        return and(Operator.LTE, lvalue, rvalue);
    }

    public Query gt(String lvalue, Number rvalue) {
        return and(Operator.GT, lvalue, rvalue);
    }

    public Query gte(String lvalue, Number rvalue) {
        return and(Operator.GTE, lvalue, rvalue);
    }

    public <T extends Number> Query inNumber(String lvalue, List<T> rvalue) {
        return and(Operator.IN, lvalue, rvalue);
    }

    public <T extends Number> Query ninNumber(String lvalue, List<T> rvalue) {
        return and(Operator.NIN, lvalue, rvalue);
    }


    /*************************  boolean  ******************************/


    public Query eq(String lvalue, boolean rvalue) {
        return and(Operator.EQ, lvalue, rvalue);
    }



}
