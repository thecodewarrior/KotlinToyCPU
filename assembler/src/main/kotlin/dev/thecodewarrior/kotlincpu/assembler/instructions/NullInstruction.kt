package dev.thecodewarrior.kotlincpu.assembler.instructions

import dev.thecodewarrior.kotlincpu.assembler.Assembler
import dev.thecodewarrior.kotlincpu.common.Instructions
import java.nio.ByteBuffer

class NullInstruction: Instruction(Instructions.pseudo_null) {
    override val width: Int = 0

    override fun push(buffer: ByteBuffer, assembler: Assembler) {
        /* nop */
    }
}