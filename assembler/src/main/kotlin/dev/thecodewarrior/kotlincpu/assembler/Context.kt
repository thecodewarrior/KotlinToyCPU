package dev.thecodewarrior.kotlincpu.assembler

import dev.thecodewarrior.kotlincpu.assembler.instructions.Insn

class Context(val parent: Context?) {
    val labels = mutableListOf<Label>()
    fun findLabel(name: String): Label {
        return labels.find { it.name == name } ?: error("Undefined label `$name`")
    }
}

class Label(val name: String, var instruction: Insn? = null)