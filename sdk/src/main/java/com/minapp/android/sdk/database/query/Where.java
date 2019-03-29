package com.minapp.android.sdk.database.query;

import androidx.annotation.NonNull;
import com.minapp.android.sdk.Global;

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

    /*************************  and, or  ******************************/


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


    /*************************  string  ******************************/

    public Where isNull(String lvalue) {
        return _and(WhereOperator.IS_NULL, lvalue, null);
    }

    public Where eq(String lvalue, String rvalue) {
        return _and(WhereOperator.EQ, lvalue, rvalue);
    }

    public Where inString(String lvalue, List<String> rvalue) {
        return _and(WhereOperator.IN, lvalue, rvalue);
    }

    public Where ninString(String lvalue, List<String> rvalue) {
        return _and(WhereOperator.NIN, lvalue, rvalue);
    }

    public Where ne(String lvalue, String rvalue) {
        return _and(WhereOperator.NE, lvalue, rvalue);
    }

    public Where contains(String lvalue, String rvalue) {
        return _and(WhereOperator.CONTAINS, lvalue, rvalue);
    }


    /*************************  number  ******************************/


    public Where eq(String lvalue, Number rvalue) {
        return _and(WhereOperator.EQ, lvalue, rvalue);
    }

    public Where ne(String lvalue, Number rvalue) {
        return _and(WhereOperator.NE, lvalue, rvalue);
    }

    public Where lt(String lvalue, Number rvalue) {
        return _and(WhereOperator.LT, lvalue, rvalue);
    }

    public Where lte(String lvalue, Number rvalue) {
        return _and(WhereOperator.LTE, lvalue, rvalue);
    }

    public Where gt(String lvalue, Number rvalue) {
        return _and(WhereOperator.GT, lvalue, rvalue);
    }

    public Where gte(String lvalue, Number rvalue) {
        return _and(WhereOperator.GTE, lvalue, rvalue);
    }

    public <T extends Number> Where inNumber(String lvalue, List<T> rvalue) {
        return _and(WhereOperator.IN, lvalue, rvalue);
    }

    public <T extends Number> Where ninNumber(String lvalue, List<T> rvalue) {
        return _and(WhereOperator.NIN, lvalue, rvalue);
    }


    /*************************  boolean  ******************************/


    public Where eq(String lvalue, boolean rvalue) {
        return _and(WhereOperator.EQ, lvalue, rvalue);
    }
}
