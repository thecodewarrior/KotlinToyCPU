package dev.thecodewarrior.kotlincpu.common

import dev.thecodewarrior.kotlincpu.common.DataType.*

object Instructions {
    private val opcodes = OpcodeTracker()

    val instructions = mutableListOf<Insn>()

    val nop = +Insn("nop", opcodes.create(), false)

    val mov_imm = +Insn("mov_imm", opcodes.create(), false, reg("dst"), u32("value"))
    val mov_r = +Insn("mov_r", opcodes.create(), false, reg("dst"), reg("src"))

    val ldr_imm = +Insn("ldr_imm", opcodes.create(), false, reg("dst"), u32("address"))
    val ldr_imm_off_r = +Insn("ldr_imm_off_r", opcodes.create(), false, reg("dst"), u32("address"), reg("offset"))
    val ldr_r = +Insn("ldr_r", opcodes.create(), false, reg("dst"), reg("address"))
    val ldr_r_off_imm = +Insn("ldr_r_off_imm", opcodes.create(), false, reg("dst"), reg("address"), u32("offset"))
    val ldr_r_off_r = +Insn("ldr_r_off_r", opcodes.create(), false, reg("dst"), reg("address"), reg("offset"))

    val str_imm = +Insn("str_imm", opcodes.create(), false, reg("src"), u32("address"))
    val str_imm_off_r = +Insn("str_imm_off_r", opcodes.create(), false, reg("src"), u32("address"), reg("offset"))
    val str_r = +Insn("str_r", opcodes.create(), false, reg("src"), reg("address"))
    val str_r_off_imm = +Insn("str_r_off_imm", opcodes.create(), false, reg("src"), reg("address"), u32("offset"))
    val str_r_off_r = +Insn("str_r_off_r", opcodes.create(), false, reg("src"), reg("address"), reg("offset"))

    val cmp_imm = +Insn("cmp_imm", opcodes.create(), false, reg("left"), u32("right"))
    val cmp_r = +Insn("cmp_r", opcodes.create(), false, reg("left"), reg("right"))

    val jmp_imm = +Insn("jmp_imm", opcodes.create(), false, u32("destination"))
    val pseudo_jmp_label = Insn(jmp_imm.name, jmp_imm.opcode, false, label("destination"))
    val jmp_r = +Insn("jmp_r", opcodes.create(), false, reg("destination"))

    val add_imm = +Insn("add_imm", opcodes.create(), false, reg("dst"), reg("left"), u32("right"))
    val add_r = +Insn("add_r", opcodes.create(), false, reg("dst"), reg("left"), reg("right"))


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

