package dev.thecodewarrior.kotlincpu.assembler.instructions

import dev.thecodewarrior.kotlincpu.assembler.Context
import dev.thecodewarrior.kotlincpu.assembler.Parser
import dev.thecodewarrior.kotlincpu.common.Condition
import dev.thecodewarrior.kotlincpu.common.Insn
import java.nio.ByteBuffer

abstract class Instruction(val insn: Insn) {
    /**
     * The assembly
     */
    lateinit var location: Location

    /**
     * The starting address of this instruction
     */
    var address: Int = 0

    /**
     * The labels this instruction is marked with
     */
    lateinit var context: Context

    /**
     * The labels this instruction is marked with
     */
    var condition: Condition = Condition.YES

    abstract fun push(buffer: ByteBuffer, parser: Parser)
}