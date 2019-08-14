package dev.thecodewarrior.kotlincpu.assembler.instructions

import dev.thecodewarrior.kotlincpu.assembler.Context
import dev.thecodewarrior.kotlincpu.assembler.Parser
import dev.thecodewarrior.kotlincpu.common.Opcode
import java.nio.ByteBuffer

abstract class Insn(val opcode: Opcode) {
    /**
     * The assembly
     */
    var sourceMap: SourceMap? = null

    /**
     * The starting address of this instruction
     */
    var address: Int = 0

    /**
     * The labels this instruction is marked with
     */
    lateinit var context: Context

    /**
     * The instruction size in bytes
     */
    val width: Int
        get() = 2 + opcode.payloadWidth

    abstract fun push(buffer: ByteBuffer, parser: Parser)
}