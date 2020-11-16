package com.minapp.android.sdk.test.util

import java.util.concurrent.locks.ReentrantLock

class SimpleCondition {

    private val lock by lazy { ReentrantLock() }
    private val cond by lazy { lock.newCondition() }

    fun await() {
        try {
            lock.lock()
            cond.awaitUninterruptibly()
        } finally {
            lock.unlock()
        }
    }

    fun signalAll() {
        lock.lock()
        cond.signalAll()
        lock.unlock()
    }

}