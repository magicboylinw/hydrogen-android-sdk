package com.minapp.android.sdk.auth;

import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.minapp.android.sdk.Const;
import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.HttpApi;
import com.minapp.android.sdk.auth.model.*;
import com.minapp.android.sdk.exception.AnonymousNotAllowedException;
import com.minapp.android.sdk.user.User;
import com.minapp.android.sdk.user.Users;
import com.minapp.android.sdk.util.ContentTypeInterceptor;
import com.minapp.android.sdk.util.MemoryCookieJar;
import okhttp3.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class Auth {

    static final String TOKEN = "TOKEN";
    static final String USER_ID = "USER_ID";

    private static final MemoryCookieJar COOKIE_JAR = new MemoryCookieJar();
    private static HttpApi API;
    private static final Object API_LOCK = new Object();
    public static final Map<Object, Object> AUTH_INFO = new HashMap<>();


    /**
     * 登出
     */
    public static void logout() {
        AUTH_INFO.clear();
    }

    /**
     * 是否已登录
     * @return
     */
    public static boolean isSignIn() {
        return AUTH_INFO.get(TOKEN) != null;
    }

    /**
     * 当前登录用户的信息；它会执行一次网络请求，获取到的用户信息总是最新的
     * @return 当未登录或者是匿名登录时，返回 null
     */
    public static @Nullable CurrentUser currentUser() throws Exception {
        String userId = (String) AUTH_INFO.get(USER_ID);
        if (userId != null) {
            return new CurrentUser(Users.use(userId));
        }
        return null;
    }


    /**
     * 邮箱注册
     * @param email
     * @param pwd
     * @return
     * @throws Exception
     */
    public static User signUpByEmail(String email, String pwd) throws Exception {
        User user = Global.httpApi().signUpByEmail(new SignUpInByEmailReq(email, pwd)).execute().body();
        signIn(user);
        return user;
    }

    /**
     * 用户名注册
     * @param username
     * @param pwd
     * @return
     * @throws Exception
     */
    public static User signUpByUsername(String username, String pwd) throws Exception {
        User user = Global.httpApi().signUpByUsername(new SignUpInByUsernameReq(username, pwd)).execute().body();
        signIn(user);
        return user;
    }

    /**
     * 邮箱登录
     * @param email
     * @param pwd
     * @return
     * @throws Exception
     */
    public static User signInByEmail(String email, String pwd) throws Exception {
        User info = Global.httpApi().signInByEmail(new SignUpInByEmailReq(email, pwd)).execute().body();
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
    public static User signInByUsername(String username, String pwd) throws Exception {
        User info = Global.httpApi().signInByUsername(new SignUpInByUsernameReq(username, pwd)).execute().body();
        signIn(info);
        return info;
    }

    /**
     * 匿名登录
     * @return
     * @throws Exception
     */
    public static void signInAnonymous() throws Exception {
        User info = Global.httpApi().signInAnonymous(new Object()).execute().body();
        signIn(info);
    }

    /**
     * 登录成功后，保存用户信息
     * @param info
     */
    private static void signIn(User info) {
        if (info != null) {
            AUTH_INFO.clear();

            String token = info.getString(User.TOKEN);
            if (token != null) {
                AUTH_INFO.put(TOKEN, token);
            }

            String userId = info.getString(User.USER_ID);
            if (userId != null) {
                AUTH_INFO.put(USER_ID, userId);
            }
        }
    }

    static @Nullable String token() {
        return (String) AUTH_INFO.get(TOKEN);
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
}
