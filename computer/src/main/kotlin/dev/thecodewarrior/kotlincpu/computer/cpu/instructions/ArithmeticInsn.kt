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

    class BinaryInsn(name: String, opcode: UShort, val operation: (ULong, ULong) -> ULong): Insn(name, opcode, 3u) {
        override fun run(cpu: CPU, buffer: ByteBuffer) {
            val left = buffer.getUByte()
            val right = buffer.getUByte()
            val out = buffer.getUByte()
            cpu.registers.values[out.toInt()] = operation(
                cpu.registers.values[left.toInt()], cpu.registers.values[right.toInt()]
            )
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
            UnaryInPlaceInsn("DEC_UL", 0x37u) { (it.u64 - 1u).u64 },

            BinaryInsn("ADD_B",  0x40u) { l, r -> (l.i8  + r.i8).u64 },
            BinaryInsn("ADD_UB", 0x41u) { l, r -> (l.u8  + r.u8).u64 },
            BinaryInsn("ADD_S",  0x42u) { l, r -> (l.i16 + r.i16).u64 },
            BinaryInsn("ADD_US", 0x43u) { l, r -> (l.u16 + r.u16).u64 },
            BinaryInsn("ADD_I",  0x44u) { l, r -> (l.i32 + r.i32).u64 },
            BinaryInsn("ADD_UI", 0x45u) { l, r -> (l.u32 + r.u32).u64 },
            BinaryInsn("ADD_L",  0x46u) { l, r -> (l.i64 + r.i64).u64 },
            BinaryInsn("ADD_UL", 0x47u) { l, r -> (l.u64 + r.u64).u64 },
            BinaryInsn("ADD_F",  0x48u) { l, r -> (l.f32 + r.f32).u64 },
            BinaryInsn("ADD_D",  0x49u) { l, r -> (l.f64 + r.f64).u64 }
        )
    }
}