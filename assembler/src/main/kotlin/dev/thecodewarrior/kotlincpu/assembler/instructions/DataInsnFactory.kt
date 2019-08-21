package dev.thecodewarrior.kotlincpu.assembler.instructions

import dev.thecodewarrior.kotlincpu.assembler.Assembler
import dev.thecodewarrior.kotlincpu.assembler.tokenizer.Tokenizer
import dev.thecodewarrior.kotlincpu.assembler.util.buildBytes
import dev.thecodewarrior.kotlincpu.assembler.util.multiReplace
import dev.thecodewarrior.kotlincpu.common.DataType
import dev.thecodewarrior.kotlincpu.common.Instructions
import java.nio.ByteBuffer

object DataInsnFactory: InsnFactory("%data") {
    override fun parse(assembler: Assembler, tokenizer: Tokenizer): Instruction {
        val type = tokenizer.pop().value
        val value: ByteArray

        when(type) {
            ".ascii" -> {
                val constant = tokenizer.pop().value.removePrefix("\"").removeSuffix("\"")
                value = processEscapes(constant).toByteArray()
            }
            ".asciiz" -> {
                val constant = tokenizer.pop().value.removePrefix("\"").removeSuffix("\"")
                value = processEscapes(constant).toByteArray() + byteArrayOf(0)
            }
            in dataTypes.keys -> {
                val dataType = dataTypes.getValue(type)
                val values = mutableListOf<Any>()
                while(!tokenizer.peek().testLine()) {
                    val token = tokenizer.pop()
                    val parsed = dataType.parse(token.value) ?: error("Invalid token `${token.value}` for type $type")
                    values.add(parsed)
                }
                value = buildBytes { buffer ->
                    values.forEach {
                        dataType.put(buffer, it)
                    }
                }
            }
            else -> error("Unknown type $type")
        }

        return DataInstruction(value)
    }

    @Suppress("UNCHECKED_CAST")
    private val dataTypes: Map<String, DataType<Any>> = listOf(
        DataType.u8, DataType.u16, DataType.u32, DataType.u64,
        DataType.i8, DataType.i16, DataType.i32, DataType.i64,
        DataType.f32, DataType.f64
    ).associateBy { ".$it" } as Map<String, DataType<Any>>

    fun processEscapes(input: String): String {
        return input.multiReplace(
            "\\\\u([0-9a-fA-F]{2,6})" to { match ->
                match.groupValues[1].toInt(16).toChar().toString()
            },
            "\\\\(.)" to { match ->
                when(match.groupValues[1]) {
                    "0" -> "\u0000"
                    "t" -> "\t"
                    "b" -> "\b"
                    "n" -> "\n"
                    "r" -> "\r"
                    "\"" -> "\""
                    else -> match.groupValues[1]
                }
            }
        )
    }

    class DataInstruction(val data: ByteArray): Instruction(Instructions.pseudo_data) {
        override val width: Int
            get() = data.size

        override fun push(buffer: ByteBuffer, assembler: Assembler) {
            buffer.put(data)
        }
    }
}