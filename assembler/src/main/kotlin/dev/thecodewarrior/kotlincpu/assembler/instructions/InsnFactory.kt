package dev.thecodewarrior.kotlincpu.assembler.instructions

import dev.thecodewarrior.kotlincpu.assembler.Assembler
import dev.thecodewarrior.kotlincpu.assembler.tokenizer.Tokenizer

abstract class InsnFactory(val name: String) {
    abstract fun parse(assembler: Assembler, tokenizer: Tokenizer): Instruction
}