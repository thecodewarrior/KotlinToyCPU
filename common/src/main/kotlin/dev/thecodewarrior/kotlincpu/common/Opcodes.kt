package dev.thecodewarrior.kotlincpu.common

import dev.thecodewarrior.kotlincpu.common.DataType.*

object Opcodes {
    private val opcodes = OpcodeTracker()

    val instructions = mutableListOf<Opcode>()

    val nop = +Opcode("nop", opcodes.create(), false)

    val mov_imm = +Opcode("mov_imm", opcodes.create(), false, reg("dst"), u32("value"))
    val mov_r = +Opcode("mov_r", opcodes.create(), false, reg("dst"), reg("src"))

    val ldr_imm = +Opcode("ldr_imm", opcodes.create(), false, reg("dst"), u32("address"))
    val ldr_imm_off_r = +Opcode("ldr_imm_off_r", opcodes.create(), false, reg("dst"), u32("address"), reg("offset"))
    val ldr_r = +Opcode("ldr_r", opcodes.create(), false, reg("dst"), reg("address"))
    val ldr_r_off_imm = +Opcode("ldr_r_off_imm", opcodes.create(), false, reg("dst"), reg("address"), u32("offset"))
    val ldr_r_off_r = +Opcode("ldr_r_off_r", opcodes.create(), false, reg("dst"), reg("address"), reg("offset"))

    val str_imm = +Opcode("str_imm", opcodes.create(), false, reg("src"), u32("address"))
    val str_imm_off_r = +Opcode("str_imm_off_r", opcodes.create(), false, reg("src"), u32("address"), reg("offset"))
    val str_r = +Opcode("str_r", opcodes.create(), false, reg("src"), reg("address"))
    val str_r_off_imm = +Opcode("str_r_off_imm", opcodes.create(), false, reg("src"), reg("address"), u32("offset"))
    val str_r_off_r = +Opcode("str_r_off_r", opcodes.create(), false, reg("src"), reg("address"), reg("offset"))

    val add_imm = +Opcode("add_imm", opcodes.create(), false, reg("dst"), reg("left"), u32("right"))
    val add_r = +Opcode("add_r", opcodes.create(), false, reg("dst"), reg("left"), reg("right"))

    private operator fun Opcode.unaryPlus(): Opcode {
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

