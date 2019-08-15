package dev.thecodewarrior.kotlincpu.assembler

import dev.thecodewarrior.kotlincpu.assembler.instructions.Instruction
import dev.thecodewarrior.kotlincpu.assembler.instructions.InstructionRegistry
import dev.thecodewarrior.kotlincpu.assembler.instructions.SourceMap
import dev.thecodewarrior.kotlincpu.assembler.tokenizer.Tokenizer
import dev.thecodewarrior.kotlincpu.assembler.util.buildBytes
import dev.thecodewarrior.kotlincpu.assembler.util.putUShort
import dev.thecodewarrior.kotlincpu.common.Condition
import org.slf4j.LoggerFactory
import java.nio.ByteBuffer

class Parser(val file: String, val text: String) {
    val registers = mutableMapOf<String, UByte>()

    val instructions = mutableListOf<Instruction>()
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

            if(tokens.peek().testLine()) {
                tokens.pop()
                continue
            }

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
            if(condition != null)
                insn.condition = Condition.valueOf(condition.toUpperCase())
            context.labels.addAll(labels.map { Label(it, insn) })
            labels.clear()

            instructions.add(insn)
            if(!tokens.eof())
                tokens.pop().expectLine()
        }

        instructions.fold(0) { address, instruction ->
            instruction.address = address
            address + 2 + instruction.insn.payloadWidth
        }
    }

    fun write(): ByteBuffer {
        val lines = text.lines()

        val size = instructions.sumBy { 2 + it.insn.payloadWidth }
        val buffer = ByteBuffer.allocate(size)
        logger.debug("Writing instructions")

        val programDigits = (instructions.lastOrNull()?.address ?: 0).toString(16).length
        val maxInsnWidth = instructions.maxBy { it.insn.payloadWidth }?.insn?.payloadWidth ?: 0
        val insnBytesTextWidth = maxInsnWidth * 2 + maxInsnWidth / 2
        val maxNameWidth = instructions.maxBy { it.insn.name.length }?.insn?.name?.length ?: 0

        instructions.forEach { instruction ->
            val insn = instruction.insn
            val opcode = insn.encoded(instruction.condition)

            val insnArray = buildBytes { instruction.push(it, this)  }

            val opcodeText = opcode.toString(16).padStart(4, '0')
            val insnBytes = insnArray
                .map { it.toUByte().toString(16).padStart(2, '0') }

            val insnPayload = insnBytes
                .chunked(2)
                .map { it.joinToString("") }
                .joinToString(" ")

            val sourceLine = instruction.sourceMap?.let { lines.getOrNull(it.line) } ?: "???"
            logger.debug(
                "0x${instruction.address.toString(16).padStart(programDigits, '0')}: " +
                    "${insn.name.padEnd(maxNameWidth, ' ')} $opcodeText " +
                    insnPayload.padEnd(insnBytesTextWidth, ' ') +
                    " > $sourceLine"
            )

            if(insnArray.size != insn.payloadWidth)
                error("Expected size ${insn.payloadWidth} is not actual size ${insnArray.size}")
            buffer.putUShort(opcode)
            buffer.put(insnArray)
        }
        return buffer
    }
}

private val logger = LoggerFactory.getLogger(Parser::class.java)