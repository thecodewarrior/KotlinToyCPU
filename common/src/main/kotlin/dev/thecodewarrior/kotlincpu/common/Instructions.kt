package dev.thecodewarrior.kotlincpu.common

import dev.thecodewarrior.kotlincpu.common.Arguments.reg
import dev.thecodewarrior.kotlincpu.common.Arguments.label
import dev.thecodewarrior.kotlincpu.common.Arguments.u8
import dev.thecodewarrior.kotlincpu.common.Arguments.i8
import dev.thecodewarrior.kotlincpu.common.Arguments.u16
import dev.thecodewarrior.kotlincpu.common.Arguments.i16
import dev.thecodewarrior.kotlincpu.common.Arguments.u32
import dev.thecodewarrior.kotlincpu.common.Arguments.i32
import dev.thecodewarrior.kotlincpu.common.Arguments.u64
import dev.thecodewarrior.kotlincpu.common.Arguments.i64
import dev.thecodewarrior.kotlincpu.common.Arguments.f32
import dev.thecodewarrior.kotlincpu.common.Arguments.f64

object Instructions {
    private val opcodes = OpcodeTracker()

    val instructions = mutableListOf<Insn>()

    val nop = +Insn("nop", opcodes.create())

    val mov_imm = +Insn("mov_imm", opcodes.create(), u32("value"), reg("dst"))
    val mov_r = +Insn("mov_r", opcodes.create(), reg("src"), reg("dst"))

    val ldr_imm = +Insn("ldr_imm", opcodes.create(), u32("address"), reg("dst"))
    val ldr_imm_off_r = +Insn("ldr_imm_off_r", opcodes.create(), u32("address"), reg("offset"), reg("dst"))
    val ldr_r = +Insn("ldr_r", opcodes.create(), reg("address"), reg("dst"))
    val ldr_r_off_imm = +Insn("ldr_r_off_imm", opcodes.create(), reg("address"), u32("offset"), reg("dst"))
    val ldr_r_off_r = +Insn("ldr_r_off_r", opcodes.create(), reg("address"), reg("offset"), reg("dst"))

    val str_imm = +Insn("str_imm", opcodes.create(), reg("src"), u32("address"))
    val str_imm_off_r = +Insn("str_imm_off_r", opcodes.create(), reg("src"), u32("address"), reg("offset"))
    val str_r = +Insn("str_r", opcodes.create(), reg("src"), reg("address"))
    val str_r_off_imm = +Insn("str_r_off_imm", opcodes.create(), reg("src"), reg("address"), u32("offset"))
    val str_r_off_r = +Insn("str_r_off_r", opcodes.create(), reg("src"), reg("address"), reg("offset"))

    val cmp_imm = +Insn("cmp_imm", opcodes.create(), reg("left"), u32("right"))
    val cmp_r = +Insn("cmp_r", opcodes.create(), reg("left"), reg("right"))

    val jmp_imm = +Insn("jmp_imm", opcodes.create(), u32("dst"))
    val pseudo_jmp_label = Insn(jmp_imm.name, jmp_imm.opcode, label("dst"))
    val jmp_r = +Insn("jmp_r", opcodes.create(), reg("dst"))

    val inc = +Insn("inc", opcodes.create(), reg("reg"))
    val dec = +Insn("dec", opcodes.create(), reg("reg"))

    val add_imm = +Insn("add_imm", opcodes.create(), reg("left"), u32("right"), reg("dst"))
    val add_r = +Insn("add_r", opcodes.create(), reg("left"), reg("right"), reg("dst"))

    val sub_imm = +Insn("sub_imm", opcodes.create(), reg("left"), u32("right"), reg("dst"))
    val sub_r = +Insn("sub_r", opcodes.create(), reg("left"), reg("right"), reg("dst"))

    val mul_imm = +Insn("mul_imm", opcodes.create(), reg("left"), u32("right"), reg("dst"))
    val mul_r = +Insn("mul_r", opcodes.create(), reg("left"), reg("right"), reg("dst"))

    val div_imm = +Insn("div_imm", opcodes.create(), reg("left"), u32("right"), reg("dst"))
    val div_r = +Insn("div_r", opcodes.create(), reg("left"), reg("right"), reg("dst"))
    val sdiv_imm = +Insn("sdiv_imm", opcodes.create(), reg("left"), u32("right"), reg("dst"))
    val sdiv_r = +Insn("sdiv_r", opcodes.create(), reg("left"), reg("right"), reg("dst"))

    val mod_imm = +Insn("mod_imm", opcodes.create(), reg("left"), u32("right"), reg("dst"))
    val mod_r = +Insn("mod_r", opcodes.create(), reg("left"), reg("right"), reg("dst"))
    val smod_imm = +Insn("smod_imm", opcodes.create(), reg("left"), u32("right"), reg("dst"))
    val smod_r = +Insn("smod_r", opcodes.create(), reg("left"), reg("right"), reg("dst"))

    private val instructionsMap = instructions.associateBy { it.opcode }
    fun decode(opcode: UShort): Insn? {
        return instructionsMap[opcode and Insn.OPCODE_MASK]
    }

    private operator fun Insn.unaryPlus(): Insn {
        instructions.add(this)
        return this
    }

    private class OpcodeTracker {
        var opcode: UShort = 0u
            private set

        fun create(): UShort {
            return opcode++
        }
    }
}

