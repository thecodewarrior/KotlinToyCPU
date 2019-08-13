package dev.thecodewarrior.kotlincpu.assembler.util

import java.util.concurrent.ConcurrentLinkedQueue

class Pool<T: Any>(val constructor: () -> T) {
    private val pool = ConcurrentLinkedQueue<T>()

    @PublishedApi
    internal fun borrow(): T {
        return pool.poll() ?: constructor()
    }

    @PublishedApi
    internal fun release(value: T) {
        pool.add(value)
    }

    inline fun <R> use(crossinline callback: (T) -> R): R {
        val value = borrow()
        val result = callback(value)
        release(value)
        return result
    }
}
