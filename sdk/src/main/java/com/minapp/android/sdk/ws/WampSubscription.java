package com.minapp.android.sdk.ws;

public class WampSubscription {

    private final SubscribeRequest request;
    private final SessionManager manager;

    public WampSubscription(SubscribeRequest request, SessionManager manager) {
        this.request = request;
        this.manager = manager;
    }

    public void unsubscribe() {
        manager.removeRequest(request);
    }
}
