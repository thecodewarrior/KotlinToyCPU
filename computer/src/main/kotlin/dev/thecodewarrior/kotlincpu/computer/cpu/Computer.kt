package dev.thecodewarrior.kotlincpu.computer.cpu

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.ByteBuffer

class Computer : CoroutineScope by CoroutineScope(Dispatchers.Default) {
    val memory = RAM(this, 0xFFFF)
    val cpu = CPU(this)

    fun loadProgram(program: ByteBuffer) {
        memory.buffer.put(program)
        memory.buffer.rewind()
    }

    fun step() {
        cpu.step()
    }
}