package dev.thecodewarrior.kotlincpu.computer.cpu.instructions

import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import dev.thecodewarrior.kotlincpu.computer.cpu.Computer
import java.nio.ByteBuffer

abstract class Insn(val name: String, val opcode: UShort, val width: UInt) {
    abstract fun run(cpu: CPU, buffer: ByteBuffer)
}