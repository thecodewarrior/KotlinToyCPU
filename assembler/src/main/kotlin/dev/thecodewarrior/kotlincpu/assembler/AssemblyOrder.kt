package dev.thecodewarrior.kotlincpu.assembler

import java.io.File
import java.util.LinkedList

class AssemblyOrder() {
    val loadOrder = mutableListOf<AssemblyFile>()
    val files = mutableMapOf<File, AssemblyFile>()
    private val resolving = LinkedList<File>()

    fun add(file: File) {
        if(file in resolving)
            throw error("Circular dependency: ${resolving.joinToString("\n -> ")}\n -> $file")
        if(file in files) return
        val assemblyFile = AssemblyFile(file)
        resolving.push(file)
        assemblyFile.includes.forEach { this.add(it) }
        loadOrder.add(assemblyFile)
        resolving.pop()
    }
}
