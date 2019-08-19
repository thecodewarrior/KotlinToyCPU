package dev.thecodewarrior.kotlincpu.assembler

import dev.thecodewarrior.kotlincpu.common.Instructions
import org.slf4j.LoggerFactory
import java.io.File

fun main(args: Array<String>) {
    if(args[0] == "--instructions") {
        File(args[1]).writeText(Instructions.instructions.joinToString("\n\n") { it.help })
        return
    }
    val text = File(args[0]).readText()
    val sourceMapFile = File(args[0]).toRelativeString(File(args[1]).parentFile)
    val parser = Parser(sourceMapFile, text)
    val out = parser.write()
    File(args[1]).writeBytes(out.array())
    File(args[1] + ".map").writeBytes(parser.sourceMap())
}

private val logger = LoggerFactory.getLogger("main")