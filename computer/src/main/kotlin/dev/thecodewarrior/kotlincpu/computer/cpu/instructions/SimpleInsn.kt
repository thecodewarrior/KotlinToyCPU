package dev.thecodewarrior.kotlincpu.computer.cpu.instructions

import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import dev.thecodewarrior.kotlincpu.computer.cpu.Computer
import java.nio.ByteBuffer

class SimpleInsn(name: String, opcode: UShort, width: UInt, val action: (CPU, ByteBuffer) -> Unit): Insn(name, opcode, width) {
    override fun run(cpu: CPU, buffer: ByteBuffer) {
        this.action(cpu, buffer)
    }
}