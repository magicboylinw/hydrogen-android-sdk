package com.ifanr.activitys.core.arch

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

interface Action

interface Change: Action

interface ViewState

typealias Reducer<S, C> = (S, C) -> S



/**
 * MVI
 */
class ActionBusFactory private constructor (
        private val owner: LifecycleOwner
) {

    private val map = HashMap<Class<*>, Subject<*>>()

    private val observer = object: LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            map.forEach { it.value.onComplete() }
            buses.remove(owner)
        }
    }

    init {
        owner.lifecycle.addObserver(observer)
    }

    private fun <T : Action> create(clz: Class<T>): Subject<T> {
        val subject = PublishSubject.create<T>().toSerialized()
        map[clz] = subject
        return subject
    }

    fun <T : Action> getSafeManagedObservable(clz: Class<T>): Observable<T> {
        return if (map[clz] != null) map[clz] as Observable<T> else create(clz)
    }

    fun <T : Action> getAutoDisposeObservable(clz: Class<T>): ObservableSubscribeProxy<T> {
        return getSafeManagedObservable(clz)
                .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
    }

    fun <T : Action> getSafeManagedEmitter(clz: Class<T>): Observer<T> {
        return if (map[clz] != null) map[clz] as Observer<T> else create(clz)
    }

    fun <T : Action> emit(clz: Class<T>, event: T) {
        getSafeManagedEmitter(clz = clz).onNext(event)
    }

    companion object {

        private val buses = HashMap<LifecycleOwner, ActionBusFactory>()

        fun get(owner: LifecycleOwner): ActionBusFactory {
            var factory = buses[owner]
            if (factory == null) {
                factory = ActionBusFactory(owner = owner)
                buses[owner] = factory
            }
            return factory
        }
    }

}

inline fun <reified T : Action> LifecycleOwner.getSafeManagedObservable(): Observable<T> {
    return ActionBusFactory.get(this).getSafeManagedObservable(clz = T::class.java)
}

inline fun <reified T : Action> LifecycleOwner.getAutoDisposeObservable(): ObservableSubscribeProxy<T> {
    return ActionBusFactory.get(this).getAutoDisposeObservable(T::class.java)
}

inline fun <reified T : Action> LifecycleOwner.getSafeManagedEmitter(): Observer<T> =
        ActionBusFactory.get(this).getSafeManagedEmitter(T::class.java)

inline fun <reified T : Action> LifecycleOwner.emit(event: T) =
        ActionBusFactory.get(this).emit(clz = T::class.java, event = event)

val LifecycleOwner.actionBus: ActionBusFactory
get() = ActionBusFactory.get(this)