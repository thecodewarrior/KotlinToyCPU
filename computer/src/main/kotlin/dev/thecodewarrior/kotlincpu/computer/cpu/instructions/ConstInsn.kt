package dev.thecodewarrior.kotlincpu.computer.cpu.instructions

import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUByte
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUInt
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getULong
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUShort
import java.nio.ByteBuffer

class ConstInsn(name: String, opcode: UShort, width: UInt, val getValue: ByteBuffer.() -> ULong): Insn(name, opcode, width + 1u) {

    override fun run(cpu: CPU, buffer: ByteBuffer) {
        val register = buffer.getUByte()
        cpu.registers.values[register.toInt()] = buffer.getValue()
    }

    companion object {
        val instructions = listOf(
            ConstInsn("CONST_B",  0x10u, 1u) { getUByte().u64 },
            ConstInsn("CONST_UB", 0x11u, 1u) { getUByte().u64 },

            ConstInsn("CONST_S",  0x12u, 2u) { getUShort().u64 },
            ConstInsn("CONST_US", 0x13u, 2u) { getUShort().u64 },

            ConstInsn("CONST_I",  0x14u, 4u) { getUInt().u64 },
            ConstInsn("CONST_UI", 0x15u, 4u) { getUInt().u64 },

            ConstInsn("CONST_L",  0x16u, 8u) { getULong() },
            ConstInsn("CONST_UL", 0x17u, 8u) { getULong() },

            ConstInsn("CONST_F",  0x18u, 4u) { getFloat().u64 },
            ConstInsn("CONST_D",  0x19u, 8u) { getDouble().u64 }
        )
    }
}
