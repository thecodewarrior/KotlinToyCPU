package dev.thecodewarrior.kotlincpu.assembler.instructions

import dev.thecodewarrior.kotlincpu.assembler.Parser
import java.nio.ByteBuffer

abstract class Insn() {
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
    val labels: MutableSet<String> = mutableSetOf()

    /**
     * The instruction size in bytes
     */
    abstract val width: Int

    abstract fun push(buffer: ByteBuffer, parser: Parser)
}