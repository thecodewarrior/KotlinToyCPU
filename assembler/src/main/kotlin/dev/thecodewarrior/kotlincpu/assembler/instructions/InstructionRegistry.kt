package dev.thecodewarrior.kotlincpu.assembler.instructions

import dev.thecodewarrior.kotlincpu.assembler.Parser
import dev.thecodewarrior.kotlincpu.assembler.tokenizer.Tokenizer
import dev.thecodewarrior.kotlincpu.common.Argument
import dev.thecodewarrior.kotlincpu.common.DataType
import dev.thecodewarrior.kotlincpu.common.Insn
import dev.thecodewarrior.kotlincpu.common.Instructions
import dev.thecodewarrior.kotlincpu.common.Register
import java.nio.ByteBuffer

internal object InstructionRegistry {
    val factories: MutableList<InsnFactory> = mutableListOf()

    val nop = +factory("nop", Instructions.nop)

    val halt = +factory("halt", Instructions.halt)

    val mov = +factory("mov", Instructions.mov_imm, Instructions.mov_r, Instructions.pseudo_mov_label)

    val ldr = +factory("ldr",
        Instructions.ldr_imm, Instructions.ldr_imm_off_r,
        Instructions.ldr_r, Instructions.ldr_r_off_imm, Instructions.ldr_r_off_r,
        Instructions.pseudo_ldr_label, Instructions.pseudo_ldr_label_off_r
    )
    val str = +factory("str",
        Instructions.str_imm, Instructions.str_imm_off_r,
        Instructions.str_r, Instructions.str_r_off_imm, Instructions.str_r_off_r,
        Instructions.pseudo_str_label, Instructions.pseudo_str_label_off_r
    )

    val cmp = +factory("cmp", Instructions.cmp_imm, Instructions.cmp_r)
    val jmp = +factory("jmp", Instructions.jmp_imm, Instructions.jmp_r, Instructions.pseudo_jmp_label)
    val call = +factory("call", Instructions.call_imm, Instructions.call_r, Instructions.pseudo_call_label)

    val inc = +factory("inc", Instructions.inc)
    val dec = +factory("dec", Instructions.dec)
    val add = +factory("add", Instructions.add_imm, Instructions.add_r)
    val sub = +factory("sub", Instructions.sub_imm, Instructions.sub_r)
    val mul = +factory("mul", Instructions.mul_imm, Instructions.mul_r)
    val div = +factory("div", Instructions.div_imm, Instructions.div_r)
    val sdiv = +factory("sdiv", Instructions.sdiv_imm, Instructions.sdiv_r)
    val mod = +factory("mod", Instructions.mod_imm, Instructions.mod_r)
    val smod = +factory("smod", Instructions.smod_imm, Instructions.smod_r)

    val shl = +factory("shl", Instructions.shl_imm, Instructions.shl_r)
    val shr = +factory("shr", Instructions.shr_imm, Instructions.shr_r)
    val sshr = +factory("sshr", Instructions.sshr_imm, Instructions.sshr_r)

    val and = +factory("and", Instructions.and_imm, Instructions.and_r)
    val or = +factory("or", Instructions.or_imm, Instructions.or_r)
    val xor = +factory("xor", Instructions.xor_imm, Instructions.xor_r)
    val not = +factory("not", Instructions.not)


    val push = +factory("push", Instructions.push5, Instructions.push4, Instructions.push3, Instructions.push2, Instructions.push1)
    val pop = +factory("pop", Instructions.pop5, Instructions.pop4, Instructions.pop3, Instructions.pop2, Instructions.pop1)

    val peek = +factory("peek", Instructions.peek)

    val mkframe = +factory("mkframe", Instructions.mkframe_keep_imm, Instructions.mkframe_keep_r, Instructions.mkframe)
    val rmframe = +factory("rmframe", Instructions.rmframe_keep_imm, Instructions.rmframe_keep_r, Instructions.rmframe)

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
                val line = tokenizer.peek().line

                for(opcode in opcodes) {
                    tokenizer.index = start
                    val values = opcode.payload.mapNotNull {
                        if(tokenizer.eof()) {
                            null
                        } else {
                            var token = tokenizer.pop()
                            val variable = parser.context.variables[token.value]
                            if(variable != null)
                                token = variable[0]
                            it.type.parse(token.value)
                        }
                    }
                    if(values.size == opcode.payload.size) { // all the elements passed the non-null test
                        return object : Instruction(opcode) {
                            override fun push(buffer: ByteBuffer, parser: Parser) {
                                opcode.payload.zip(values) { argument, value ->
                                    val resolved = when {
                                        argument.type == DataType.label -> {
                                            val labelInsn = context.findLabel(value as String).instruction!!
                                            labelInsn.address
                                        }
                                        else -> value
                                    }
                                    @Suppress("UNCHECKED_CAST")
                                    (argument as Argument<Any>).type.put(buffer, resolved)
                                }
                            }
                        }
                    }
                }
                error("invalid instruction $name on line ${line + 1}")
            }
        }
    }

    val factoryMap: Map<String, InsnFactory> = factories.associateBy { it.name }

}