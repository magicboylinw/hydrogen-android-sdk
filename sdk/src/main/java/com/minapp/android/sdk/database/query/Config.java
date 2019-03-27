package com.minapp.android.sdk.database.query;

import android.text.TextUtils;
import android.view.TextureView;

import java.util.HashMap;
import java.util.Map;

public class Config {

    /**
     * 需要展开的字段（把 pointer 展开，否则 pointer 只返回 table & id）
     * 每一个需要排序的字段以逗号分隔，如：name,age,created_at
     */
    public static final String EXPAND = "expand";

    /**
     * 指定输出/不输出 field（需注意，接口不支持指定输出，不输出的情况，即：?keys=-a,b）
     * 指定输出：keys=horse_name,horse_age
     * 指定不输出；keys=-horse_name,-horse_age
     */
    public static final String KEYS = "keys";

    private String expand;
    private String keys;

    public Config(Config config) {
        if (config != null) {
            this.expand = config.expand;
            this.keys = config.keys;
        }
    }

    public Config() {}

    public Map<String, String> _toQueryMap() {
        Map<String, String> query = new HashMap<>(10);
        if (!TextUtils.isEmpty(expand)) {
            query.put(EXPAND, expand);
        }
        if (!TextUtils.isEmpty(keys)) {
            query.put(KEYS, keys);
        }
        return query;
    }

    public String getExpand() {
        return expand;
    }

    /**
     * @see #EXPAND
     * @param expand
     */
    public void setExpand(String expand) {
        this.expand = expand;
    }

    public String getKeys() {
        return keys;
    }

    /**
     * @see #KEYS
     * @param keys
     */
    public void setKeys(String keys) {
        this.keys = keys;
    }
}
