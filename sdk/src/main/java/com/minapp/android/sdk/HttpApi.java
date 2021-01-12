package com.minapp.android.sdk;

import com.minapp.android.sdk.alipay.AlipayOrder;
import com.minapp.android.sdk.auth.*;
import com.minapp.android.sdk.auth.model.*;
import com.minapp.android.sdk.content.ContentCategory;
import com.minapp.android.sdk.content.ContentGroup;
import com.minapp.android.sdk.database.BatchResult;
import com.minapp.android.sdk.mock.GetPushConfig;
import com.minapp.android.sdk.mock.Mockable;
import com.minapp.android.sdk.mock.UploadPushMetaData;
import com.minapp.android.sdk.model.*;
import com.minapp.android.sdk.storage.FileCategory;
import com.minapp.android.sdk.content.Content;
import com.minapp.android.sdk.database.Record;
import com.minapp.android.sdk.database.query.Query;
import com.minapp.android.sdk.storage.*;
import com.minapp.android.sdk.storage.model.*;
import com.minapp.android.sdk.user.User;
import com.minapp.android.sdk.util.BaseStatusResp;
import com.minapp.android.sdk.util.PagedListResponse;
import com.minapp.android.sdk.wechat.WechatOrder;
import com.minapp.android.sdk.wechat.WechatOrderResp;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


/**
 * cloud.minapp.com 提供的 http api
 */
public interface HttpApi {

    /**
     * 上传推送相关的参数
     * @param meta
     * @return
     */
    @Mockable(UploadPushMetaData.class)
    @POST("hserve/v2.1/upload_push_meta_data")
    Call<Void> uploadPushMetaData(@Body PushMetaData meta);

    /**
     * 拿后台配置的消息推送配置
     * @param vendor
     * @return
     */
    @Mockable(GetPushConfig.class)
    @GET("hserve/v2.1/push-config")
    Call<PushConfig> getPushConfig(
            @retrofit2.http.Query("vendor") String vendor);

    /**
     *
     * 使用短信验证码验证当前登录用户
     *
     * 201:	用户激活成功
     * 400:	参数错误
     * 401:	用户未登录
     *
     * @return
     */
    @POST("hserve/v2.1/sms-phone-verification/")
    Call<Void> smsPhoneVerification(
            @Body SmsPhoneVerificationRequest request
    );


    /**
     * 微信登录
     * @param req
     * @return
     */
    @POST("hserve/v2.3/idp/oauth/wechat-native/authenticate/")
    Call<ThirdPartySignInResp> signInWithWechat(
            @Body ThirdPartySignInReq req
    );

    /**
     * 微博登录
     * @param req
     * @return
     */
    @POST("hserve/v2.3/idp/oauth/weibo-native/authenticate/")
    Call<ThirdPartySignInResp> signInWithWeibo(
            @Body ThirdPartySignInReq req
    );

    /**
     * 关联微信
     * @param req
     * @return
     */
    @POST("hserve/v2.3/idp/oauth/wechat-native/user-association/")
    Call<ThirdPartySignInResp> associationWithWechat (
            @Body ThirdPartySignInReq req
    );

    /**
     * 关联微博
     * @param req
     * @return
     */
    @POST("hserve/v2.3/idp/oauth/weibo-native/user-association/")
    Call<ThirdPartySignInResp> associationWithWeibo (
            @Body ThirdPartySignInReq req
    );

    /**
     * 获取服务器时间
     * @return
     */
    @GET("hserve/v2.2/server/time/")
    Call<ServerDateResp> getServerDate();


    /********************************* pay api ****************************************/

    /**
     * 发起微信支付
     * @param req
     * @return
     */
    @POST("hserve/v2.0/idp/pay/order/")
    Call<WechatOrderResp> requestWechatOrder(
            @Body WechatOrder req
    );

    /**
     * 发起支付宝支付
     * @param req
     * @return
     */
    @POST("hserve/v2.0/idp/pay/order/")
    Call<AlipayOrderResp> requestAlipayOrder(
            @Body AlipayOrder req
    );

    /**
     * 获取订单详情
     * @param transactionNo
     * @return
     */
    @GET("hserve/v2.0/idp/pay/order/{transaction_no}/")
    Call<OrderResp> getOrderInfo (
            @Path("transaction_no") String transactionNo
    );


    /********************************* cloud func api ****************************************/

