package dev.thecodewarrior.kotlincpu.assembler

import dev.thecodewarrior.kotlincpu.common.Instructions
import org.slf4j.LoggerFactory
import java.io.File

fun main(args: Array<String>) {
    if(args[0] == "--instructions") {
        File(args[1]).writeText(Instructions.instructions.joinToString("\n\n") { it.help })
        return
    }

    val assemblyOrder = AssemblyOrder()
    assemblyOrder.add(File(args[0]))

    val assembler = Assembler()
    assemblyOrder.loadOrder.forEach {
        try {
            assembler.parse(it)
        } catch (e: Exception) {
            logger.error("Error parsing $it")
            throw e
        }
    }
    assembler.finish()

    File(args[1]).writeBytes(assembler.write().array())
    File(args[1] + ".map").writeBytes(assembler.sourceMap())
}

private val logger = LoggerFactory.getLogger("main")