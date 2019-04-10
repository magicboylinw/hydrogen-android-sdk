package com.minapp.android.sdk.user;

import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.database.query.Query;
import com.minapp.android.sdk.util.PagedList;

public abstract class Users {

    /**
     * 用户列表
     * @return
     * @throws Exception
     */
    public static PagedList<User> users(Query query) throws Exception {
        return Global.httpApi().users(query != null ? query : new Query()).execute().body().readonly();
    }

    /**
     * 用户明细
     * @param id
     * @return
     * @throws Exception
     */
    public static User use(String id) throws Exception {
        return Global.httpApi().user(id).execute().body();
    }
}
