package dev.thecodewarrior.kotlincpu.computer.cpu.instructions

import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUByte
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUInt
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getULong
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUShort
import java.nio.ByteBuffer

object InstructionRegistry {
    val instructions: List<Insn> = listOf(
        SimpleInsn("NOP", 0x0u, 0u) { _, _ -> },
        SimpleInsn("HALT", 0xFFFFu, 0u) { cpu, _ ->
            cpu.computer.running = false
        }
    ) +
        ConstInsn.instructions +
        ArithmeticInsn.instructions +
        JumpInsn.instructions

    fun findInsn(buffer: ByteBuffer, address: Int): Insn? = instructions.find { it.matches(buffer, address) }
}