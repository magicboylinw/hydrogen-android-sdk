package com.minapp.android.sdk.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.minapp.android.sdk.Const;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.auth.model.ResetPwdReq;
import com.minapp.android.sdk.auth.model.UpdateUserReq;
import com.minapp.android.sdk.auth.model.UpdateUserResp;
import com.minapp.android.sdk.database.Table;
import com.minapp.android.sdk.user.User;
import com.minapp.android.sdk.util.BaseCallback;
import com.minapp.android.sdk.util.Util;

import java.util.concurrent.Callable;

public class CurrentUser extends User {

    CurrentUser() {
        super(new Table(Const.TABLE_USER_PROFILE), null);
    }

    CurrentUser(@NonNull User user) {
        super(user._getTable(), user._getJson());
    }

    /**
     * 重置邮箱所属用户密码
     * @param email
     * @return
     * @throws Exception
     */
    public boolean resetPwd(String email) throws Exception {
        ResetPwdReq request = new ResetPwdReq();
        request.setEmail(email);
        return Global.httpApi().resetPwd(request).execute().body().isOk();
    }


    public void resetPwdInBackground(final String email, @NonNull BaseCallback<Boolean> cb) {
        Util.inBackground(cb, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return resetPwd(email);
            }
        });
    }


    /**
     * 修改用户用于登录的基本信息
     * @param request
     * @return
     * @throws Exception
     */
    public UpdateUserResp updateUser(UpdateUserReq request) throws Exception {
        return Global.httpApi().updateUser(request).execute().body();
    }

    public void updateUserInBackground(final UpdateUserReq request, @NonNull BaseCallback<UpdateUserResp> cb) {
        Util.inBackground(cb, new Callable<UpdateUserResp>() {
            @Override
            public UpdateUserResp call() throws Exception {
                return updateUser(request);
            }
        });
    }

    /**
     * 发送验证邮件
     * @return
     * @throws Exception
     */
    public boolean emailVerify() throws Exception {
        return Global.httpApi().emailVerify(new Object()).execute().body().isOk();
    }

    public void emailVerifyInBackground(@NonNull BaseCallback<Boolean> cb) {
        Util.inBackground(cb, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return emailVerify();
            }
        });
    }

    /**
     * session 过期时间戳，单位毫秒
     * @return
     */
    public @Nullable Long getExpiresAt() {
        return Auth.getExpiresAt();
    }

}
