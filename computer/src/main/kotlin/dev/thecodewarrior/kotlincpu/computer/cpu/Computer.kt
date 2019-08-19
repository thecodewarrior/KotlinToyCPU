package dev.thecodewarrior.kotlincpu.computer.cpu

import dev.thecodewarrior.kotlincpu.common.Register
import dev.thecodewarrior.kotlincpu.computer.util.MiB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.nio.ByteBuffer

class Computer : CoroutineScope by CoroutineScope(Dispatchers.Default) {
    val memory = RAM(this, 64 * MiB)
    val cpu = CPU(this)

    init {
        cpu.registers[Register.sp] = (memory.size - 4).toUInt()
        cpu.registers[Register.fp] = cpu.registers[Register.sp]
    }

    fun loadProgram(program: ByteBuffer) {
        memory.buffer.put(program)
        memory.buffer.rewind()
    }

    fun step() {
        cpu.step()
    }

}