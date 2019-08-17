package dev.thecodewarrior.kotlincpu.computer.util

import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class Ticker(val name: String, frequency: Double, var tick: () -> Unit) {
    var frequency: Double = frequency
        set(value) {
            if(field == value) return
            field = value
            restart()
        }
    private val executor = Executors.newSingleThreadScheduledExecutor {
        val thread = Thread(it, name)
        try {
            thread.priority -= 1
        } catch (e: SecurityException) {
        } catch (e: IllegalArgumentException) {
        }
        thread
    }
    private var future: ScheduledFuture<*>? = null

    var running: Boolean
        get() = future != null
        set(value) {
            if(running == value)
                return
            if(value)
                start()
            else
                stop()
        }

    fun start() {
        if(future != null) {
            logger.info("Ticker is already running, start() is a no-op.")
            return
        }
        future = executor.scheduleAtFixedRate({ this.tick() }, 0, roundLong(TimeUnit.SECONDS.toNanos(1) / frequency), TimeUnit.NANOSECONDS)
    }

    fun stop() {
        if(future == null) {
            logger.info("Ticker is not running, stop() is a no-op.")
            return
        }
        try {
            future?.cancel(false)
            future = null
        } catch(e: InterruptedException) {
            future = null
            throw RuntimeException("Interrupt while awaiting termination of ticker", e)
        }
    }

    fun restart() {
        stop()
        start()
    }

    fun shutdown() {
        executor.shutdownNow()
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS)
        } catch(e: InterruptedException) {
            throw RuntimeException("Interrupt while awaiting termination of ticker", e)
        }
    }
}

val logger = LoggerFactory.getLogger(Ticker::class.java)
