package dev.thecodewarrior.kotlincpu.assembler

import java.io.File

fun main(args: Array<String>) {
    val text = File(args[0]).readText()
    val sourceMapFile = File(args[0]).toRelativeString(File(args[1]).parentFile)
    val parser = Parser(sourceMapFile, text)
    val out = parser.write()
    File(args[1]).writeBytes(out.array())
    File(args[1] + ".map").writeBytes(parser.sourceMap())
}