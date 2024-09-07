package kz.hxncus.mc.minesonapikotlin.util.lazy

import java.util.*
import kotlin.reflect.KProperty

fun <This, Return> lazyWithReceiver(initializer: This.() -> Return): LazyWithReceiver<This, Return> = LazyWithReceiver(initializer)

class LazyWithReceiver<This, out Return>(val initializer: This.() -> Return) {
    private val values = WeakHashMap<This, Return>()

    operator fun getValue(thisRef: This, property: KProperty<*>): Return = synchronized(values) {
        return values.getOrPut(thisRef) { thisRef.initializer() }
    }
}