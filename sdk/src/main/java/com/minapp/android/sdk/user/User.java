package com.minapp.android.sdk.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.minapp.android.sdk.Const;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.database.Record;
import com.minapp.android.sdk.database.Table;
import com.minapp.android.sdk.util.Util;

public class User extends Record {

    /**
     * 昵称，{@link String} 类型
     */
    public static final String NICKNAME = "nickname";

    /**
     * 头像地址，{@link String} 类型
     */
    public static final String AVATAR = "avatar";

    /**
     * 邮箱，{@link String} 类型
     */
    public static final String EMAIL = "_email";

    /**
     * 用户名，{@link String} 类型
     */
    public static final String USERNAME = "_username";

    /**
     * 是否已通过邮箱验证，{@link Boolean} 类型
     */
    public static final String EMAIL_VERIFIED = "_email_verified";

    /**
     * 用户的性别，值为 1 时是男性，值为 2 时是女性，值为 0 时是未知；{@link Integer} 类型
     */
    public static final String GENDER = "gender";

    /**
     * 用户所在省份，{@link String} 类型
     */
    public static final String PROVINCE = "province";

    /**
     * 用户在平台方的用户信息，{@link JsonObject} 类型
     */
    public static final String PROVIDER = "_provider";

    /**
     * 用户是否授权，true 为已授权，false 为未授权；{@link Boolean} 类型
     */
    public static final String IS_AUTHORIZED = "is_authorized";

    /**
     * unionid，{@link String} 类型
     */
    public static final String UNIONID = "unionid";

    /**
     * openid，{@link String} 类型
     */
    public static final String OPENID = "openid";

    /**
     * 用户的语言，{@link String} 类型
     */
    public static final String LANGUAGE = "language";

    /**
     * 用户所在城市，{@link String} 类型
     */
    public static final String CITY = "city";

    /**
     * 用户所在国家，{@link String} 类型
     */
    public static final String COUNTRY = "country";

    /**
     * 登录后得到的 token，{@link String} 类型
     */
    public static final String TOKEN = "token";

    /**
     * 登录后得到的 token 过期时间，单位秒，{@link Integer} 类型
     */
    public static final String EXPIRES_IN = "expires_in";


    public User(Table table) {
        this(table, null);
    }

    public User() {
        this(null, null);
    }

    public User(Table table, JsonObject json) {
        super(new Table(Const.TABLE_USER_PROFILE), json);
    }

    @Override
    public User save() throws Exception {

        // 这里要处理下 pointer 类型
        Record clone = _deepClone();
        JsonObject json = clone._getJson();
        for (String field : json.keySet()) {
            String id = Util.getPointerId(json.get(field));
            if (id != null) {
                json.addProperty(field, id);
            }
        }

        // 更新
        User resp = Global.httpApi().updateUserCustomField(this).execute().body();
        _setJson(resp._getJson());
        return this;
    }
}
