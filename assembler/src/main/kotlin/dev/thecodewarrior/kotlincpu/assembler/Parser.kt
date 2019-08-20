package dev.thecodewarrior.kotlincpu.assembler

import dev.thecodewarrior.kotlincpu.assembler.instructions.Instruction
import dev.thecodewarrior.kotlincpu.assembler.instructions.InstructionRegistry
import dev.thecodewarrior.kotlincpu.assembler.instructions.Location
import dev.thecodewarrior.kotlincpu.assembler.tokenizer.Token
import dev.thecodewarrior.kotlincpu.assembler.tokenizer.Tokenizer
import dev.thecodewarrior.kotlincpu.assembler.util.buildBytes
import dev.thecodewarrior.kotlincpu.common.Condition
import dev.thecodewarrior.kotlincpu.common.DataType
import dev.thecodewarrior.kotlincpu.common.Instructions
import dev.thecodewarrior.kotlincpu.common.util.putUInt
import dev.thecodewarrior.kotlincpu.common.util.putUShort
import org.slf4j.LoggerFactory
import java.nio.ByteBuffer

class Parser(val file: String, val text: String) {
    val registers = mutableMapOf<String, UByte>()

    val instructions = mutableListOf<Instruction>()
    var context = Context(null)

    init {
        val commentRegex = ";[^\"]*$".toRegex()
        val tokens = Tokenizer(text.lines().joinToString("\n") { it.replace(commentRegex, " ") })
        val labels = mutableSetOf<String>()
        while(!tokens.eof()) {
            if(tokens.peek().testLine()) {
                tokens.pop()
                continue
            }
            while(tokens.peek().value.endsWith(':')) {
                labels.add(tokens.pop().value.removeSuffix(":"))
            }
            if(tokens.peek().value.startsWith('.')) {
                val variable = tokens.pop().value.removePrefix(".")
                tokens.pop().expect("=")
                val list = mutableListOf<Token>()
                while(!tokens.peek().testLine()) {
                    list.add(tokens.pop())
                }
                context.variables[variable] = list
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

            insn.location = Location(file, name.line)
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

        val size = instructions.sumBy { 2 + it.width }
        val buffer = ByteBuffer.allocate(size)
        logger.debug("Writing instructions")

        val programDigits = (instructions.lastOrNull()?.address ?: 0).toString(16).length
        val maxInsnWidth = instructions.map { it.insn.payloadWidth * 2 + it.insn.payload.filter { it.type !is DataType.asm_const }.size }.max() ?: 0
        val maxNameWidth = instructions.maxBy { it.insn.name.length }?.insn?.name?.length ?: 0

        instructions.forEach { instruction ->
            val insn = instruction.insn
            val opcode = insn.encoded(instruction.condition)

            val insnArray = buildBytes { instruction.push(it, this)  }

            if(insn != Instructions.pseudo_data) {
                val opcodeText = opcode.toString(16).padStart(4, '0')
                val insnBytes = insnArray
                    .map { it.toUByte().toString(16).padStart(2, '0') }

                var i = 0
                val payloadText = insn.payload
                    .filter { it.type !is DataType.asm_const }
                    .map { arg ->
                        val sub = insnBytes.subList(i, i + arg.type.width)
                        i += arg.type.width
                        sub.joinToString("")
                    }
                    .joinToString(" ")

                val sourceLine = lines.getOrNull(instruction.location.line)
                logger.debug(
                    "0x${instruction.address.toString(16).padStart(programDigits, '0')}: " +
                        "${insn.name.padEnd(maxNameWidth, ' ')} $opcodeText " +
                        payloadText.padEnd(maxInsnWidth, ' ') +
                        "> $sourceLine"
                )
            }

            if(insnArray.size != instruction.width)
                error("Expected size ${instruction.width} is not actual size ${insnArray.size}")
            if(insn != Instructions.pseudo_data)
                buffer.putUShort(opcode)
            buffer.put(insnArray)
        }
        return buffer
    }

    fun sourceMap(): ByteArray {
        return buildBytes { buffer ->
            logger.debug("Writing source map")

            val files = instructions.mapTo(mutableSetOf()) { it.location.file }.toList()
            val fileIndexes = files.mapIndexed { i, it -> it to i }.associate { it }

            buffer.putUShort(files.size.toUShort())
            files.forEachIndexed { i, file ->
                val fileArray = file.toByteArray()
                buffer.putUShort(fileArray.size.toUShort())
                buffer.put(fileArray)
            }

            instructions.forEach { instruction ->
                buffer.putInt(instruction.address)
                buffer.putUShort(fileIndexes.getValue(instruction.location.file).toUShort())
                buffer.putUShort(instruction.location.line.toUShort())
            }
        }
    }
}

private val logger = LoggerFactory.getLogger(Parser::class.java)