package dev.thecodewarrior.kotlincpu.assembler.instructions

import dev.thecodewarrior.kotlincpu.assembler.Constants
import dev.thecodewarrior.kotlincpu.assembler.Parser
import dev.thecodewarrior.kotlincpu.assembler.tokenizer.Tokenizer
import dev.thecodewarrior.kotlincpu.assembler.util.putUByte
import dev.thecodewarrior.kotlincpu.assembler.util.putUInt
import dev.thecodewarrior.kotlincpu.assembler.util.putULong
import dev.thecodewarrior.kotlincpu.assembler.util.putUShort
import dev.thecodewarrior.kotlincpu.assembler.util.toByteDetectRadix
import dev.thecodewarrior.kotlincpu.assembler.util.toIntDetectRadix
import dev.thecodewarrior.kotlincpu.assembler.util.toLongDetectRadix
import dev.thecodewarrior.kotlincpu.assembler.util.toShortDetectRadix
import dev.thecodewarrior.kotlincpu.assembler.util.toUByteDetectRadix
import dev.thecodewarrior.kotlincpu.assembler.util.toUIntDetectRadix
import dev.thecodewarrior.kotlincpu.assembler.util.toULongDetectRadix
import dev.thecodewarrior.kotlincpu.assembler.util.toUShortDetectRadix
import java.nio.ByteBuffer

class SimpleInsnFactory(name: String, val opcode: UShort, vararg arguments: Argument): InsnFactory(name) {
    val arguments = arguments.toList()
    val width: Int = 2 + arguments.sumBy { it.width }

    override fun parse(parser: Parser, tokenizer: Tokenizer): Insn {
        return SimpleInsn(arguments.map { it.parse(parser, tokenizer) })
    }

    inner class SimpleInsn(val argumentValues: List<Any>): Insn() {
        override val width: Int = this@SimpleInsnFactory.width

        override fun push(buffer: ByteBuffer, parser: Parser) {
            buffer.putUShort(opcode)
            arguments.zip(argumentValues).forEach { (argument, value) ->
                argument.push(buffer, parser, value)
            }
        }
    }
}

sealed class Argument(val name: String, val width: kotlin.Int) {
    abstract fun parse(parser: Parser, tokenizer: Tokenizer): Any
    abstract fun push(buffer: ByteBuffer, parser: Parser, value: Any)

    class Boolean(name: String): Argument(name, 1) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.next()
            return token.value.toBoolean()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, value: Any) {
            value as kotlin.Boolean
            buffer.put((if(value) 1 else 0).toByte())
        }
    }
    class Byte(name: String): Argument(name, 1) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.next()
            return token.value.toByteDetectRadix()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, value: Any) {
            value as kotlin.Byte
            buffer.put(value)
        }
    }
    class UByte(name: String): Argument(name, 1) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.next()
            return token.value.toUByteDetectRadix()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, value: Any) {
            value as kotlin.UByte
            buffer.putUByte(value)
        }
    }
    class Short(name: String): Argument(name, 2) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.next()
            return token.value.toShortDetectRadix()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, value: Any) {
            value as kotlin.Short
            buffer.putShort(value)
        }
    }
    class UShort(name: String): Argument(name, 2) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.next()
            return token.value.toUShortDetectRadix()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, value: Any) {
            value as kotlin.UShort
            buffer.putUShort(value)
        }
    }
    class Int(name: String): Argument(name, 4) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.next()
            return token.value.toIntDetectRadix()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, value: Any) {
            value as kotlin.Int
            buffer.putInt(value)
        }
    }
    class UInt(name: String): Argument(name, 4) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.next()
            return token.value.toUIntDetectRadix()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, value: Any) {
            value as kotlin.UInt
            buffer.putUInt(value)
        }
    }
    class Long(name: String): Argument(name, 8) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.next()
            return token.value.toLongDetectRadix()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, value: Any) {
            value as kotlin.Long
            buffer.putLong(value)
        }
    }
    class ULong(name: String): Argument(name, 8) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.next()
            return token.value.toULongDetectRadix()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, value: Any) {
            value as kotlin.ULong
            buffer.putULong(value)
        }
    }
    class Float(name: String): Argument(name, 4) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.next()
            return token.value.toFloat()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, value: Any) {
            value as kotlin.Float
            buffer.putFloat(value)
        }
    }
    class Double(name: String): Argument(name, 8) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.next()
            return token.value.toDouble()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, value: Any) {
            value as kotlin.Double
            buffer.putDouble(value)
        }
    }

    class StringData(name: String): Argument(name, 8) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun push(buffer: ByteBuffer, parser: Parser, value: Any) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    class Register(name: String): Argument(name, 1) {
        val specials = listOf(
            "CTR"
        )

        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.next()
            return token.value
        }

        override fun push(buffer: ByteBuffer, parser: Parser, value: Any) {
            value as kotlin.String
            val specialIndex = specials.indexOf(value)

            val register: kotlin.UByte = when {
                specialIndex >= 0 -> (Constants.registerCount - specials.size + specialIndex).toUByte()
                value.startsWith("R") -> value.removePrefix("R").toUByte()
                else -> parser.registers[value]!!
            }
            buffer.putUByte(register)
        }
    }

    class Label(name: String): Argument(name, 8) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.next()
            if(!token.value.startsWith("@"))
                throw RuntimeException("Labels must start with @")
            return token.value.removePrefix("@")
        }

        override fun push(buffer: ByteBuffer, parser: Parser, value: Any) {
            value as String
            val label = parser.labels[value]!!
            buffer.putULong(label.address!!)
        }
    }
    class Address(name: String): Argument(name, 8) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun push(buffer: ByteBuffer, parser: Parser, value: Any) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}

