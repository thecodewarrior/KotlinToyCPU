package dev.thecodewarrior.kotlincpu.computer.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FrequencyTracker(val windowSize: Int, val updateDelay: Long): CoroutineScope by CoroutineScope(Dispatchers.Default) {
    val ticks = LongArray(windowSize)
    val times = LongArray(windowSize)
    var frequency: Double = 0.0
        private set
    private var current: Int = 0
    private var tick = 0L

    init {
        launch {
            while(true) {
                val head = current % windowSize
                val tail = (current + 1) % windowSize
                ticks[head] = tick
                times[head] = System.nanoTime()
                val timeDelta = times[head] - times[tail]
                val tickDelta = ticks[head] - ticks[tail]
                frequency = (tickDelta * 1_000_000_000.0) / timeDelta
                current++
                delay(updateDelay)
            }
        }
    }

    fun tick() {
        tick++
    }
}