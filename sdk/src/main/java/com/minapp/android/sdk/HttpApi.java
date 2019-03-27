package com.minapp.android.sdk;

import com.minapp.android.sdk.auth.*;
import com.minapp.android.sdk.auth.model.SignUpInByEmailReq;
import com.minapp.android.sdk.auth.model.SignUpInByUsernameReq;
import com.minapp.android.sdk.auth.model.SignUpInResp;
import com.minapp.android.sdk.content.Content;
import com.minapp.android.sdk.database.BatchDeleteResp;
import com.minapp.android.sdk.database.Record;
import com.minapp.android.sdk.storage.*;
import com.minapp.android.sdk.storage.category.CategoryInfo;
import com.minapp.android.sdk.storage.category.CreateCategoryBody;
import com.minapp.android.sdk.storage.category.UpdateCategoryBody;
import com.minapp.android.sdk.storage.model.*;
import com.minapp.android.sdk.user.User;
import com.minapp.android.sdk.util.PagedListResponse;
import okhttp3.RequestBody;
import retrofit2.http.*;

import java.util.Collection;
import java.util.Map;


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
    CheckedCall<SignUpInResp> signUpByEmail(@Body SignUpInByEmailReq body);

    /**
     * 通过用户名注册
     * @param body
     * @return
     */
    @POST("hserve/v2.0/register/username/")
    CheckedCall<SignUpInResp> signUpByUsername(@Body SignUpInByUsernameReq body);

    /**
     * 邮箱登录
     * @param body
     * @return
     */
    @POST("hserve/v2.0/login/email/")
    CheckedCall<SignUpInResp> signInByEmail(@Body SignUpInByEmailReq body);

    /**
     * 用户名登录
     * @param body
     * @return
     */
    @POST("hserve/v2.0/login/username/")
    CheckedCall<SignUpInResp> signInByUsername(@Body SignUpInByUsernameReq body);

    /**
     * 匿名登录
     * @return
     */
    @POST("hserve/v2.0/login/anonymous/")
    CheckedCall<SignUpInResp> signInAnonymous(@Body Object body);



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
            @QueryMap Map<String, String> query
    );


    /**
     * 查询记录
     * @param tableName
     * @return
     */
    @GET("hserve/v2.0/table/{table_name}/record/")
    CheckedCall<PagedListResponse<Record>> queryRecord(
            @Path("table_name") String tableName,
            @QueryMap Map<String, String> query
    );

    /**
     * 批量删除
     * @param tableName
     * @return
     */
    @DELETE("hserve/v2.0/table/{tableName}/record/")
    CheckedCall<BatchDeleteResp> batchDelete(
            @Path("tableName") String tableName,
            @QueryMap Map<String, String> query
    );


    /********************************* File api ****************************************/


    /**
     * 上传文件前现获取到上传相关的必要信息
     * @param body
     * @return
     */
    @POST("hserve/v1/upload/")
    CheckedCall<UploadMetaResponse> getUploadMeta(@Body UploadMetaBody body);

    /**
     * 上传文件
     * @param url {@link UploadMetaResponse#uploadUrl}
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
    @GET("oserve/v1/file/{file_id}/")
    CheckedCall<UploadedFile> file(@Path("file_id") String id);


    /**
     * 文件列表
     * @return
     */
    @GET("/hserve/v1.3/uploaded-file/")
    CheckedCall<PagedListResponse<UploadedFile>> files(
            @QueryMap Map<String, String> query
    );

    /**
     * 删除单个文件
     * @param id
     * @return
     */
    @DELETE("oserve/v1/file/{file_id}/")
    CheckedCall<Void> deleteFile(@Path("file_id") String id);

    /**
     * 批量删除文件
     * @param ids
     * @return
     */
    @DELETE("oserve/v1/file/")
    CheckedCall<Void> deleteFiles(@Query("id__in") Collection<String> ids);


    /********************************* Category api ****************************************/


    /**
     * 创建分类
     * @param body
     * @return
     */
    @POST("oserve/v1/file-category/")
    CheckedCall<CategoryInfo> createCategory(@Body CreateCategoryBody body);

    /**
     * 获取分类信息
     * @param id
     * @return
     */
    @GET("oserve/v1/file-category/{category_id}/")
    CheckedCall<CategoryInfo> category(@Path("category_id") String id);

    /**
     * 分类列表
     * @param orderBy
     * @param limit
     * @param offset
     * @return
     */
    @GET("oserve/v1/file-category/")
    CheckedCall<PagedListResponse<CategoryInfo>> categories(
            @Query("order_by") String orderBy,
            @Query("limit") Long limit,
            @Query("offset") Long offset
    );

    /**
     * 修改分类
     * @param body
     * @return
     */
    @PUT("oserve/v1/file-category/{category_id}/")
    CheckedCall<CategoryInfo> updateCategory(
            @Path("category_id") String id,
            @Body UpdateCategoryBody body
    );

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DELETE("oserve/v1/file-category/{category_id}/")
    CheckedCall<Void> deleteCategory(@Path("category_id") String id);



    /********************************* User api ****************************************/


    /**
     * 用户列表
     * @return
     */
    @GET("hserve/v2.0/user/info/")
    CheckedCall<PagedListResponse<User>> userList(@QueryMap Map<String, String> query);



    /********************************* Content api ****************************************/

    /**
     * 内容库列表
     * @param categoryId
     * @param groupId
     * @param query
     * @return
     */
    @GET("hserve/v2.0/content/detail/")
    CheckedCall<PagedListResponse<Content>> contentList(
            @Query("category_id") String categoryId,
            @Query("content_group_id") String groupId,
            @QueryMap Map<String, String> query
    );

    /**
     * 内容明细
     * @param id
     * @return
     */
    @GET("hserve/v2.0/content/detail/{id}/")
    CheckedCall<Content> content(@Path("id") String id);
}
