package com.minapp.android.sdk.auth;

import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.minapp.android.sdk.Const;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.HttpApi;
import com.minapp.android.sdk.auth.model.*;
import com.minapp.android.sdk.exception.AnonymousNotAllowedException;
import com.minapp.android.sdk.util.ContentTypeInterceptor;
import com.minapp.android.sdk.util.MemoryCookieJar;
import okhttp3.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public abstract class Auth {

    private static String CLIENT_ID;
    private static final MemoryCookieJar COOKIE_JAR = new MemoryCookieJar();
    private static HttpApi API;
    private static final Object API_LOCK = new Object();
    private static SignUpInResp AUTH_INFO;

    /**
     * 重置邮箱所属用户密码
     * @param email
     * @return
     * @throws Exception
     */
    public static boolean resetPwd(String email) throws Exception {
        anonymousCheck();
        ResetPwdReq request = new ResetPwdReq();
        request.setEmail(email);
        return Global.httpApi().resetPwd(request).execute().body().isOk();
    }

    /**
     * 修改用户用于登录的基本信息
     * @param request
     * @return
     * @throws Exception
     */
    public static UpdateUserResp updateUser(UpdateUserReq request) throws Exception {
        anonymousCheck();
        return Global.httpApi().updateUser(request).execute().body();
    }

    /**
     * 发送验证邮件
     * @return
     * @throws Exception
     */
    public static boolean emailVerify() throws Exception {
        anonymousCheck();
        return Global.httpApi().emailVerify(new Object()).execute().body().isOk();
    }


    public static void init(String clientId) {
        CLIENT_ID = clientId;
    }


    /**
     * 登出
     */
    public static void logout() {
        AUTH_INFO = null;
    }

    /**
     * 是否已登录
     * @return
     */
    public static boolean isSignIn() {
        return AUTH_INFO != null;
    }

    /**
     * 当前登录用户的信息
     * @return
     */
    public static @Nullable SignUpInResp currentUser() {
        return AUTH_INFO;
    }


    /**
     * 邮箱注册
     * @param email
     * @param pwd
     * @return
     * @throws Exception
     */
    public static SignUpInResp signUpByEmail(String email, String pwd) throws Exception {
        return Global.httpApi().signUpByEmail(new SignUpInByEmailReq(email, pwd)).execute().body();
    }

    /**
     * 用户名注册
     * @param username
     * @param pwd
     * @return
     * @throws Exception
     */
    public static SignUpInResp signUpByUsername(String username, String pwd) throws Exception {
        return Global.httpApi().signUpByUsername(new SignUpInByUsernameReq(username, pwd)).execute().body();
    }

    /**
     * 邮箱登录
     * @param email
     * @param pwd
     * @return
     * @throws Exception
     */
    public static SignUpInResp signInByEmail(String email, String pwd) throws Exception {
        SignUpInResp info = Global.httpApi().signInByEmail(new SignUpInByEmailReq(email, pwd)).execute().body();
        signIn(info);
        return info;
    }

    /**
     * 用户名登录
     * @param username
     * @param pwd
     * @return
     * @throws Exception
     */
    public static SignUpInResp signInByUsername(String username, String pwd) throws Exception {
        SignUpInResp info = Global.httpApi().signInByUsername(new SignUpInByUsernameReq(username, pwd)).execute().body();
        signIn(info);
        return info;
    }

    /**
     * 匿名登录
     * @return
     * @throws Exception
     */
    public static SignUpInResp signInAnonymous() throws Exception {
        SignUpInResp info = Global.httpApi().signInAnonymous(new Object()).execute().body();
        signIn(info);
        return info;
    }

    /**
     * 登录成功后，保存用户信息
     * @param info
     */
    private static void signIn(SignUpInResp info) {
        if (info != null) {
            AUTH_INFO = Global.gson().fromJson(Global.gson().toJson(info), SignUpInResp.class);
        }
    }

    public static @Nullable String clientId() {
        return CLIENT_ID;
    }

    static @Nullable String token() {
        return AUTH_INFO != null ? AUTH_INFO.getToken() : null;
    }

    private static HttpApi httpApi() {
        if (API == null) {
            synchronized (API_LOCK) {
                if (API == null) {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .followRedirects(true)
                            .followSslRedirects(true)
                            .connectTimeout(Const.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
                            .readTimeout(Const.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
                            .writeTimeout(Const.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
                            .cookieJar(new MemoryCookieJar())
                            .retryOnConnectionFailure(true)
                            .addNetworkInterceptor(new ContentTypeInterceptor())
                            .build();

                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Const.HTTP_HOST)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(client)
                            .build();
                    API = retrofit.create(HttpApi.class);
                }
            }
        }
        return API;
    }


    static void anonymousCheck() throws AnonymousNotAllowedException {
        if (AUTH_INFO != null && AUTH_INFO.isAnonymous()) {
            throw new AnonymousNotAllowedException();
        }
    }
}
