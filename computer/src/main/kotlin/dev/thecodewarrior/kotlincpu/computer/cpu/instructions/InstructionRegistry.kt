package dev.thecodewarrior.kotlincpu.computer.cpu.instructions

import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUByte
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUInt
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getULong
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUShort

object InstructionRegistry {
    val instructions: List<Insn> = listOf(
        SimpleInsn("NOP", 0x0u, 0u) { _, _ -> },

        SimpleInsn("CONST_B",  0x10u, 2u) { cpu, buffer ->
            val register = buffer.getUByte()
            val value = buffer.getUByte()
            cpu.registers.values[register.toInt()] = value.toLong().toULong()
        },
        SimpleInsn("CONST_BU", 0x11u, 2u) { cpu, buffer ->
            val register = buffer.getUByte()
            val value = buffer.getUByte()
            cpu.registers.values[register.toInt()] = value.toULong()
        },

        SimpleInsn("CONST_S",  0x12u, 3u) { cpu, buffer ->
            val register = buffer.getUByte()
            val value = buffer.getUShort()
            cpu.registers.values[register.toInt()] = value.toLong().toULong()
        },
        SimpleInsn("CONST_SU", 0x13u, 3u) { cpu, buffer ->
            val register = buffer.getUByte()
            val value = buffer.getUShort()
            cpu.registers.values[register.toInt()] = value.toULong()
        },

        SimpleInsn("CONST_I",  0x14u, 5u) { cpu, buffer ->
            val register = buffer.getUByte()
            val value = buffer.getUInt()
            cpu.registers.values[register.toInt()] = value.toLong().toULong()
        },
        SimpleInsn("CONST_IU", 0x15u, 5u) { cpu, buffer ->
            val register = buffer.getUByte()
            val value = buffer.getUInt()
            cpu.registers.values[register.toInt()] = value.toULong()
        },

        SimpleInsn("CONST_L",  0x16u, 9u) { cpu, buffer ->
            val register = buffer.getUByte()
            val value = buffer.getULong()
            cpu.registers.values[register.toInt()] = value
        },
        SimpleInsn("CONST_LU", 0x17u, 9u) { cpu, buffer ->
            val register = buffer.getUByte()
            val value = buffer.getULong()
            cpu.registers.values[register.toInt()] = value
        },

        SimpleInsn("CONST_F",  0x18u, 5u) { cpu, buffer ->
            val register = buffer.getUByte()
            val value = buffer.getFloat()
            cpu.registers.values[register.toInt()] = value.toRawBits().toULong()
        },

        SimpleInsn("CONST_D",  0x19u, 9u) { cpu, buffer ->
            val register = buffer.getUByte()
            val value = buffer.getDouble()
            cpu.registers.values[register.toInt()] = value.toRawBits().toULong()
        },

        SimpleInsn("HALT", 0xFFu, 0u) { cpu, _ -> cpu.computer.running = false }
    )

    val instructionMap: Map<UShort, Insn> = instructions.associateBy { it.opcode }
}