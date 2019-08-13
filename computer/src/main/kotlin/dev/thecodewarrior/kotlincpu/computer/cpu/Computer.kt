package dev.thecodewarrior.kotlincpu.computer.cpu

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.ByteBuffer

class Computer {
    val memory = RAM(this, 0xFFFF)
    val cpu = CPU(this)

    var running: Boolean = false
    var clockSpeed: Int = 10

    fun loadProgram(program: ByteBuffer) {
        memory.buffer.put(program)
        memory.buffer.rewind()
    }

    private val clock = ClockChannel { 1000L / clockSpeed }
    private val postClock = mutableListOf<Channel<Unit>>()

    suspend fun start() {
        while(true) {
            clock.receive()
            if(running) {
                step()
                postClock.forEach { it.offer(Unit) }
            }
        }
    }

    fun step() {
        cpu.step()
    }

    fun createPostClockChannel(): ReceiveChannel<Unit> {
        val channel = Channel<Unit>()
        postClock.add(channel)
        return channel
    }
}