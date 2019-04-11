package com.minapp.android.sdk.database.query;

import androidx.annotation.NonNull;
import com.minapp.android.sdk.Global;

import java.util.Collection;
import java.util.List;

public class Where {

    private ConditionNode where = new ConditionNode();
    

    Where _and(WhereOperator op, String lvalue, Object rvalue) {
        where.addCondition(new Condition(op, lvalue, rvalue));
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return Global.gson().toJson(where);
    }



    /*************************  组合：and, or  ******************************/


    public static @NonNull Where and(Where lvalue, Where rvalue) {
        if (lvalue == null && rvalue == null) {
            return new Where();
        }

        if (lvalue == null) {
            return rvalue;
        }

        if (rvalue == null) {
            return lvalue;
        }

        Where newQuery = new Where();
        newQuery.where = ConditionNode.and(lvalue.where, rvalue.where);
        return newQuery;
    }

    public static @NonNull Where or(Where lvalue, Where rvalue) {
        if (lvalue == null && rvalue == null) {
            return new Where();
        }

        if (lvalue == null) {
            return rvalue;
        }

        if (rvalue == null) {
            return lvalue;
        }

        Where newQuery = new Where();
        newQuery.where = ConditionNode.or(lvalue.where, rvalue.where);
        return newQuery;
    }


    /*************************  比较操作符  ******************************/


    public Where equalTo(String lvalue, Object rvalue) {
        return _and(WhereOperator.EQ, lvalue, rvalue);
    }

    public Where notEqualTo(String lvalue, Object rvalue) {
        return _and(WhereOperator.NE, lvalue, rvalue);
    }

    public Where lessThan(String lvalue, Object rvalue) {
        return _and(WhereOperator.LT, lvalue, rvalue);
    }

    public Where lessThanOrEqualTo(String lvalue, Object rvalue) {
        return _and(WhereOperator.LTE, lvalue, rvalue);
    }

    public Where greaterThan(String lvalue, Object rvalue) {
        return _and(WhereOperator.GT, lvalue, rvalue);
    }

    public Where greaterThanOrEqualTo(String lvalue, Object rvalue) {
        return _and(WhereOperator.GTE, lvalue, rvalue);
    }



    /*************************  包含操作符  ******************************/


    public <T> Where containedIn(String lvalue, Collection<T> rvalue) {
        return _and(WhereOperator.IN, lvalue, rvalue);
    }

    public <T> Where notContainedIn(String lvalue, Collection<T> rvalue) {
        return _and(WhereOperator.NIN, lvalue, rvalue);
    }



    /*************************  字符串相关  ******************************/


    public Where contains(String lvalue, String rvalue) {
        return _and(WhereOperator.CONTAINS, lvalue, rvalue);
    }



    /*************************  misc  ******************************/


    public Where isNull(String lvalue) {
        return _and(WhereOperator.IS_NULL, lvalue, null);
    }

    public Where exists(String lvalue) {
        return _and(WhereOperator.EXISTS, lvalue, null);
    }

}
