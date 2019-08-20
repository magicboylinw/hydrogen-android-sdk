package com.minapp.android.sdk.database.query;

import androidx.annotation.NonNull;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.database.GeoPoint;
import com.minapp.android.sdk.database.GeoPolygon;

import java.util.Collection;

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


    /*************************  geo  ******************************/

    /**
     * 在指定多边形集合中找出包含某一点的多边形
     * @param lvalue
     * @param rvalue
     * @return
     */
    public Where include(String lvalue, GeoPoint rvalue) {
        _and(WhereOperator.INTERSECTS, lvalue, rvalue);
        return this;
    }

    /**
     * 在指定点集合中，查找包含在指定圆心和指定半径所构成的圆形区域中的点 (返回结果随机排序)
     * @param lvalue
     * @param center
     * @param radius km
     * @return
     */
    public Where withinCircle(String lvalue, GeoPoint center, float radius) {
        _and(WhereOperator.CENTER, lvalue, new WithinCircle(center, radius));
        return this;
    }


    /**
     * 在指定点集合中，查找包含在以指定点为圆点，以最大和最小距离为半径，所构成的圆环区域中的点（返回结果按从近到远排序）
     * @param lvalue
     * @param center
     * @param maxDistance m
     * @param minDistance m
     * @return
     */
    public Where withinRegion(String lvalue, GeoPoint center, float maxDistance, float minDistance) {
        _and(WhereOperator.NEASPHERE, lvalue, new WithinRegion(center, maxDistance, minDistance));
        return this;
    }

    /**
     * 在指定点集合中，查找包含于指定的多边形区域的点
     * @param lvalue
     * @param rvalue
     * @return
     */
    public Where withIn(String lvalue, GeoPolygon rvalue) {
        _and(WhereOperator.WITHIN, lvalue, rvalue);
        return this;
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

    public Where contains(String lvalue, Object rvalue) {
        return _and(WhereOperator.CONTAINS, lvalue, rvalue);
    }



    /*************************  null & exists  ******************************/


    public Where isNull(String lvalue) {
        return _and(WhereOperator.IS_NULL, lvalue, true);
    }

    public Where isNotNull(String lvalue) {
        return _and(WhereOperator.IS_NULL, lvalue, false);
    }

    public Where exists(String lvalue) {
        return _and(WhereOperator.EXISTS, lvalue, true);
    }

    public Where notExists(String lvalue) {
        return _and(WhereOperator.EXISTS, lvalue, false);
    }


    /*************************  misc  ******************************/


    public Where hasKey(String lvalue, String rvalue) {
        return _and(WhereOperator.HAS_KEY, lvalue, rvalue);
    }

    public Where arrayContains(String lvalue, Collection rvalue) {
        return _and(WhereOperator.ALL, lvalue, rvalue);
    }

    public Where matchs(String lvalue, Object rvalue) {
        return _and(WhereOperator.REGEX, lvalue, rvalue);
    }

}
