package com.minapp.android.sdk.ws;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.annotation.AnyThread;
import androidx.annotation.NonNull;

import com.minapp.android.sdk.database.Table;
import com.minapp.android.sdk.database.query.Query;
import com.minapp.android.sdk.util.HandlerExecutor;
import com.minapp.android.sdk.util.LazyProperty;
import com.minapp.android.sdk.ws.exceptions.BeforeConnectException;
import com.minapp.android.sdk.ws.exceptions.UserErrorException;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.crossbar.autobahn.wamp.Session;
import io.crossbar.autobahn.wamp.interfaces.TriConsumer;
import io.crossbar.autobahn.wamp.messages.Hello;
import io.crossbar.autobahn.wamp.messages.Subscribe;
import io.crossbar.autobahn.wamp.messages.Subscribed;
import io.crossbar.autobahn.wamp.messages.Welcome;
import io.crossbar.autobahn.wamp.types.CloseDetails;
import io.crossbar.autobahn.wamp.types.EventDetails;
import io.crossbar.autobahn.wamp.types.SessionDetails;
import io.crossbar.autobahn.wamp.types.SubscribeOptions;

/**
 * WAMP 有两种模式：Pub/Sub（发布/订阅）和 RPC（远程方法调用），这里使用 Pub/Sub 模式
 *
 * Pub/Sub 模式下有三种角色：Subscribers（订阅者），Publishers（发布者）和 Broker（经纪人）；客户端只涉及
 * Subscribers（客户端）和 Broker（服务端）这两种角色
 *
 * WAMP 是应用层协议，需要通过「Handshake」建立连接/会话；客户端发送 HELLO，服务端返回 WELCOME 就表示握手成功，
 * 这一过程由 {@link Session} 完成
 *
 * Subscriber 和 Broker 之间通过 Message 沟通，WAMP 定义了很多 Message 类型：{@link Hello},
 * {@link Welcome}, {@link Subscribe}, {@link Subscribed} 等等，都在包
 * io.crossbar.autobahn.wamp.messages 下面，对应 WAMP 的各种类型的消息
 *
 *
 *
 */
public class SessionManager implements IWampSessionListener {

    private static final LazyProperty<SessionManager> SINGLETON =
            new LazyProperty<SessionManager>() {
                @NonNull
                @Override
                protected SessionManager newInstance() {
                    return new SessionManager();
                }
            };

    public static @NonNull SessionManager get() {
        return SINGLETON.get();
    }


    private final Queue<SubscribeRequest> requests = new ConcurrentLinkedQueue<>();

    private final LazyProperty<Handler> worker = new LazyProperty<Handler>() {
        @NonNull
        @Override
        protected Handler newInstance() {
            HandlerThread worker = new HandlerThread("SessionManager");
            worker.start();
            return new Handler(worker.getLooper());
        }
    };

    private final LazyProperty<WampSession> session = new LazyProperty<WampSession>() {
        @NonNull
        @Override
        protected WampSession newInstance() {
            WampSession session = new WampSession(new HandlerExecutor(worker.get()));
            session.addListener(SessionManager.this);
            return session;
        }
    };

    private SessionManager() {}

    @AnyThread
    public @NonNull
    WampSubscription subscribe(
            @NonNull Table table, @NonNull Query query, @NonNull SubscribeEvent event,
            @NonNull SubscribeCallback cb) {
        SubscribeRequest request = new SubscribeRequest(table.getTableName(), query, event, cb);
        requests.add(request);
        requestInitSession();
        return new WampSubscription(request, this);
    }

    private void requestInitSession() {
        worker.get().post(new Runnable() {
            @Override
            public void run() {
                session.get().connect();
            }
        });
    }

    @Override
    public void onConnect(Session session) {

    }

    @Override
    public void onDisconnect(Session session, boolean wasClean) {

    }

    @Override
    public void onJoin(Session session, SessionDetails details) {

    }

    @Override
    public void onLeave(Session session, CloseDetails details) {

    }

    @Override
    public void onReady(Session session) {
        // invoke init
        SubscribeRequest[] requests = new SubscribeRequest[this.requests.size()];
        requests = this.requests.toArray(requests);
        for (SubscribeRequest request : requests) {
            if (!request.initInvoked) {
                try {
                    request.cb.onInit();
                } catch (Throwable tr) {}
                request.initInvoked = true;
            }
        }

        this.session.get().subscribe(requests);
    }

    @Override
    public void onUserError(Session session, String msg) {
        dispatchException(new UserErrorException(msg));
    }

    @Override
    public void onExceptionBeforeConnection(Throwable tr) {
        dispatchException(new BeforeConnectException(tr));
    }

    private void dispatchException(Exception ex) {
        SubscribeRequest[] requests = new SubscribeRequest[this.requests.size()];
        requests = this.requests.toArray(requests);
        for (SubscribeRequest request : requests) {
            try {
                request.cb.onError(ex);
            } catch (Throwable tr) {}
        }
    }

    void removeRequest(SubscribeRequest request) {
        worker.get().post(new Runnable() {
            @Override
            public void run() {
                requests.remove(request);
                session.get().unsubscribe(request);
            }
        });
    }

}
