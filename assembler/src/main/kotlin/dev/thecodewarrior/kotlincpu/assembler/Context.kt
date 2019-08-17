package dev.thecodewarrior.kotlincpu.assembler

import dev.thecodewarrior.kotlincpu.assembler.instructions.Instruction
import dev.thecodewarrior.kotlincpu.assembler.tokenizer.Token

class Context(val parent: Context?) {
    val labels = mutableListOf<Label>()
    val variables = mutableMapOf<String, List<Token>>()

    fun findLabel(name: String): Label {
        return labels.find { it.name == name } ?: error("Undefined label `$name`")
    }
}

class Label(val name: String, var instruction: Instruction? = null)