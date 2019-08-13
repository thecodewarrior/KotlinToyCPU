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
        cpu.registers[register.toInt()] = buffer.getValue()
    }

    companion object {
        val instructions = listOf(
            ConstInsn("CONST.i8",  0x10u, 1u) { getUByte().u64 },
            ConstInsn("CONST.u8", 0x11u, 1u) { getUByte().u64 },

            ConstInsn("CONST.i16",  0x12u, 2u) { getUShort().u64 },
            ConstInsn("CONST.u16", 0x13u, 2u) { getUShort().u64 },

            ConstInsn("CONST.i32",  0x14u, 4u) { getUInt().u64 },
            ConstInsn("CONST.u32", 0x15u, 4u) { getUInt().u64 },

            ConstInsn("CONST.i64",  0x16u, 8u) { getULong() },
            ConstInsn("CONST.u64", 0x17u, 8u) { getULong() },

            ConstInsn("CONST.f32",  0x18u, 4u) { getFloat().u64 },
            ConstInsn("CONST.f64",  0x19u, 8u) { getDouble().u64 }
        )
    }
}