    /**
     * 触发云函数执行
     * @param req
     * @return
     */
    @POST("hserve/v1/cloud-function/job/")
    Call<CloudFuncResp> invokeCloudFunc(
            @Body CloudFuncReq req
    );


    /********************************* sms api ****************************************/


    /**
     * 发送短信验证码
     * @param req
     * @return
     */
    @POST("hserve/v2.1/sms-verification-code/")
    Call<StatusResp> sendSmsCode (
            @Body SendSmsCodeReq req
    );

    /**
     * 校验短信验证码
     * @param req
     * @return
     */
    @POST("hserve/v1.8/sms-verification-code/verify/")
    Call<StatusResp> verifySmsCode (
            @Body VerifySmsCodeReq req
    );


    /********************************* auth api ****************************************/

    /**
     * 使用手机号 + 短信验证码登录（注册）
     * @param req
     * @return
     */
    @POST("hserve/v2.1/login/sms/")
    Call<User> signInWithPhone(@Body SignInWithPhoneRequest req);

    /**
     * 通过邮箱注册
     * @param body
     * @return
     */
    @POST("hserve/v2.0/register/email/")
    Call<User> signUpWithEmail(
            @Body SignUpInWithEmailReq body
    );

    /**
     * 通过用户名注册
     * @param body
     * @return
     */
    @POST("hserve/v2.0/register/username/")
    Call<User> signUpWithUsername(
            @Body SignUpInWithUsernameReq body
    );

    /**
     * 邮箱登录
     * @param body
     * @return
     */
    @POST("hserve/v2.0/login/email/")
    Call<User> signInWithEmail(
            @Body SignUpInWithEmailReq body
    );

    /**
     * 用户名登录
     * @param body
     * @return
     */
    @POST("hserve/v2.0/login/username/")
    Call<User> signInWithUsername(
            @Body SignUpInWithUsernameReq body
    );

    /**
     * 匿名登录
     * @return
     */
    @POST("hserve/v2.0/login/anonymous/")
    Call<User> signInAnonymous(
            @Body Object body
    );


    /**
     * 发送验证邮件
     * @return
     */
    @POST("hserve/v2.0/user/email-verify/")
    Call<BaseStatusResp> emailVerify(
            @Body Object emptyBody
    );


    /**
     * 修改用户用于登录的基本信息
     * @param body
     * @return
     */
    @PUT("hserve/v2.1/user/account/")
    Call<UpdateUserResp> updateUser(
            @Body UpdateUserReq body
    );


    /**
     * 重置邮箱所属用户密码
     * @param body
     * @return
     */
    @POST("hserve/v2.0/user/password/reset/")
    Call<BaseStatusResp> resetPwd(
            @Body ResetPwdReq body
    );


    /********************************* Record api ****************************************/


    /**
     * 写入一条记录
     * @param tableName
     * @return
     */
    @POST("hserve/v2.1/table/{table_name}/record/")
    Call<Record> saveRecord(
            @Path("table_name") String tableName,
            @Body Record body
    );


    /**
     * 批量保存
     * @param tableName
     * @param body
     * @return
     */
    @POST("hserve/v2.1/table/{table_name}/record/")
    Call<BatchResult> batchSaveRecord(
            @Path("table_name") String tableName,
            @Body List<Record> body,
            @QueryMap Query query
    );


    /**
     * 更新记录
     * @param tableName
     * @param recordId
     * @param body
     * @return
     */
    @PUT("hserve/v2.1/table/{table_name}/record/{record_id}/")
    Call<Record> updateRecord(
            @Path("table_name") String tableName,
            @Path("record_id") String recordId,
            @Body Record body
    );

    /**
     * 批量更新
     * @param tableName
     * @param query
     * @param body
     * @return
     */
    @PUT("hserve/v2.2/table/{tableName}/record/")
    Call<BatchResult> batchUpdate(
            @Path("tableName") String tableName,
            @QueryMap Query query,
            @Body Record body
    );

    /**
     * 删除记录
     * @param tableName
     * @param recordId
     * @return
     */
    @DELETE("hserve/v2.1/table/{table_name}/record/{record_id}/")
    Call<Void> deleteRecord(
            @Path("table_name") String tableName,
            @Path("record_id") String recordId
    );

    /**
     * 获取一条记录
     * @param tableName
     * @param recordId
     * @return
     */
    @GET("hserve/v2.1/table/{table_name}/record/{record_id}/")
    Call<Record> fetchRecord(
            @Path("table_name") String tableName,
            @Path("record_id") String recordId,
            @QueryMap Query query
    );


