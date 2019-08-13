package dev.thecodewarrior.kotlincpu.computer.cpu

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class ClockChannel(val periodMillis: () -> Long): Channel<Unit> by Channel() {
    init {
        val weakThis = WeakReference(this)

        GlobalScope.launch {
            while(true) {
                val clock = weakThis.get() ?: break
                clock.offer(Unit)
                delay(clock.periodMillis())
            }
        }
    }
}