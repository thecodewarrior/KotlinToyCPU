package dev.thecodewarrior.kotlincpu.computer.cpu.instructions

import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUByte
import java.nio.ByteBuffer

class ArithmeticInsn {
    class UnaryInPlaceInsn(name: String, opcode: UShort, val operation: (ULong) -> ULong): Insn(name, opcode, 1u) {
        override fun run(cpu: CPU, buffer: ByteBuffer) {
            val register = buffer.getUByte()
            cpu.registers.values[register.toInt()] = operation(cpu.registers.values[register.toInt()])
        }
    }

    companion object {
        val instructions = listOf(
            UnaryInPlaceInsn("INC_B",  0x20u) { (it.i8  + 1 ).u64 },
            UnaryInPlaceInsn("INC_UB", 0x21u) { (it.u8  + 1u).u64 },
            UnaryInPlaceInsn("INC_S",  0x22u) { (it.i16 + 1 ).u64 },
            UnaryInPlaceInsn("INC_US", 0x23u) { (it.u16 + 1u).u64 },
            UnaryInPlaceInsn("INC_I",  0x24u) { (it.i32 + 1 ).u64 },
            UnaryInPlaceInsn("INC_UI", 0x25u) { (it.u32 + 1u).u64 },
            UnaryInPlaceInsn("INC_L",  0x26u) { (it.i64 + 1 ).u64 },
            UnaryInPlaceInsn("INC_UL", 0x27u) { (it.u64 + 1u).u64 },

            UnaryInPlaceInsn("DEC_B",  0x30u) { (it.i8  - 1 ).u64 },
            UnaryInPlaceInsn("DEC_UB", 0x31u) { (it.u8  - 1u).u64 },
            UnaryInPlaceInsn("DEC_S",  0x32u) { (it.i16 - 1 ).u64 },
            UnaryInPlaceInsn("DEC_US", 0x33u) { (it.u16 - 1u).u64 },
            UnaryInPlaceInsn("DEC_I",  0x34u) { (it.i32 - 1 ).u64 },
            UnaryInPlaceInsn("DEC_UI", 0x35u) { (it.u32 - 1u).u64 },
            UnaryInPlaceInsn("DEC_L",  0x36u) { (it.i64 - 1 ).u64 },
            UnaryInPlaceInsn("DEC_UL", 0x37u) { (it.u64 - 1u).u64 }
        )
    }
}