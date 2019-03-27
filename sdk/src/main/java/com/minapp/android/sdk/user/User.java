package com.minapp.android.sdk.user;

import androidx.annotation.Nullable;
import com.google.gson.reflect.TypeToken;
import com.minapp.android.sdk.database.Record;

import java.util.Map;

public class User extends Record {

    public static final String AVATAR = "avatar";
    public static final String PROVIDER = "_provider";
    public static final String USERNAME = "_username";
    public static final String EMAIL_VERIFIED = "_email_verified";
    public static final String IS_AUTHORIZED = "is_authorized";
    public static final String EMAIL = "_email";

    public @Nullable String getEmail() {
        return getString(EMAIL);
    }

    public User setEmail(String email) {
        put(EMAIL, email);
        return this;
    }

    public @Nullable String getAvatar() {
        return getString(AVATAR);
    }

    public User setAvatar(String avatar) {
        put(AVATAR, avatar);
        return this;
    }

    public @Nullable String getUserName() {
        return getString(USERNAME);
    }

    public User setUserName(String username) {
        put(USERNAME, username);
        return this;
    }

    public @Nullable Boolean isEmailVerified() {
        return getBoolean(EMAIL_VERIFIED);
    }

    public User setEmailVerified(Boolean verified) {
        put(EMAIL_VERIFIED, verified);
        return this;
    }

    public @Nullable Boolean isAuthorized() {
        return getBoolean(IS_AUTHORIZED);
    }

    public User setAuthorized(Boolean authorized) {
        put(IS_AUTHORIZED, authorized);
        return this;
    }

    public @Nullable Map<String, Provider> getProvider() {
        try {
            return getObject(PROVIDER, new TypeToken<Map<String, Provider>>(){}.getType());
        } catch (Exception e) {
            return null;
        }
    }

    public User setProvider(Map<String, Provider> provider) {
        put(PROVIDER, provider);
        return this;
    }
}
