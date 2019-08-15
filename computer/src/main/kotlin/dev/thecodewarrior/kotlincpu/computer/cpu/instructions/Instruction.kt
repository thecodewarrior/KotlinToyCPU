package dev.thecodewarrior.kotlincpu.computer.cpu.instructions

import dev.thecodewarrior.kotlincpu.common.Insn
import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import java.nio.ByteBuffer

abstract class Instruction(val insn: Insn) {
    abstract fun run(cpu: CPU, buffer: ByteBuffer)
}