package dev.thecodewarrior.kotlincpu.assembler

import java.io.File

fun main(args: Array<String>) {
    val text = File(args[0]).readText()
    val parser = Parser(args[0], text)
    val out = parser.write()
    File(args[1]).writeBytes(out.array())
}