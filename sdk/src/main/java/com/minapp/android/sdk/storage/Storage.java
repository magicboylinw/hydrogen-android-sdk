package com.minapp.android.sdk.storage;

import com.minapp.android.sdk.Global;
import com.minapp.android.sdk.database.query.Query;
import com.minapp.android.sdk.storage.model.BatchDeleteReq;
import com.minapp.android.sdk.storage.model.UploadInfoReq;
import com.minapp.android.sdk.storage.model.UploadInfoResp;
import com.minapp.android.sdk.util.PagedList;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.util.*;

public abstract class Storage {

    static final String PART_AUTHORIZATION = "authorization";
    static final String PART_POLICY = "policy";
    static final String PART_FILE = "file";
    static final int FILE_CHECKING_MILLIS = 500;
    public static final int FILE_CHECKING_MAX = 10;

    /**
     * 文件上传，分两步：<br />
     * 1. 获取上传文件所需授权凭证和上传地址<br />
     * 2. 使用上一步获取的授权凭证和上传地址，进行文件上传
     */
    public static UploadedFile uploadFile(String filename, byte[] data) throws Exception {
        return uploadFile(filename, null, data);
    }

    /**
     * 文件上传，分两步：<br />
     * 1. 获取上传文件所需授权凭证和上传地址<br />
     * 2. 使用上一步获取的授权凭证和上传地址，进行文件上传
     */
    public static UploadedFile uploadFile(String filename, String categoryId, byte[] data) throws Exception {
        UploadInfoReq body = new UploadInfoReq();
        body.setFileName(filename);
        body.setCategoryId(categoryId);
        UploadInfoResp meta = Global.httpApi().getUploadMeta(body).execute().body();

        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(PART_AUTHORIZATION, meta.getAuthorization())
                .addFormDataPart(PART_POLICY, meta.getPolicy())
                .addFormDataPart(PART_FILE, filename, RequestBody.create(null, data))
                .build();
        Global.httpApi().uploadFile(meta.getUploadUrl(), multipartBody).execute();

        UploadedFile uploaded = null;
        int maxLoop = FILE_CHECKING_MAX;
        synchronized (meta) {
            while (maxLoop > 0) {
                try {
                    uploaded = file(meta.getId());
                } catch (Exception ignored) {}
                if (uploaded != null && uploaded.isUploadSuccess()) {
                    return uploaded;
                } else {
                    maxLoop--;
                    meta.wait(FILE_CHECKING_MILLIS);
                }
            }
        }
        return uploaded;
    }


    /**
     * 文件信息
     * @param id
     * @return
     * @throws Exception
     */
    public static UploadedFile file(String id) throws Exception {
        return Global.httpApi().file(id).execute().body();
    }


    /**
     * 查询文件列表
     * @throws Exception
     */
    public static PagedList<UploadedFile> files(Query query) throws Exception {
        return Global.httpApi().files(query != null ? query : new Query()).execute().body().readonly();
    }

    /**
     * 批量删除文件
     * @param ids
     * @throws Exception
     */
    public static void deleteFiles(Collection<String> ids) throws Exception {
        if (ids == null || ids.size() == 0) {
            return;
        }

        if (ids.size() == 1) {
            Global.httpApi().deleteFile(ids.iterator().next()).execute();
        } else {
            Global.httpApi().deleteFiles(new BatchDeleteReq(ids)).execute();
        }
    }

    /**
     * 获取分类
     * @param id
     * @return
     * @throws Exception
     */
    public static FileCategory category(String id) throws Exception {
        return Global.httpApi().fileCategory(id).execute().body();
    }

    /**
     * 列表查询分类
     * @return
     * @throws Exception
     */
    public static PagedList<FileCategory> categories(Query query) throws Exception {
        return Global.httpApi().fileCategories(query).execute().body().readonly();
    }

}
