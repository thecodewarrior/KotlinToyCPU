package dev.thecodewarrior.kotlincpu.computer.cpu.instructions

import dev.thecodewarrior.kotlincpu.common.Opcode
import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUShort
import java.nio.ByteBuffer

abstract class Insn(val opcode: Opcode) {
    val name: String = opcode.name
    val width: Int = opcode.payloadWidth + 2

    open fun matches(buffer: ByteBuffer, address: Int): Boolean {
        return buffer.getUShort(address) == this.opcode.opcode
    }

    open fun dataStart(buffer: ByteBuffer, address: Int): Int {
        return address + 2
    }

    abstract fun run(cpu: CPU, buffer: ByteBuffer)
}