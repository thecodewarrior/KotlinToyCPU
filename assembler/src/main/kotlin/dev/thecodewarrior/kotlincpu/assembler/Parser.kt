package dev.thecodewarrior.kotlincpu.assembler

import dev.thecodewarrior.kotlincpu.assembler.instructions.Insn
import dev.thecodewarrior.kotlincpu.assembler.instructions.InstructionRegistry
import dev.thecodewarrior.kotlincpu.assembler.instructions.SourceMap
import dev.thecodewarrior.kotlincpu.assembler.tokenizer.Tokenizer
import dev.thecodewarrior.kotlincpu.assembler.util.buildBytes
import org.slf4j.LoggerFactory
import java.nio.ByteBuffer

class Parser(val file: String, val text: String) {
    val registers = mutableMapOf<String, UByte>()

    val instructions = mutableListOf<Insn>()
    var context = Context(null)

    init {
        val commentRegex = ";[^\"]*$".toRegex()
        val tokens = Tokenizer(text.replace(commentRegex, ""))
        val labels = mutableSetOf<String>()
        while(!tokens.eof()) {
            if(tokens.peek().testLine()) {
                tokens.pop()
                continue
            }
            while(tokens.peek().value.endsWith(':')) {
                labels.add(tokens.pop().value.removeSuffix(":"))
            }

            if(tokens.peek().testLine())
                continue

            val condition: String? =
                if(tokens.peek().value.endsWith("?")) {
                    tokens.pop().value.removeSuffix("?")
                } else {
                    null
                }

            val name = tokens.pop()

            val factory = InstructionRegistry.factoryMap.getValue(name.value)
            val insn = factory.parse(this, tokens)

            insn.sourceMap = SourceMap(file, name.line)
            insn.context = context
            context.labels.addAll(labels.map { Label(it, insn) })
            labels.clear()

            instructions.add(insn)
            if(!tokens.eof())
                tokens.pop().expectLine()
        }

        instructions.fold(0) { address, insn ->
            insn.address = address
            address + insn.width
        }
    }

    fun write(): ByteBuffer {
        val lines = text.lines()

        val size = instructions.sumBy { it.width }
        val buffer = ByteBuffer.allocate(size)
        logger.debug("Writing instructions")

        val programSize = instructions.sumBy { it.width } - (instructions.lastOrNull()?.width ?: 0)
        val programDigits = programSize.toString(16).length
        val maxInsnWidth = instructions.maxBy { it.width }?.width ?: 0
        val insnBytesTextWidth = maxInsnWidth * 2 + maxInsnWidth / 4

        instructions.forEach { insn ->
            val insnArray = buildBytes { insn.push(it, this)  }

            val insnBytes = insnArray
                .map { it.toUByte().toString(16).padStart(2, '0') }
            val opcodeText = insnBytes.subList(0, 2).joinToString("")
            val insnPayload = insnBytes.subList(2, insnBytes.size)
                .chunked(4)
                .map { it.joinToString("") }
                .joinToString(" ")
            val sourceLine = insn.sourceMap?.let { lines.getOrNull(it.line) } ?: "???"
            logger.debug(
                "0x${insn.address.toString(16).padStart(programDigits, '0')}: $opcodeText " +
                    insnPayload.padEnd(insnBytesTextWidth, ' ') +
                    " > $sourceLine"
            )

            if(insnArray.size != insn.width)
                error("Expected size ${insn.width} is not actual size ${insnArray.size}")
            buffer.put(insnArray)
        }
        return buffer
    }
}

private val logger = LoggerFactory.getLogger(Parser::class.java)