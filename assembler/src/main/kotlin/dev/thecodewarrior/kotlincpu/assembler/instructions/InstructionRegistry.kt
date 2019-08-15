package dev.thecodewarrior.kotlincpu.assembler.instructions

import dev.thecodewarrior.kotlincpu.assembler.Parser
import dev.thecodewarrior.kotlincpu.assembler.tokenizer.Tokenizer
import dev.thecodewarrior.kotlincpu.assembler.util.putUShort
import dev.thecodewarrior.kotlincpu.common.DataType
import dev.thecodewarrior.kotlincpu.common.Insn
import dev.thecodewarrior.kotlincpu.common.Instructions
import java.nio.ByteBuffer

object InstructionRegistry {
    val factories: MutableList<InsnFactory> = mutableListOf()

    val nop = +factory("nop") { _, _ ->
        insn(Instructions.nop) { _, _ -> }
    }

    val mov = +factory("mov", Instructions.mov_imm, Instructions.mov_r)
    val cmp = +factory("cmp", Instructions.cmp_imm, Instructions.cmp_r)
    val jmp = +factory("jmp", Instructions.jmp_imm, Instructions.jmp_r, Instructions.pseudo_jmp_label)

    val add = +factory("add", Instructions.add_imm, Instructions.add_r)
    val sub = +factory("sub", Instructions.sub_imm, Instructions.sub_r)
    val mul = +factory("mul", Instructions.mul_imm, Instructions.mul_r)
    val div = +factory("div", Instructions.div_imm, Instructions.div_r)
    val sdiv = +factory("sdiv", Instructions.sdiv_imm, Instructions.sdiv_r)
    val mod = +factory("mod", Instructions.mod_imm, Instructions.mod_r)
    val smod = +factory("smod", Instructions.smod_imm, Instructions.smod_r)

    private operator fun InsnFactory.unaryPlus(): InsnFactory {
        factories.add(this)
        return this
    }

    private inline fun factory(name: String, crossinline callback: (parser: Parser, tokenizer: Tokenizer) -> Instruction): InsnFactory {
        return object : InsnFactory(name) {
            override fun parse(parser: Parser, tokenizer: Tokenizer): Instruction = callback(parser, tokenizer)
        }
    }

    private inline fun insn(opcode: Insn, crossinline payload: (buffer: ByteBuffer, parser: Parser) -> Unit): Instruction {
        return object : Instruction(opcode) {
            override fun push(buffer: ByteBuffer, parser: Parser) {
                payload(buffer, parser)
            }
        }
    }

    private fun factory(name: String, vararg _opcodes: Insn): InsnFactory {
        val opcodes = _opcodes.toList()
        return object : InsnFactory(name) {
            override fun parse(parser: Parser, tokenizer: Tokenizer): Instruction {
                val start = tokenizer.index

                for(opcode in opcodes) {
                    tokenizer.index = start
                    val values = opcode.payload.mapNotNull {
                        if(tokenizer.eof()) null else it.parse(tokenizer.pop().value)
                    }
                    if(values.size == opcode.payload.size) { // all the elements passed the non-null test
                        return object : Instruction(opcode) {
                            override fun push(buffer: ByteBuffer, parser: Parser) {
                                opcode.payload.zip(values) { argument, _value ->
                                    val value = if(argument is DataType.label) {
                                        val labelInsn = context.findLabel(_value as String).instruction!!
                                        labelInsn.address
                                    } else {
                                        _value
                                    }
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