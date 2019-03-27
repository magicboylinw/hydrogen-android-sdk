package com.minapp.android.sdk.user;

import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.database.query.Query;
import com.minapp.android.sdk.util.PagedList;

import java.util.HashMap;

public abstract class Users {

    /**
     * 用户列表
     * @return
     * @throws Exception
     */
    public static PagedList<User> users(Query query) throws Exception {
        return Global.httpApi().userList(query != null ? query._toQueryMap() : new HashMap<String, String>()).execute().body().readonly();
    }
}
