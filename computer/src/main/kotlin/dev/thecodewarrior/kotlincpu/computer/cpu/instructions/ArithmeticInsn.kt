package dev.thecodewarrior.kotlincpu.computer.cpu.instructions

import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUByte
import java.nio.ByteBuffer

abstract class ArithmeticInsn(name: String, qualifier: UShort, width: UInt): Insn(name, 0x20u, qualifier, width) {

    class UnaryInPlaceInsn<T>(name: String, qualifier: UShort, val type: DataType<T>, val operation: (T) -> T): ArithmeticInsn(name, qualifier, 1u) {
        override fun run(cpu: CPU, buffer: ByteBuffer) {
            val register = buffer.getUByte()
            val input = type.fromULong(cpu.registers[register])
            val output = operation(input)
            cpu.registers[register] = type.toULong(output)
        }
    }

    class BinaryInsn<T>(name: String, qualifier: UShort, val type: DataType<T>, val operation: (T, T) -> T): ArithmeticInsn(name, qualifier, 3u) {
        override fun run(cpu: CPU, buffer: ByteBuffer) {
            val left = buffer.getUByte()
            val right = buffer.getUByte()
            val out = buffer.getUByte()
            cpu.registers[out] = type.toULong(
                operation(
                    type.fromULong(cpu.registers[left]), type.fromULong(cpu.registers[right])
                )
            )
        }
    }

    class BinaryRightConstInsn<T>(name: String, qualifier: UShort, val type: DataType<T>, val operation: (T, T) -> T): ArithmeticInsn(name, qualifier, 3u) {
        override fun run(cpu: CPU, buffer: ByteBuffer) {
            val left = buffer.getUByte()
            val right = type.get(buffer)
            val out = buffer.getUByte()
            cpu.registers[out] = type.toULong(
                operation(
                    type.fromULong(cpu.registers[left]), right
                )
            )
        }
    }

    class BinaryLeftConstInsn<T>(name: String, qualifier: UShort, val type: DataType<T>, val operation: (T, T) -> T): ArithmeticInsn(name, qualifier, 3u) {
        override fun run(cpu: CPU, buffer: ByteBuffer) {
            val left = type.get(buffer)
            val right = buffer.getUByte()
            val out = buffer.getUByte()
            cpu.registers[out] = type.toULong(
                operation(
                    left, type.fromULong(cpu.registers[right])
                )
            )
        }
    }

