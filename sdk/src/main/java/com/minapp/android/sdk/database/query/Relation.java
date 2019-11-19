package com.minapp.android.sdk.database.query;

public enum Relation {

    OR("$or"), AND("$and");

    public final String value;

    Relation(String value) {
        this.value = value;
    }
}
