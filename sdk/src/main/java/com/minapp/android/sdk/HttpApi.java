package com.minapp.android.sdk;

import com.minapp.android.sdk.auth.*;
import com.minapp.android.sdk.auth.model.*;
import com.minapp.android.sdk.content.ContentCategory;
import com.minapp.android.sdk.content.ContentGroup;
import com.minapp.android.sdk.database.BatchResult;
import com.minapp.android.sdk.storage.FileCategory;
import com.minapp.android.sdk.content.Content;
import com.minapp.android.sdk.database.Record;
import com.minapp.android.sdk.database.query.BaseQuery;
import com.minapp.android.sdk.storage.*;
import com.minapp.android.sdk.storage.model.*;
import com.minapp.android.sdk.user.User;
import com.minapp.android.sdk.util.BaseStatusResp;
import com.minapp.android.sdk.util.PagedListResponse;
import okhttp3.RequestBody;
import retrofit2.http.*;

import java.util.List;


/**
 * cloud.minapp.com 提供的 http api
 */
public interface HttpApi {


    /********************************* auth api ****************************************/

    /**
     * 通过邮箱注册
     * @param body
     * @return
     */
    @POST("hserve/v2.0/register/email/")
    CheckedCall<SignUpInResp> signUpByEmail(
            @Body SignUpInByEmailReq body
    );

    /**
     * 通过用户名注册
     * @param body
     * @return
     */
    @POST("hserve/v2.0/register/username/")
    CheckedCall<SignUpInResp> signUpByUsername(
            @Body SignUpInByUsernameReq body
    );

    /**
     * 邮箱登录
     * @param body
     * @return
     */
    @POST("hserve/v2.0/login/email/")
    CheckedCall<SignUpInResp> signInByEmail(
            @Body SignUpInByEmailReq body
    );

    /**
     * 用户名登录
     * @param body
     * @return
     */
    @POST("hserve/v2.0/login/username/")
    CheckedCall<SignUpInResp> signInByUsername(
            @Body SignUpInByUsernameReq body
    );

    /**
     * 匿名登录
     * @return
     */
    @POST("hserve/v2.0/login/anonymous/")
    CheckedCall<SignUpInResp> signInAnonymous(
            @Body Object body
    );


    /**
     * 发送验证邮件
     * @return
     */
    @POST("hserve/v2.0/user/email-verify/")
    CheckedCall<BaseStatusResp> emailVerify(
            @Body Object emptyBody
    );


    /**
     * 修改用户用于登录的基本信息
     * @param body
     * @return
     */
    @PUT("hserve/v2.0/user/account/")
    CheckedCall<UpdateUserResp> updateUser(
            @Body UpdateUserReq body
    );


    /**
     * 重置邮箱所属用户密码
     * @param body
     * @return
     */
    @POST("hserve/v2.0/user/password/reset/")
    CheckedCall<BaseStatusResp> resetPwd(
            @Body ResetPwdReq body
    );


    /********************************* Record api ****************************************/


    /**
     * 写入一条记录
     * @param tableName
     * @return
     */
    @POST("hserve/v2.0/table/{table_name}/record/")
    CheckedCall<Record> saveRecord(
            @Path("table_name") String tableName,
            @Body Record body
    );


    /**
     * 批量保存
     * @param tableName
     * @param body
     * @return
     */
    @POST("hserve/v2.0/table/{table_name}/record/")
    CheckedCall<BatchResult> batchSaveRecord(
            @Path("table_name") String tableName,
            @Body List<Record> body
    );


