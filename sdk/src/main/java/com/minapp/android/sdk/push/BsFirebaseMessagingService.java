package com.minapp.android.sdk.push;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.minapp.android.sdk.util.BsLog;

/**
 * 接收 FCM 通知
 */
public class BsFirebaseMessagingService extends FirebaseMessagingService {

    private BsLog log = Log.get();

    /**
     *
     * Called when a message is received.
     * This is also called when a notification message is received while the app is in the foreground.
     * The notification parameters can be retrieved with RemoteMessage.getNotification().
     *
     * @param msg
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage msg) {
        log.d("fcm onMessageReceived: %s", msg.getNotification().getTitle());
    }

    /**
     * 此方法只会在 FCM 第一次生成 token 时被调用，后续如果此 token 没有被修改/删除，此方法都不会被调用
     *
     * Called when a new token for the default Firebase project is generated.
     * This is invoked after app install when a token is first generated, and again if the token changes.
     *
     * @param token The token used for sending messages to this application instance.
     *              This token is the same as the one retrieved by FirebaseMessaging.getToken().
     */
    @Override
    public void onNewToken(@NonNull String token) {}
}
