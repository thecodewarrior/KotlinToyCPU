package dev.thecodewarrior.kotlincpu.assembler

import java.io.File

class AssemblyFile(val file: File) {
    val name: String = file.absolutePath
    val text: String = file.readText()
    val lines: List<String> = text.lines()
    val includes: List<File> = lines
        .filter { it.startsWith("#include") }
        .map {
            file.resolveSibling(it.removePrefix("#include").trim())
        }

    override fun toString(): String {
        return name
    }
}