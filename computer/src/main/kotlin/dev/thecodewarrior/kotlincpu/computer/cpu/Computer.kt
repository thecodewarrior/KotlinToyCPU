package dev.thecodewarrior.kotlincpu.computer.cpu

import dev.thecodewarrior.kotlincpu.common.Register
import dev.thecodewarrior.kotlincpu.common.util.getUInt
import dev.thecodewarrior.kotlincpu.computer.util.MiB
import dev.thecodewarrior.kotlincpu.computer.util.Ticker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.nio.ByteBuffer

class Computer(val clock: Ticker) : CoroutineScope by CoroutineScope(Dispatchers.Default) {
    val memory = RAM(this, 64 * MiB)
    val cpu = CPU(this)
    val peripherals = Peripherals(this)

    init {
        cpu.registers[Register.sp] = (memory.size - 4).toUInt()
        cpu.registers[Register.fp] = cpu.registers[Register.sp]
    }

    fun loadProgram(program: ByteArray) {
        memory.buffer.rewind()
        memory.buffer.put(program)
        memory.buffer.rewind()
        cpu.pc = memory.buffer.getUInt(0)
    }

    fun step() {
        cpu.step()
        peripherals.step()
    }
}