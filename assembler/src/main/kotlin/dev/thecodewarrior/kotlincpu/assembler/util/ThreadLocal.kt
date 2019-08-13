package dev.thecodewarrior.kotlincpu.assembler.util

import kotlin.reflect.KProperty

fun <T : Any?> threadLocal() = ThreadLocalDelegate<T?>(null)
fun <T> threadLocal(initial: () -> T) = ThreadLocalDelegate(initial)

class ThreadLocalDelegate<T>(initial: (() -> T)?) {
    private val local = if (initial == null) ThreadLocal<T>() else ThreadLocal.withInitial(initial)

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = local.get()
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = local.set(value)
}
