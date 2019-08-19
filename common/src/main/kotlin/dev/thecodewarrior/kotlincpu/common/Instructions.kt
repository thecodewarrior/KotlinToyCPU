package dev.thecodewarrior.kotlincpu.common

import dev.thecodewarrior.kotlincpu.common.Arguments.asm_const
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

    val mov_imm = +Insn("mov_imm", opcodes.create(), u32("value"), reg("dest"))
    val pseudo_mov_label = Insn(mov_imm, label("value"), reg("dest"))
    val mov_r = +Insn("mov_r", opcodes.create(), reg("src"), reg("dest"))

    val ldr_imm = +Insn("ldr_imm", opcodes.create(), reg("dest"), asm_const("["), u32("address"), asm_const("]"))
    val ldr_imm_off_r = +Insn("ldr_imm_off_r", opcodes.create(), reg("dest"), asm_const("["), u32("address"), reg("offset"), asm_const("]"))
    val pseudo_ldr_label = Insn(ldr_imm, reg("dest"), asm_const("["), label("address"), asm_const("]"))
    val pseudo_ldr_label_off_r = Insn(ldr_imm_off_r, reg("dest"), asm_const("["), label("address"), reg("offset"), asm_const("]"))

    val ldr_r = +Insn("ldr_r", opcodes.create(), reg("dest"), asm_const("["), reg("address"), asm_const("]"))
    val ldr_r_off_imm = +Insn("ldr_r_off_imm", opcodes.create(), reg("dest"), asm_const("["), reg("address"), u32("offset"), asm_const("]"))
    val ldr_r_off_r = +Insn("ldr_r_off_r", opcodes.create(), reg("dest"), asm_const("["), reg("address"), reg("offset"), asm_const("]"))

    val str_imm = +Insn("str_imm", opcodes.create(), reg("src"), asm_const("["), u32("address"), asm_const("]"))
    val str_imm_off_r = +Insn("str_imm_off_r", opcodes.create(), reg("src"), asm_const("["), u32("address"), reg("offset"), asm_const("]"))
    val pseudo_str_label = Insn(str_imm, reg("src"), asm_const("["), label("address"), asm_const("]"))
    val pseudo_str_label_off_r = Insn(str_imm_off_r, reg("src"), asm_const("["), label("address"), reg("offset"), asm_const("]"))

    val str_r = +Insn("str_r", opcodes.create(), reg("src"), asm_const("["), reg("address"), asm_const("]"))
    val str_r_off_imm = +Insn("str_r_off_imm", opcodes.create(), reg("src"), asm_const("["), reg("address"), u32("offset"), asm_const("]"))
    val str_r_off_r = +Insn("str_r_off_r", opcodes.create(), reg("src"), asm_const("["), reg("address"), reg("offset"), asm_const("]"))

    val cmp_imm = +Insn("cmp_imm", opcodes.create(), reg("left"), u32("right"))
    val cmp_r = +Insn("cmp_r", opcodes.create(), reg("left"), reg("right"))

    val jmp_imm = +Insn("jmp_imm", opcodes.create(), u32("dest"))
    val pseudo_jmp_label = Insn(jmp_imm, label("dest"))
    val jmp_r = +Insn("jmp_r", opcodes.create(), reg("dest"))

    val inc = +Insn("inc", opcodes.create(), reg("reg"))
    val dec = +Insn("dec", opcodes.create(), reg("reg"))

    val add_imm = +Insn("add_imm", opcodes.create(), reg("left"), u32("right"), reg("dest"))
    val add_r = +Insn("add_r", opcodes.create(), reg("left"), reg("right"), reg("dest"))

    val sub_imm = +Insn("sub_imm", opcodes.create(), reg("left"), u32("right"), reg("dest"))
    val sub_r = +Insn("sub_r", opcodes.create(), reg("left"), reg("right"), reg("dest"))

    val mul_imm = +Insn("mul_imm", opcodes.create(), reg("left"), u32("right"), reg("dest"))
    val mul_r = +Insn("mul_r", opcodes.create(), reg("left"), reg("right"), reg("dest"))

    val div_imm = +Insn("div_imm", opcodes.create(), reg("left"), u32("right"), reg("dest"))
    val div_r = +Insn("div_r", opcodes.create(), reg("left"), reg("right"), reg("dest"))
    val sdiv_imm = +Insn("sdiv_imm", opcodes.create(), reg("left"), u32("right"), reg("dest"))
    val sdiv_r = +Insn("sdiv_r", opcodes.create(), reg("left"), reg("right"), reg("dest"))

    val mod_imm = +Insn("mod_imm", opcodes.create(), reg("left"), u32("right"), reg("dest"))
    val mod_r = +Insn("mod_r", opcodes.create(), reg("left"), reg("right"), reg("dest"))
    val smod_imm = +Insn("smod_imm", opcodes.create(), reg("left"), u32("right"), reg("dest"))
    val smod_r = +Insn("smod_r", opcodes.create(), reg("left"), reg("right"), reg("dest"))

    val shl_imm = +Insn("shl_imm", opcodes.create(), reg("value"), u32("shift"), reg("dest"))
    val shl_r = +Insn("shl_r", opcodes.create(), reg("value"), reg("shift"), reg("dest"))

    val shr_imm = +Insn("shr_imm", opcodes.create(), reg("value"), u32("shift"), reg("dest"))
    val shr_r = +Insn("shr_r", opcodes.create(), reg("value"), reg("shift"), reg("dest"))
    val sshr_imm = +Insn("sshr_imm", opcodes.create(), reg("value"), u32("shift"), reg("dest"))
    val sshr_r = +Insn("sshr_r", opcodes.create(), reg("value"), reg("shift"), reg("dest"))

    val and_imm = +Insn("and_imm", opcodes.create(), reg("left"), u32("right"), reg("dest"))
    val and_r = +Insn("and_r", opcodes.create(), reg("left"), reg("right"), reg("dest"))
    val or_imm = +Insn("or_imm", opcodes.create(), reg("left"), u32("right"), reg("dest"))
    val or_r = +Insn("or_r", opcodes.create(), reg("left"), reg("right"), reg("dest"))
    val xor_imm = +Insn("xor_imm", opcodes.create(), reg("left"), u32("right"), reg("dest"))
    val xor_r = +Insn("xor_r", opcodes.create(), reg("left"), reg("right"), reg("dest"))

    val not = +Insn("not", opcodes.create(), reg("value"), reg("dest"))

    val shl_imm = +Insn("shl_imm", opcodes.create(), reg("value"), u32("shift"), reg("dst"))
    val shl_r = +Insn("shl_r", opcodes.create(), reg("value"), reg("shift"), reg("dst"))

    val shr_imm = +Insn("shr_imm", opcodes.create(), reg("value"), u32("shift"), reg("dst"))
    val shr_r = +Insn("shr_r", opcodes.create(), reg("value"), reg("shift"), reg("dst"))
    val sshr_imm = +Insn("sshr_imm", opcodes.create(), reg("value"), u32("shift"), reg("dst"))
    val sshr_r = +Insn("sshr_r", opcodes.create(), reg("value"), reg("shift"), reg("dst"))

    val and_imm = +Insn("and_imm", opcodes.create(), reg("left"), u32("right"), reg("dst"))
    val and_r = +Insn("and_r", opcodes.create(), reg("left"), reg("right"), reg("dst"))
    val or_imm = +Insn("or_imm", opcodes.create(), reg("left"), u32("right"), reg("dst"))
    val or_r = +Insn("or_r", opcodes.create(), reg("left"), reg("right"), reg("dst"))
    val xor_imm = +Insn("xor_imm", opcodes.create(), reg("left"), u32("right"), reg("dst"))
    val xor_r = +Insn("xor_r", opcodes.create(), reg("left"), reg("right"), reg("dst"))

    val not = +Insn("not", opcodes.create(), reg("value"), reg("dst"))

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

