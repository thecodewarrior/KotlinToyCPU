package dev.thecodewarrior.kotlincpu.assembler.instructions

import dev.thecodewarrior.kotlincpu.assembler.Parser
import dev.thecodewarrior.kotlincpu.assembler.tokenizer.Tokenizer

abstract class InsnFactory(val name: String) {
    abstract fun parse(parser: Parser, tokenizer: Tokenizer): Instruction
}