    /**
     * 查询记录
     * @param tableName
     * @return
     */
    @GET("hserve/v2.2/table/{table_name}/record/")
    Call<PagedListResponse<Record>> queryRecord(
            @Path("table_name") String tableName,
            @QueryMap Query query
    );

    /**
     * 批量删除
     * @param tableName
     * @return
     */
    @DELETE("hserve/v2.2/table/{tableName}/record/")
    Call<BatchResult> batchDelete(
            @Path("tableName") String tableName,
            @QueryMap Query query
    );


    /**
     * 查询异步的批量操作（更新，删除）的结果
     * @param id 异步操作返回的 id
     * @return
     */
    @GET("hserve/v1/bulk-operation/{id}/")
    Call<BatchOperationResp> queryBatchOperation (
            @Path("id") int id
    );


    /********************************* File api ****************************************/


    /**
     * 上传文件前现获取到上传相关的必要信息
     * @param body
     * @return
     */
    @POST("hserve/v1/upload/")
    Call<UploadInfoResp> getUploadMeta(
            @Body UploadInfoReq body
    );

    /**
     * 上传文件
     * @param url {@link UploadInfoResp#getUploadUrl()}
     * @param body {@link Storage#uploadFile(String, byte[])}
     */
    @POST
    Call<UploadResponse> uploadFile(
            @Url String url,
            @Body RequestBody body
    );

    /**
     * 文件信息
     * @param id
     * @return
     */
    @GET("hserve/v2.1/uploaded-file/{file_id}/")
    Call<CloudFile> file(
            @Path("file_id") String id
    );


    /**
     * 文件列表
     * @return
     */
    @GET("hserve/v2.2/uploaded-file/")
    Call<PagedListResponse<CloudFile>> files(
            @QueryMap Query query
    );

    /**
     * 删除单个文件
     * @param id
     * @return
     */
    @DELETE("hserve/v1.3/uploaded-file/{file_id}/")
    Call<Void> deleteFile(
            @Path("file_id") String id
    );

    /**
     * 批量删除文件
     * @return
     */
    @HTTP(method = "DELETE", path = "hserve/v1.3/uploaded-file/", hasBody = true)
    Call<Void> deleteFiles(@Body BatchDeleteReq body);



    /********************************* File Category api ****************************************/


    /**
     * 获取分类信息
     * @param id
     * @return
     */
    @GET("hserve/v1.3/file-category/{id}/")
    Call<FileCategory> fileCategory(@Path("id") String id);

    /**
     * 分类列表
     */
    @GET("hserve/v2.2/file-category/")
    Call<PagedListResponse<FileCategory>> fileCategories(
            @QueryMap Query query
    );



    /********************************* User api ****************************************/


    /**
     * 用户列表
     * @return
     */
    @GET("hserve/v2.2/user/info/")
    Call<PagedListResponse<User>> users(
            @QueryMap Query query
    );

    /**
     * 用户明细
     * @param id
     * @return
     */
    @GET("hserve/v2.1/user/info/{id}/")
    Call<User> user(
            @Path("id") String id
    );


    /**
     * 更新用户信息中的自定义字段
     * @return
     */
    @PUT("/hserve/v2.1/user/info/")
    Call<User> updateUserCustomField (
            @Body User data
    );


    /********************************* Content api ****************************************/

    /**
     * 内容库列表
     * @param query
     * @return
     */
    @GET("hserve/v2.2/content/detail/")
    Call<PagedListResponse<Content>> contents(
            @QueryMap Query query
    );

    /**
     * 内容明细
     * @param id
     * @return
     */
    @GET("hserve/v2.0/content/detail/{id}/")
    Call<Content> content(
            @Path("id") String id
    );

    /**
     * 内容库列表
     * @param query
     * @return
     */
    @GET("/hserve/v2.2/content/group/")
    Call<PagedListResponse<ContentGroup>> contentGroups(
            @QueryMap Query query
    );

    /**
     * 内容库下的分类列表
     * @param query
     * @return
     */
    @GET("hserve/v2.2/content/category/")
    Call<PagedListResponse<ContentCategory>> contentCategories(
            @QueryMap Query query
    );

    /**
     * 分类详情
     * @param id
     * @return
     */
    @GET("hserve/v1/content/category/{id}/")
    Call<ContentCategory> contentCategory(
            @Path("id") String id
    );

}