    companion object {
        val instructions = mutableListOf<Insn>()

        private operator fun <T: Insn> T.unaryPlus(): T {
            instructions.add(this)
            return this
        }

        init {
            increment()
            decrement()
            add()
            subtract()
            multiply()
            divide()
        }

        private fun increment() {
            // increment
            +UnaryInPlaceInsn("INC.i8",  0x000u, DataType.i8) { (it + 1).i8 }
            +UnaryInPlaceInsn("INC.u8", 0x001u, DataType.u8) { (it + 1u).u8 }
            +UnaryInPlaceInsn("INC.i16",  0x002u, DataType.i16) { (it + 1).i16 }
            +UnaryInPlaceInsn("INC.u16", 0x003u, DataType.u16) { (it + 1u).u16 }
            +UnaryInPlaceInsn("INC.i32",  0x004u, DataType.i32) { it + 1 }
            +UnaryInPlaceInsn("INC.u32", 0x005u, DataType.u32) { it + 1u }
            +UnaryInPlaceInsn("INC.i64",  0x006u, DataType.i64) { it + 1 }
            +UnaryInPlaceInsn("INC.u64", 0x007u, DataType.u64) { it + 1u }
        }

        private fun decrement() {
            // decrement
            +UnaryInPlaceInsn("DEC.i8",  0x010u, DataType.i8) { (it + 1).i8 }
            +UnaryInPlaceInsn("DEC.u8", 0x011u, DataType.u8) { (it + 1u).u8 }
            +UnaryInPlaceInsn("DEC.i16",  0x012u, DataType.i16) { (it + 1).i16 }
            +UnaryInPlaceInsn("DEC.u16", 0x013u, DataType.u16) { (it + 1u).u16 }
            +UnaryInPlaceInsn("DEC.i32",  0x014u, DataType.i32) { it - 1 }
            +UnaryInPlaceInsn("DEC.u32", 0x015u, DataType.u32) { it - 1u }
            +UnaryInPlaceInsn("DEC.i64",  0x016u, DataType.i64) { it - 1 }
            +UnaryInPlaceInsn("DEC.u64", 0x017u, DataType.u64) { it - 1u }
        }

        private fun add() {
            // add registers
            +BinaryInsn("ADD.i8",  0x020u, DataType.i8) { l, r -> (l + r).i8 }
            +BinaryInsn("ADD.u8", 0x021u, DataType.u8) { l, r -> (l + r).u8 }
            +BinaryInsn("ADD.i16",  0x022u, DataType.i16) { l, r -> (l + r).i16 }
            +BinaryInsn("ADD.u16", 0x023u, DataType.u16) { l, r -> (l + r).u16 }
            +BinaryInsn("ADD.i32",  0x024u, DataType.i32) { l, r -> l + r }
            +BinaryInsn("ADD.u32", 0x025u, DataType.u32) { l, r -> l + r }
            +BinaryInsn("ADD.i64",  0x026u, DataType.i64) { l, r -> l + r }
            +BinaryInsn("ADD.u64", 0x027u, DataType.u64) { l, r -> l + r }
            +BinaryInsn("ADD.f32",  0x028u, DataType.f32) { l, r -> l + r }
            +BinaryInsn("ADD.f64",  0x029u, DataType.f64) { l, r -> l + r }

            // add register and constant
            +BinaryRightConstInsn("ADDC.i8",  0x030u, DataType.i8) { l, r -> (l + r).i8 }
            +BinaryRightConstInsn("ADDC.u8", 0x031u, DataType.u8) { l, r -> (l + r).u8 }
            +BinaryRightConstInsn("ADDC.i16",  0x032u, DataType.i16) { l, r -> (l + r).i16 }
            +BinaryRightConstInsn("ADDC.u16", 0x033u, DataType.u16) { l, r -> (l + r).u16 }
            +BinaryRightConstInsn("ADDC.i32",  0x034u, DataType.i32) { l, r -> l + r }
            +BinaryRightConstInsn("ADDC.u32", 0x035u, DataType.u32) { l, r -> l + r }
            +BinaryRightConstInsn("ADDC.i64",  0x036u, DataType.i64) { l, r -> l + r }
            +BinaryRightConstInsn("ADDC.u64", 0x037u, DataType.u64) { l, r -> l + r }
            +BinaryRightConstInsn("ADDC.f32",  0x038u, DataType.f32) { l, r -> l + r }
            +BinaryRightConstInsn("ADDC.f64",  0x039u, DataType.f64) { l, r -> l + r }
        }

        /**
         * 0x05_
         */
        private fun subtract() {
            // subtract registers
            +BinaryInsn("SUB.i8",  0x040u, DataType.i8) { l, r -> (l - r).i8 }
            +BinaryInsn("SUB.u8", 0x041u, DataType.u8) { l, r -> (l - r).u8 }
            +BinaryInsn("SUB.i16",  0x042u, DataType.i16) { l, r -> (l - r).i16 }
            +BinaryInsn("SUB.u16", 0x043u, DataType.u16) { l, r -> (l - r).u16 }
            +BinaryInsn("SUB.i32",  0x044u, DataType.i32) { l, r -> l - r }
            +BinaryInsn("SUB.u32", 0x045u, DataType.u32) { l, r -> l - r }
            +BinaryInsn("SUB.i64",  0x046u, DataType.i64) { l, r -> l - r }
            +BinaryInsn("SUB.u64", 0x047u, DataType.u64) { l, r -> l - r }
            +BinaryInsn("SUB.f32",  0x048u, DataType.f32) { l, r -> l - r }
            +BinaryInsn("SUB.f64",  0x049u, DataType.f64) { l, r -> l - r }

            // subtract register and right constant
            +BinaryRightConstInsn("SUBC.i8",  0x050u, DataType.i8) { l, r -> (l - r).i8 }
            +BinaryRightConstInsn("SUBC.u8", 0x051u, DataType.u8) { l, r -> (l - r).u8 }
            +BinaryRightConstInsn("SUBC.i16",  0x052u, DataType.i16) { l, r -> (l - r).i16 }
            +BinaryRightConstInsn("SUBC.u16", 0x053u, DataType.u16) { l, r -> (l - r).u16 }
            +BinaryRightConstInsn("SUBC.i32",  0x054u, DataType.i32) { l, r -> l - r }
            +BinaryRightConstInsn("SUBC.u32", 0x055u, DataType.u32) { l, r -> l - r }
            +BinaryRightConstInsn("SUBC.i64",  0x056u, DataType.i64) { l, r -> l - r }
            +BinaryRightConstInsn("SUBC.u64", 0x057u, DataType.u64) { l, r -> l - r }
            +BinaryRightConstInsn("SUBC.f32",  0x058u, DataType.f32) { l, r -> l - r }
            +BinaryRightConstInsn("SUBC.f64",  0x059u, DataType.f64) { l, r -> l - r }

            // subtract register and left constant
            +BinaryLeftConstInsn("SUB_CR.i8",  0x060u, DataType.i8) { l, r -> (l - r).i8 }
            +BinaryLeftConstInsn("SUB_CR.u8", 0x061u, DataType.u8) { l, r -> (l - r).u8 }
            +BinaryLeftConstInsn("SUB_CR.i16",  0x062u, DataType.i16) { l, r -> (l - r).i16 }
            +BinaryLeftConstInsn("SUB_CR.u16", 0x063u, DataType.u16) { l, r -> (l - r).u16 }
            +BinaryLeftConstInsn("SUB_CR.i32",  0x064u, DataType.i32) { l, r -> l - r }
            +BinaryLeftConstInsn("SUB_CR.u32", 0x065u, DataType.u32) { l, r -> l - r }
            +BinaryLeftConstInsn("SUB_CR.i64",  0x066u, DataType.i64) { l, r -> l - r }
            +BinaryLeftConstInsn("SUB_CR.u64", 0x067u, DataType.u64) { l, r -> l - r }
            +BinaryLeftConstInsn("SUB_CR.f32",  0x068u, DataType.f32) { l, r -> l - r }
            +BinaryLeftConstInsn("SUB_CR.f64",  0x069u, DataType.f64) { l, r -> l - r }
        }

        private fun multiply() {
            // multiply registers
            +BinaryInsn("MUL.i8",  0x070u, DataType.i8) { l, r -> (l * r).i8 }
            +BinaryInsn("MUL.u8", 0x071u, DataType.u8) { l, r -> (l * r).u8 }
            +BinaryInsn("MUL.i16",  0x072u, DataType.i16) { l, r -> (l * r).i16 }
            +BinaryInsn("MUL.u16", 0x073u, DataType.u16) { l, r -> (l * r).u16 }
            +BinaryInsn("MUL.i32",  0x074u, DataType.i32) { l, r -> l * r }
            +BinaryInsn("MUL.u32", 0x075u, DataType.u32) { l, r -> l * r }
            +BinaryInsn("MUL.i64",  0x076u, DataType.i64) { l, r -> l * r }
            +BinaryInsn("MUL.u64", 0x077u, DataType.u64) { l, r -> l * r }
            +BinaryInsn("MUL.f32",  0x078u, DataType.f32) { l, r -> l * r }
            +BinaryInsn("MUL.f64",  0x079u, DataType.f64) { l, r -> l * r }

            // multiply register and right constant
            +BinaryRightConstInsn("MULC.i8",  0x080u, DataType.i8) { l, r -> (l * r).i8 }
            +BinaryRightConstInsn("MULC.u8", 0x081u, DataType.u8) { l, r -> (l * r).u8 }
            +BinaryRightConstInsn("MULC.i16",  0x082u, DataType.i16) { l, r -> (l * r).i16 }
            +BinaryRightConstInsn("MULC.u16", 0x083u, DataType.u16) { l, r -> (l * r).u16 }
            +BinaryRightConstInsn("MULC.i32",  0x084u, DataType.i32) { l, r -> l * r }
            +BinaryRightConstInsn("MULC.u32", 0x085u, DataType.u32) { l, r -> l * r }
            +BinaryRightConstInsn("MULC.i64",  0x086u, DataType.i64) { l, r -> l * r }
            +BinaryRightConstInsn("MULC.u64", 0x087u, DataType.u64) { l, r -> l * r }
            +BinaryRightConstInsn("MULC.f32",  0x088u, DataType.f32) { l, r -> l * r }
            +BinaryRightConstInsn("MULC.f64",  0x089u, DataType.f64) { l, r -> l * r }
        }

        fun divide() {
            // multiply registers
            +BinaryInsn("DIV.i8",  0x090u, DataType.i8) { l, r -> (l / r).i8 }
            +BinaryInsn("DIV.u8", 0x091u, DataType.u8) { l, r -> (l / r).u8 }
            +BinaryInsn("DIV.i16",  0x092u, DataType.i16) { l, r -> (l / r).i16 }
            +BinaryInsn("DIV.u16", 0x093u, DataType.u16) { l, r -> (l / r).u16 }
            +BinaryInsn("DIV.i32",  0x094u, DataType.i32) { l, r -> l / r }
            +BinaryInsn("DIV.u32", 0x095u, DataType.u32) { l, r -> l / r }
            +BinaryInsn("DIV.i64",  0x096u, DataType.i64) { l, r -> l / r }
            +BinaryInsn("DIV.u64", 0x097u, DataType.u64) { l, r -> l / r }
            +BinaryInsn("DIV.f32",  0x098u, DataType.f32) { l, r -> l / r }
            +BinaryInsn("DIV.f64",  0x099u, DataType.f64) { l, r -> l / r }

            // subtract register and right constant
            +BinaryRightConstInsn("DIVC.i8",  0x0a0u, DataType.i8) { l, r -> (l / r).i8 }
            +BinaryRightConstInsn("DIVC.u8", 0x0a1u, DataType.u8) { l, r -> (l / r).u8 }
            +BinaryRightConstInsn("DIVC.i16",  0x0a2u, DataType.i16) { l, r -> (l / r).i16 }
            +BinaryRightConstInsn("DIVC.u16", 0x0a3u, DataType.u16) { l, r -> (l / r).u16 }
            +BinaryRightConstInsn("DIVC.i32",  0x0a4u, DataType.i32) { l, r -> l / r }
            +BinaryRightConstInsn("DIVC.u32", 0x0a5u, DataType.u32) { l, r -> l / r }
            +BinaryRightConstInsn("DIVC.i64",  0x0a6u, DataType.i64) { l, r -> l / r }
            +BinaryRightConstInsn("DIVC.u64", 0x0a7u, DataType.u64) { l, r -> l / r }
            +BinaryRightConstInsn("DIVC.f32",  0x0a8u, DataType.f32) { l, r -> l / r }
            +BinaryRightConstInsn("DIVC.f64",  0x0a9u, DataType.f64) { l, r -> l / r }

            // subtract register and left constant
            +BinaryLeftConstInsn("DIV_CR.i8",  0x0b0u, DataType.i8) { l, r -> (l / r).i8 }
            +BinaryLeftConstInsn("DIV_CR.u8", 0x0b1u, DataType.u8) { l, r -> (l / r).u8 }
            +BinaryLeftConstInsn("DIV_CR.i16",  0x0b2u, DataType.i16) { l, r -> (l / r).i16 }
            +BinaryLeftConstInsn("DIV_CR.u16", 0x0b3u, DataType.u16) { l, r -> (l / r).u16 }
            +BinaryLeftConstInsn("DIV_CR.i32",  0x0b4u, DataType.i32) { l, r -> l / r }
            +BinaryLeftConstInsn("DIV_CR.u32", 0x0b5u, DataType.u32) { l, r -> l / r }
            +BinaryLeftConstInsn("DIV_CR.i64",  0x0b6u, DataType.i64) { l, r -> l / r }
            +BinaryLeftConstInsn("DIV_CR.u64", 0x0b7u, DataType.u64) { l, r -> l / r }
            +BinaryLeftConstInsn("DIV_CR.f32",  0x0b8u, DataType.f32) { l, r -> l / r }
            +BinaryLeftConstInsn("DIV_CR.f64",  0x0b9u, DataType.f64) { l, r -> l / r }
        }


        fun modulo() {
            // multiply registers
            +BinaryInsn("MOD.i8",  0x0c0u, DataType.i8) { l, r -> (l % r).i8 }
            +BinaryInsn("MOD.u8", 0x0c1u, DataType.u8) { l, r -> (l % r).u8 }
            +BinaryInsn("MOD.i16",  0x0c2u, DataType.i16) { l, r -> (l % r).i16 }
            +BinaryInsn("MOD.u16", 0x0c3u, DataType.u16) { l, r -> (l % r).u16 }
            +BinaryInsn("MOD.i32",  0x0c4u, DataType.i32) { l, r -> l % r }
            +BinaryInsn("MOD.u32", 0x0c5u, DataType.u32) { l, r -> l % r }
            +BinaryInsn("MOD.i64",  0x0c6u, DataType.i64) { l, r -> l % r }
            +BinaryInsn("MOD.u64", 0x0c7u, DataType.u64) { l, r -> l % r }
            +BinaryInsn("MOD.f32",  0x0c8u, DataType.f32) { l, r -> l % r }
            +BinaryInsn("MOD.f64",  0x0c9u, DataType.f64) { l, r -> l % r }

            // subtract register and right constant
            +BinaryRightConstInsn("MODC.i8",  0x0d0u, DataType.i8) { l, r -> (l % r).i8 }
            +BinaryRightConstInsn("MODC.u8", 0x0d1u, DataType.u8) { l, r -> (l % r).u8 }
            +BinaryRightConstInsn("MODC.i16",  0x0d2u, DataType.i16) { l, r -> (l % r).i16 }
            +BinaryRightConstInsn("MODC.u16", 0x0d3u, DataType.u16) { l, r -> (l % r).u16 }
            +BinaryRightConstInsn("MODC.i32",  0x0d4u, DataType.i32) { l, r -> l % r }
            +BinaryRightConstInsn("MODC.u32", 0x0d5u, DataType.u32) { l, r -> l % r }
            +BinaryRightConstInsn("MODC.i64",  0x0d6u, DataType.i64) { l, r -> l % r }
            +BinaryRightConstInsn("MODC.u64", 0x0d7u, DataType.u64) { l, r -> l % r }
            +BinaryRightConstInsn("MODC.f32",  0x0d8u, DataType.f32) { l, r -> l % r }
            +BinaryRightConstInsn("MODC.f64",  0x0d9u, DataType.f64) { l, r -> l % r }

            // subtract register and left constant
            +BinaryLeftConstInsn("MOD_CR.i8",  0x0e0u, DataType.i8) { l, r -> (l % r).i8 }
            +BinaryLeftConstInsn("MOD_CR.u8", 0x0e1u, DataType.u8) { l, r -> (l % r).u8 }
            +BinaryLeftConstInsn("MOD_CR.i16",  0x0e2u, DataType.i16) { l, r -> (l % r).i16 }
            +BinaryLeftConstInsn("MOD_CR.u16", 0x0e3u, DataType.u16) { l, r -> (l % r).u16 }
            +BinaryLeftConstInsn("MOD_CR.i32",  0x0e4u, DataType.i32) { l, r -> l % r }
            +BinaryLeftConstInsn("MOD_CR.u32", 0x0e5u, DataType.u32) { l, r -> l % r }
            +BinaryLeftConstInsn("MOD_CR.i64",  0x0e6u, DataType.i64) { l, r -> l % r }
            +BinaryLeftConstInsn("MOD_CR.u64", 0x0e7u, DataType.u64) { l, r -> l % r }
            +BinaryLeftConstInsn("MOD_CR.f32",  0x0e8u, DataType.f32) { l, r -> l % r }
            +BinaryLeftConstInsn("MOD_CR.f64",  0x0e9u, DataType.f64) { l, r -> l % r }
        }
    }
}