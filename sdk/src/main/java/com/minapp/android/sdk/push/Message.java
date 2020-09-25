package com.minapp.android.sdk.push;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.minapp.android.sdk.Assert;
import com.minapp.android.sdk.Global;

public class Message implements Parcelable {

    public Body body = null;

    public Message() {}

    protected Message(Parcel in) {
        body = in.readParcelable(Body.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(body, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void broadcast(@NonNull Context ctx) {
        Assert.notNull(ctx, "Context");
        PushUtil.broadcastMessage(this, ctx);
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public static @NonNull Message parse(@NonNull String content) {
        Message message = new Message();
        message.body = new Body();
        message.body.content = content;
        return message;
    }

    public static class Body implements Parcelable {

        public String content = null;

        public Body() {}

        protected Body(Parcel in) {
            content = in.readString();
        }

        public static final Creator<Body> CREATOR = new Creator<Body>() {
            @Override
            public Body createFromParcel(Parcel in) {
                return new Body(in);
            }

            @Override
            public Body[] newArray(int size) {
                return new Body[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(content);
        }
    }
}
