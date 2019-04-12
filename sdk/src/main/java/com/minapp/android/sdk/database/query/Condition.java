package com.minapp.android.sdk.database.query;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * 要生成这么个 json 结构：
 * {
 *      "price（左值）": {
 *          "$lt"（操作符）: 100（右值，不一定有）
 *      }
 * }
 */
public class Condition {

    private WhereOperator operator;
    private String lvalue;
    private Object rvalue;

    Condition(WhereOperator operator, String lvalue, Object rvalue) {
        this.operator = operator;
        this.lvalue = lvalue;
        this.rvalue = rvalue;
    }

    public static class Serializer implements JsonSerializer<Condition> {
        @Override
        public JsonElement serialize(Condition src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject root = new JsonObject();
            if (src.operator != null && src.lvalue != null) {
                JsonObject value = new JsonObject();
                value.add(src.operator.value, context.serialize(src.rvalue));
                root.add(src.lvalue, value);
            }
            return root;
        }
    }
}
