package com.minapp.android.sdk.user;

import androidx.annotation.Nullable;
import com.minapp.android.sdk.database.Record;

public class Provider extends Record {

    public static final String USER_ID = "user_id";
    public static final String AVATAR = "avatar";
    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String NICK_NAME = "nick_name";
    public static final String IS_STUDENT = "is_student";
    public static final String USER_TYPE = "user_type";
    public static final String USER_STATUS = "user_status";
    public static final String VERIFIED = "verified";
    public static final String GENDER = "gender";

    public @Nullable String getUserId() {
        return getString(USER_ID);
    }

    public Provider setUserId(String userId) {
        put(USER_ID, userId);
        return this;
    }

    public @Nullable String getAvatar() {
        return getString(AVATAR);
    }

    public Provider setAvatar(String avatar) {
        put(AVATAR, avatar);
        return this;
    }

    public @Nullable String getProvince() {
        return getString(PROVINCE);
    }

    public Provider setProvince(String province) {
        put(PROVINCE, province);
        return this;
    }

    public @Nullable String getCity() {
        return getString(CITY);
    }

    public Provider setCity(String city) {
        put(CITY, city);
        return this;
    }

    public @Nullable String getNickName() {
        return getString(NICK_NAME);
    }

    public Provider setNickName(String nickName) {
        put(NICK_NAME, nickName);
        return this;
    }

    public @Nullable Boolean isStudent() {
        return getBoolean(IS_STUDENT);
    }

    public Provider setIsStudent(Boolean isStudent) {
        put(IS_STUDENT, isStudent);
        return this;
    }

    public @Nullable String getUserType() {
        return getString(USER_TYPE);
    }

    public Provider setUserType(String userType) {
        put(USER_TYPE, userType);
        return this;
    }

    public @Nullable String getUserStatus() {
        return getString(USER_STATUS);
    }

    public Provider setUserStatus(String userStatus) {
        put(USER_STATUS, userStatus);
        return this;
    }

    public @Nullable Boolean isVerified() {
        return getBoolean(VERIFIED);
    }

    public Provider setIsVerified(Boolean verified) {
        put(VERIFIED, verified);
        return this;
    }

    public @Nullable String getGender() {
        return getString(GENDER);
    }

    public Provider setGender(String gender) {
        put(GENDER, gender);
        return this;
    }
}
