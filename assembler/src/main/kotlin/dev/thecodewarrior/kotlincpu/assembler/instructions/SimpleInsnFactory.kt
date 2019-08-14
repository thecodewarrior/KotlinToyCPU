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

open class SimpleInsnFactory(name: String, val opcode: UShort, vararg arguments: Argument): InsnFactory(name) {
    var qualifier: UShort? = null

    constructor(name: String, opcode: UShort, qualifier: UShort, vararg arguments: Argument): this(name, opcode, *arguments) {
        this.qualifier = qualifier
    }

    val arguments = arguments.toList()
    open val width: Int
        get() = 2 + (if(qualifier == null) 0 else 2) + arguments.sumBy { it.width }

    override fun parse(parser: Parser, tokenizer: Tokenizer): Insn {
        return SimpleInsn(arguments.map { it.parse(parser, tokenizer) }.toMutableList())
    }

    override fun toString(): String {
        val opcodeText = opcode.toString(16).padStart(4, '0')
        val qualifierText = qualifier?.let { " " + it.toString(16).padStart(4, '0') } ?: ""
        return "$name ${arguments.joinToString(" ")} > $opcodeText$qualifierText"
    }

    inner class SimpleInsn(val argumentValues: MutableList<Any>): Insn() {
        override val width: Int = this@SimpleInsnFactory.width

        override fun push(buffer: ByteBuffer, parser: Parser) {
            buffer.putUShort(opcode)
            qualifier?.also { buffer.putUShort(it) }
            arguments.zip(argumentValues).forEach { (argument, value) ->
                argument.push(buffer, parser, this, value)
            }
        }
    }
}

sealed class Argument(val name: String, val width: kotlin.Int) {
    abstract fun parse(parser: Parser, tokenizer: Tokenizer): Any
    abstract fun push(buffer: ByteBuffer, parser: Parser, insn: Insn, value: Any)

    class Boolean(name: String): Argument(name, 1) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.pop()
            return token.value.toBoolean()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, insn: Insn, value: Any) {
            value as kotlin.Boolean
            buffer.put((if(value) 1 else 0).toByte())
        }

        override fun toString(): String = "<$name: boolean>"
    }
    class Byte(name: String): Argument(name, 1) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.pop()
            return token.value.toByteDetectRadix()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, insn: Insn, value: Any) {
            value as kotlin.Byte
            buffer.put(value)
        }

        override fun toString(): String = "<$name: byte>"
    }
    class UByte(name: String): Argument(name, 1) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.pop()
            return token.value.toUByteDetectRadix()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, insn: Insn, value: Any) {
            value as kotlin.UByte
            buffer.putUByte(value)
        }

        override fun toString(): String = "<$name: ubyte>"
    }
    class Short(name: String): Argument(name, 2) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.pop()
            return token.value.toShortDetectRadix()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, insn: Insn, value: Any) {
            value as kotlin.Short
            buffer.putShort(value)
        }

        override fun toString(): String = "<$name: short>"
    }
    class UShort(name: String): Argument(name, 2) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.pop()
            return token.value.toUShortDetectRadix()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, insn: Insn, value: Any) {
            value as kotlin.UShort
            buffer.putUShort(value)
        }

        override fun toString(): String = "<$name: ushort>"
    }
    class Int(name: String): Argument(name, 4) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.pop()
            return token.value.toIntDetectRadix()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, insn: Insn, value: Any) {
            value as kotlin.Int
            buffer.putInt(value)
        }

        override fun toString(): String = "<$name: int>"
    }
    class UInt(name: String): Argument(name, 4) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.pop()
            return token.value.toUIntDetectRadix()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, insn: Insn, value: Any) {
            value as kotlin.UInt
            buffer.putUInt(value)
        }

        override fun toString(): String = "<$name: uint>"
    }
    class Long(name: String): Argument(name, 8) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.pop()
            return token.value.toLongDetectRadix()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, insn: Insn, value: Any) {
            value as kotlin.Long
            buffer.putLong(value)
        }

        override fun toString(): String = "<$name: long>"
    }
    class ULong(name: String): Argument(name, 8) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.pop()
            return token.value.toULongDetectRadix()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, insn: Insn, value: Any) {
            value as kotlin.ULong
            buffer.putULong(value)
        }

        override fun toString(): String = "<$name: ulong>"
    }
    class Float(name: String): Argument(name, 4) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.pop()
            return token.value.toFloat()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, insn: Insn, value: Any) {
            value as kotlin.Float
            buffer.putFloat(value)
        }

        override fun toString(): String = "<$name: float>"
    }
    class Double(name: String): Argument(name, 8) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.pop()
            return token.value.toDouble()
        }

        override fun push(buffer: ByteBuffer, parser: Parser, insn: Insn, value: Any) {
            value as kotlin.Double
            buffer.putDouble(value)
        }

        override fun toString(): String = "<$name: double>"
    }

    class StringData(name: String): Argument(name, 8) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun push(buffer: ByteBuffer, parser: Parser, insn: Insn, value: Any) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun toString(): String = "<$name: string>"
    }

    class Register(name: String): Argument(name, 1) {
        val specials = listOf(
            "CTR"
        )

        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.pop()
            return token.value
        }

        override fun push(buffer: ByteBuffer, parser: Parser, insn: Insn, value: Any) {
            value as kotlin.String
            val specialIndex = specials.indexOf(value)

            val register: kotlin.UByte = when {
                specialIndex >= 0 -> (Constants.registerCount - specials.size + specialIndex).toUByte()
                value.startsWith("R") -> value.removePrefix("R").toUByte()
                else -> parser.registers[value] ?: error("Unknown register $value")
            }
            buffer.putUByte(register)
        }

        override fun toString(): String = "<$name: register>"
    }

    class Label(name: String): Argument(name, 8) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            val token = tokenizer.pop()
            if(!token.value.startsWith("@"))
                throw RuntimeException("Labels must start with @")
            return token.value.removePrefix("@")
        }

        override fun push(buffer: ByteBuffer, parser: Parser, insn: Insn, value: Any) {
            value as String
            val label = insn.context.findLabel(value)
            buffer.putULong(label.instruction!!.address.toULong())
        }

        override fun toString(): String = "<$name: label>"
    }
    class Address(name: String): Argument(name, 8) {
        override fun parse(parser: Parser, tokenizer: Tokenizer): Any {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun push(buffer: ByteBuffer, parser: Parser, insn: Insn, value: Any) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun toString(): String = "<$name: address>"
    }
}

