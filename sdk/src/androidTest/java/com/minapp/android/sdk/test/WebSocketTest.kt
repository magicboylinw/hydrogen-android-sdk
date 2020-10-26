package com.minapp.android.sdk.test

import android.net.Uri
import android.util.Log
import com.minapp.android.sdk.Config
import com.minapp.android.sdk.Const
import com.minapp.android.sdk.Global
import com.minapp.android.sdk.auth.Auth
import com.minapp.android.sdk.database.query.Query
import com.minapp.android.sdk.test.base.BaseAuthedTest
import com.minapp.android.sdk.ws.SchemeEvent
import com.minapp.android.sdk.ws.SessionListenerAdapter
import io.crossbar.autobahn.wamp.Client
import io.crossbar.autobahn.wamp.Session
import io.crossbar.autobahn.wamp.interfaces.TriConsumer
import io.crossbar.autobahn.wamp.interfaces.TriFunction
import io.crossbar.autobahn.wamp.messages.Subscribe
import io.crossbar.autobahn.wamp.types.CloseDetails
import io.crossbar.autobahn.wamp.types.SessionDetails
import io.crossbar.autobahn.wamp.types.SubscribeOptions
import io.crossbar.autobahn.websocket.Connection
import io.crossbar.autobahn.websocket.WebSocketConnection
import io.crossbar.autobahn.websocket.types.WebSocketOptions
import org.junit.Test
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.Socket
import java.security.Security
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import javax.net.SocketFactory

class WebSocketTest: BaseAuthedTest() {

    companion object {
        private const val TAG = "WebSocketTest"
    }

    private val condition by lazy { lock.newCondition() }

    private val lock by lazy { ReentrantLock() }

    private val gson by lazy { Global.gson() }

    private val listener = object: SessionListenerAdapter() {

        override fun onReady(session: Session) {
            super.onReady(session)

            val option = SubscribeOptions()
            option.put("where", Query())
            session.subscribe(
                "com.ifanrcloud.schema_event.danmu.on_create",
                TriConsumer { var1, var2, var3 ->
                    val event = SchemeEvent(var2)
                    event.after
                }, option)
                .handle { t, u -> }

            session.subscribe(
                "com.ifanrcloud.schema_event.danmu.on_update",
                TriConsumer { var1, var2, var3 ->
                    val event = SchemeEvent(var2)
                    event.after
                }, option)
                .handle { t, u -> }

            session.subscribe(
                "com.ifanrcloud.schema_event.danmu.on_delete",
                TriConsumer { var1, var2, var3 ->
                    val event = SchemeEvent(var2)
                    event.after
                }, option)
                .handle { t, u -> }
        }

        override fun onDisconnect(session: Session?, wasClean: Boolean) {
            super.onDisconnect(session, wasClean)
            condition.signalAll()
        }

        override fun onLeave(session: Session?, details: CloseDetails?) {
            super.onLeave(session, details)
        }
    }

    @Test
    fun test() {
        lock.lock()
        val session = Session(Global.executorService())
        session.addOnConnectListener(listener)
        session.addOnDisconnectListener(listener)
        session.addOnJoinListener(listener)
        session.addOnLeaveListener(listener)
        session.addOnUserErrorListener(listener)
        session.addOnReadyListener(listener)

        val clientId = Config.getClientId()
        val token = Auth.token()
        val envId = Config.getEnvId()
        val pathBuilder = Uri.parse(Const.WAMP_DEFAULT_PATH).buildUpon()
        pathBuilder.appendQueryParameter(Const.HTTP_HEADER_CLIENT_ID.toLowerCase(), clientId)
        pathBuilder.appendQueryParameter(Const.HTTP_HEADER_AUTH.toLowerCase(),
            "${Const.HTTP_HEADER_AUTH_PREFIX}$token")
        if (!envId.isNullOrEmpty())
            pathBuilder.appendQueryParameter(Const.HTTP_HEADER_ENV, envId)
        val path = pathBuilder.build().toString()
        Log.d(TAG, path)
        val client = Client(session, path, "com.ifanrcloud")
        // 起另一个 thread 执行网络连接，在连接前如果检查参数，如果异常这里会抛出
        client.connect()

        try {
            condition.await()
        } finally {
            lock.unlock()
        }
    }
}