    /**
     * 更新记录
     * @param tableName
     * @param recordId
     * @param body
     * @return
     */
    @PUT("hserve/v2.0/table/{table_name}/record/{record_id}/")
    CheckedCall<Record> updateRecord(
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
    @PUT("hserve/v1.5/table/{tableName}/record/")
    CheckedCall<BatchResult> batchUpdate(
            @Path("tableName") String tableName,
            @QueryMap BaseQuery query,
            @Body Record body
    );

    /**
     * 删除记录
     * @param tableName
     * @param recordId
     * @return
     */
    @DELETE("hserve/v2.0/table/{table_name}/record/{record_id}/")
    CheckedCall<Void> deleteRecord(
            @Path("table_name") String tableName,
            @Path("record_id") String recordId
    );

    /**
     * 获取一条记录
     * @param tableName
     * @param recordId
     * @return
     */
    @GET("hserve/v2.0/table/{table_name}/record/{record_id}/")
    CheckedCall<Record> fetchRecord(
            @Path("table_name") String tableName,
            @Path("record_id") String recordId,
            @QueryMap BaseQuery query
    );


    /**
     * 查询记录
     * @param tableName
     * @return
     */
    @GET("hserve/v2.0/table/{table_name}/record/")
    CheckedCall<PagedListResponse<Record>> queryRecord(
            @Path("table_name") String tableName,
            @QueryMap BaseQuery query
    );

    /**
     * 批量删除
     * @param tableName
     * @return
     */
    @DELETE("hserve/v2.0/table/{tableName}/record/")
    CheckedCall<BatchResult> batchDelete(
            @Path("tableName") String tableName,
            @QueryMap BaseQuery query
    );


    /********************************* File api ****************************************/


    /**
     * 上传文件前现获取到上传相关的必要信息
     * @param body
     * @return
     */
    @POST("hserve/v1/upload/")
    CheckedCall<UploadInfoResp> getUploadMeta(
            @Body UploadInfoReq body
    );

    /**
     * 上传文件
     * @param url {@link UploadInfoResp#uploadUrl}
     * @param body {@link Storage#uploadFile(String, byte[])}
     */
    @POST
    CheckedCall<UploadResponse> uploadFile(
            @Url String url,
            @Body RequestBody body
    );

    /**
     * 文件信息
     * @param id
     * @return
     */
    @GET("hserve/v1.3/uploaded-file/{file_id}/")
    CheckedCall<UploadedFile> file(
            @Path("file_id") String id
    );


    /**
     * 文件列表
     * @return
     */
    @GET("hserve/v1.3/uploaded-file/")
    CheckedCall<PagedListResponse<UploadedFile>> files(
            @QueryMap BaseQuery query
    );

    /**
     * 删除单个文件
     * @param id
     * @return
     */
    @DELETE("hserve/v1.3/uploaded-file/{file_id}/")
    CheckedCall<Void> deleteFile(
            @Path("file_id") String id
    );

    /**
     * 批量删除文件
     * @return
     */
    @HTTP(method = "DELETE", path = "hserve/v1.3/uploaded-file/", hasBody = true)
    CheckedCall<Void> deleteFiles(@Body BatchDeleteReq body);



    /********************************* File Category api ****************************************/


    /**
     * 获取分类信息
     * @param id
     * @return
     */
    @GET("hserve/v1.3/file-category/{id}/")
    CheckedCall<FileCategory> fileCategory(@Path("id") String id);

    /**
     * 分类列表
     */
    @GET("hserve/v1.3/file-category/")
    CheckedCall<PagedListResponse<FileCategory>> fileCategories(
            @QueryMap BaseQuery query
    );



    /********************************* User api ****************************************/


    /**
     * 用户列表
     * @return
     */
    @GET("hserve/v2.0/user/info/")
    CheckedCall<PagedListResponse<User>> users(
            @QueryMap BaseQuery query
    );

    /**
     * 用户明细
     * @param id
     * @return
     */
    @GET("hserve/v2.0/user/info/{id}/")
    CheckedCall<User> user(
            @Path("id") String id
    );





    /********************************* Content api ****************************************/

    /**
     * 内容库列表
     * @param query
     * @return
     */
    @GET("hserve/v2.0/content/detail/")
    CheckedCall<PagedListResponse<Content>> contents(
            @QueryMap BaseQuery query
    );

    /**
     * 内容明细
     * @param id
     * @return
     */
    @GET("hserve/v2.0/content/detail/{id}/")
    CheckedCall<Content> content(
            @Path("id") String id
    );

    /**
     * 内容库列表
     * @param query
     * @return
     */
    @GET("/hserve/v1/content/group/")
    CheckedCall<PagedListResponse<ContentGroup>> contentGroups(
            @QueryMap BaseQuery query
    );

    /**
     * 内容库下的分类列表
     * @param query
     * @return
     */
    @GET("hserve/v1/content/category/")
    CheckedCall<PagedListResponse<ContentCategory>> contentCategories(
            @QueryMap BaseQuery query
    );

    /**
     * 分类详情
     * @param id
     * @return
     */
    @GET("hserve/v1/content/category/{id}/")
    CheckedCall<ContentCategory> contentCategory(
            @Path("id") String id
    );

}
