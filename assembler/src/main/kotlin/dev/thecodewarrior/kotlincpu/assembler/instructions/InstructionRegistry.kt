package dev.thecodewarrior.kotlincpu.assembler.instructions

import dev.thecodewarrior.kotlincpu.assembler.Parser
import dev.thecodewarrior.kotlincpu.assembler.tokenizer.Tokenizer
import dev.thecodewarrior.kotlincpu.assembler.util.putUByte
import dev.thecodewarrior.kotlincpu.assembler.util.putUInt
import dev.thecodewarrior.kotlincpu.assembler.util.putUShort
import dev.thecodewarrior.kotlincpu.common.DataType
import dev.thecodewarrior.kotlincpu.common.Opcode
import dev.thecodewarrior.kotlincpu.common.Opcodes
import java.nio.ByteBuffer

object InstructionRegistry {
    val factories: MutableList<InsnFactory> = mutableListOf()

    val nop = +factory("nop") { _, _ ->
        insn(Opcodes.nop) { _, _ -> }
    }

    val mov = +factory("mov", Opcodes.mov_imm, Opcodes.mov_r)

    val add = +factory("add", Opcodes.add_imm, Opcodes.add_r)

    private operator fun InsnFactory.unaryPlus(): InsnFactory {
        factories.add(this)
        return this
    }

    private inline fun factory(name: String, crossinline callback: (parser: Parser, tokenizer: Tokenizer) -> Insn): InsnFactory {
        return object : InsnFactory(name) {
            override fun parse(parser: Parser, tokenizer: Tokenizer): Insn = callback(parser, tokenizer)
        }
    }

    private inline fun insn(opcode: Opcode, crossinline payload: (buffer: ByteBuffer, parser: Parser) -> Unit): Insn {
        return object : Insn(opcode) {
            override fun push(buffer: ByteBuffer, parser: Parser) {
                buffer.putUShort(opcode.opcode)
                payload(buffer, parser)
            }
        }
    }

    private fun factory(name: String, vararg _opcodes: Opcode): InsnFactory {
        val opcodes = _opcodes.toList()
        return object : InsnFactory(name) {
            override fun parse(parser: Parser, tokenizer: Tokenizer): Insn {
                val start = tokenizer.index

                for(opcode in opcodes) {
                    tokenizer.index = start
                    val values = opcode.payload.mapNotNull {
                        if(tokenizer.eof()) null else it.parse(tokenizer.pop().value)
                    }
                    if(values.size == opcode.payload.size) { // all the elements passed the non-null test
                        return object : Insn(opcode) {
                            override fun push(buffer: ByteBuffer, parser: Parser) {
                                buffer.putUShort(opcode.opcode)
                                opcode.payload.zip(values) { argument, value ->
                                    @Suppress("UNCHECKED_CAST")
                                    (argument as DataType<Any>).put(buffer, value)
                                }
                            }
                        }
                    }
                }
                error("invalid instruction $name")
            }
        }
    }

    val factoryMap: Map<String, InsnFactory> = factories.associateBy { it.name }

}