package com.minapp.android.test.ui.base

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 *
 * copy from [https://github.com/airbnb/epoxy/blob/master/kotlinsample/src/main/java/com/airbnb/epoxy/kotlinsample/helpers/KotlinEpoxyHolder.kt]
 * a view holder pattern with kotlin
 *
 * A pattern for easier view binding with an [EpoxyHolder]
 *
 * See [com.airbnb.epoxy.kotlinsample.models.ItemEpoxyHolder] for a usage example.
 */
abstract class BaseHolder : EpoxyHolder() {
    private lateinit var view: View

    override fun bindView(itemView: View) {
        view = itemView
    }

    protected fun <V : View> bind(id: Int): ReadOnlyProperty<BaseHolder, V> =
        Lazy { holder: BaseHolder, prop ->
            holder.view.findViewById(id) as V?
                ?: throw IllegalStateException("View ID $id for '${prop.name}' not found.")
        }

    /**
     * Taken from Kotterknife.
     * https://github.com/JakeWharton/kotterknife
     */
    private class Lazy<V>(
        private val initializer: (BaseHolder, KProperty<*>) -> V
    ) : ReadOnlyProperty<BaseHolder, V> {
        private object EMPTY

        private var value: Any? = EMPTY

        override fun getValue(thisRef: BaseHolder, property: KProperty<*>): V {
            if (value == EMPTY) {
                value = initializer(thisRef, property)
            }
            @Suppress("UNCHECKED_CAST")
            return value as V
        }
    }
}