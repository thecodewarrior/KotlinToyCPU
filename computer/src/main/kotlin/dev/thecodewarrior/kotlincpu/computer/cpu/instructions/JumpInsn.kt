package dev.thecodewarrior.kotlincpu.computer.cpu.instructions

import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUByte
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getULong
import java.nio.ByteBuffer

abstract class JumpInsn(name: String, qualifier: UShort, width: UInt): Insn(name, 0x40u, qualifier, width) {
    class BinaryInsn<T>(name: String, qualifier: UShort, val type: DataType<T>, val operation: (T, T) -> Boolean): JumpInsn(name, qualifier, 10u) {
        override fun run(cpu: CPU, buffer: ByteBuffer) {
            val left = buffer.getUByte()
            val right = buffer.getUByte()
            val destination = buffer.getULong()
            if(operation(
                    type.fromULong(cpu.registers[left]), type.fromULong(cpu.registers[right])
                )) {
                cpu.ctr = destination.toUInt()
            }
        }
    }

    class BinaryRightConstInsn<T>(name: String, qualifier: UShort, val type: DataType<T>, val operation: (T, T) -> Boolean): JumpInsn(name, qualifier, 9u + type.width) {
        override fun run(cpu: CPU, buffer: ByteBuffer) {
            val left = buffer.getUByte()
            val right = type.get(buffer)
            val destination = buffer.getULong()
            if(operation(
                    type.fromULong(cpu.registers[left]), right
                )) {
                cpu.ctr = destination.toUInt()
            }
        }
    }

    class BinaryLeftConstInsn<T>(name: String, qualifier: UShort, val type: DataType<T>, val operation: (T, T) -> Boolean): JumpInsn(name, qualifier, 9u + type.width) {
        override fun run(cpu: CPU, buffer: ByteBuffer) {
            val left = type.get(buffer)
            val right = buffer.getUByte()
            val destination = buffer.getULong()
            if(operation(
                    left, type.fromULong(cpu.registers[right])
                )) {
                cpu.ctr = destination.toUInt()
            }
        }
    }

    companion object {
        val instructions = mutableListOf<Insn>()

        private operator fun <T: Insn> T.unaryPlus(): T {
            instructions.add(this)
            return this
        }

        init {
            unconditional()
            eq()
            lt()
            le()
            gt()
            ge()
        }

        private fun unconditional() {
            instructions.add(object : JumpInsn("JMP", 0x100u, 8u) {
                override fun run(cpu: CPU, buffer: ByteBuffer) {
                    cpu.ctr = buffer.getULong().toUInt()
                }
            })
        }

        private fun eq() {
            // add registers
            +BinaryInsn("JMP_EQ.i8",  0x000u, DataType.i8 ) { l, r -> l == r }
            +BinaryInsn("JMP_EQ.u8",  0x001u, DataType.u8 ) { l, r -> l == r }
            +BinaryInsn("JMP_EQ.i16", 0x002u, DataType.i16) { l, r -> l == r }
            +BinaryInsn("JMP_EQ.u16", 0x003u, DataType.u16) { l, r -> l == r }
            +BinaryInsn("JMP_EQ.i32", 0x004u, DataType.i32) { l, r -> l == r }
            +BinaryInsn("JMP_EQ.u32", 0x005u, DataType.u32) { l, r -> l == r }
            +BinaryInsn("JMP_EQ.i64", 0x006u, DataType.i64) { l, r -> l == r }
            +BinaryInsn("JMP_EQ.u64", 0x007u, DataType.u64) { l, r -> l == r }
            +BinaryInsn("JMP_EQ.f32", 0x008u, DataType.f32) { l, r -> l == r }
            +BinaryInsn("JMP_EQ.f64", 0x009u, DataType.f64) { l, r -> l == r }

            // add register and constant
            +BinaryRightConstInsn("JMP_EQ_RC.i8",  0x010u, DataType.i8 ) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_EQ_RC.u8",  0x011u, DataType.u8 ) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_EQ_RC.i16", 0x012u, DataType.i16) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_EQ_RC.u16", 0x013u, DataType.u16) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_EQ_RC.i32", 0x014u, DataType.i32) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_EQ_RC.u32", 0x015u, DataType.u32) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_EQ_RC.i64", 0x016u, DataType.i64) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_EQ_RC.u64", 0x017u, DataType.u64) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_EQ_RC.f32", 0x018u, DataType.f32) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_EQ_RC.f64", 0x019u, DataType.f64) { l, r -> l == r }
        }

        private fun ne() {
            // add registers
            +BinaryInsn("JMP_NE.i8",  0x020u, DataType.i8 ) { l, r -> l == r }
            +BinaryInsn("JMP_NE.u8",  0x021u, DataType.u8 ) { l, r -> l == r }
            +BinaryInsn("JMP_NE.i16", 0x022u, DataType.i16) { l, r -> l == r }
            +BinaryInsn("JMP_NE.u16", 0x023u, DataType.u16) { l, r -> l == r }
            +BinaryInsn("JMP_NE.i32", 0x024u, DataType.i32) { l, r -> l == r }
            +BinaryInsn("JMP_NE.u32", 0x025u, DataType.u32) { l, r -> l == r }
            +BinaryInsn("JMP_NE.i64", 0x026u, DataType.i64) { l, r -> l == r }
            +BinaryInsn("JMP_NE.u64", 0x027u, DataType.u64) { l, r -> l == r }
            +BinaryInsn("JMP_NE.f32", 0x028u, DataType.f32) { l, r -> l == r }
            +BinaryInsn("JMP_NE.f64", 0x029u, DataType.f64) { l, r -> l == r }

            // add register and constant
            +BinaryRightConstInsn("JMP_NE_RC.i8",  0x030u, DataType.i8 ) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_NE_RC.u8",  0x031u, DataType.u8 ) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_NE_RC.i16", 0x032u, DataType.i16) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_NE_RC.u16", 0x033u, DataType.u16) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_NE_RC.i32", 0x034u, DataType.i32) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_NE_RC.u32", 0x035u, DataType.u32) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_NE_RC.i64", 0x036u, DataType.i64) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_NE_RC.u64", 0x037u, DataType.u64) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_NE_RC.f32", 0x038u, DataType.f32) { l, r -> l == r }
            +BinaryRightConstInsn("JMP_NE_RC.f64", 0x039u, DataType.f64) { l, r -> l == r }
        }

        private fun lt() {
            +BinaryInsn("JMP_LT.i8",  0x040u, DataType.i8 ) { l, r -> l < r }
            +BinaryInsn("JMP_LT.u8",  0x041u, DataType.u8 ) { l, r -> l < r }
            +BinaryInsn("JMP_LT.i16", 0x042u, DataType.i16) { l, r -> l < r }
            +BinaryInsn("JMP_LT.u16", 0x043u, DataType.u16) { l, r -> l < r }
            +BinaryInsn("JMP_LT.i32", 0x044u, DataType.i32) { l, r -> l < r }
            +BinaryInsn("JMP_LT.u32", 0x045u, DataType.u32) { l, r -> l < r }
            +BinaryInsn("JMP_LT.i64", 0x046u, DataType.i64) { l, r -> l < r }
            +BinaryInsn("JMP_LT.u64", 0x047u, DataType.u64) { l, r -> l < r }
            +BinaryInsn("JMP_LT.f32", 0x048u, DataType.f32) { l, r -> l < r }
            +BinaryInsn("JMP_LT.f64", 0x049u, DataType.f64) { l, r -> l < r }

            +BinaryRightConstInsn("JMP_LT_RC.i8",  0x050u, DataType.i8 ) { l, r -> l < r }
            +BinaryRightConstInsn("JMP_LT_RC.u8",  0x051u, DataType.u8 ) { l, r -> l < r }
            +BinaryRightConstInsn("JMP_LT_RC.i16", 0x052u, DataType.i16) { l, r -> l < r }
            +BinaryRightConstInsn("JMP_LT_RC.u16", 0x053u, DataType.u16) { l, r -> l < r }
            +BinaryRightConstInsn("JMP_LT_RC.i32", 0x054u, DataType.i32) { l, r -> l < r }
            +BinaryRightConstInsn("JMP_LT_RC.u32", 0x055u, DataType.u32) { l, r -> l < r }
            +BinaryRightConstInsn("JMP_LT_RC.i64", 0x056u, DataType.i64) { l, r -> l < r }
            +BinaryRightConstInsn("JMP_LT_RC.u64", 0x057u, DataType.u64) { l, r -> l < r }
            +BinaryRightConstInsn("JMP_LT_RC.f32", 0x058u, DataType.f32) { l, r -> l < r }
            +BinaryRightConstInsn("JMP_LT_RC.f64", 0x059u, DataType.f64) { l, r -> l < r }

            +BinaryLeftConstInsn("JMP_LT_CR.i8",  0x060u, DataType.i8 ) { l, r -> l < r }
            +BinaryLeftConstInsn("JMP_LT_CR.u8",  0x061u, DataType.u8 ) { l, r -> l < r }
            +BinaryLeftConstInsn("JMP_LT_CR.i16", 0x062u, DataType.i16) { l, r -> l < r }
            +BinaryLeftConstInsn("JMP_LT_CR.u16", 0x063u, DataType.u16) { l, r -> l < r }
            +BinaryLeftConstInsn("JMP_LT_CR.i32", 0x064u, DataType.i32) { l, r -> l < r }
            +BinaryLeftConstInsn("JMP_LT_CR.u32", 0x065u, DataType.u32) { l, r -> l < r }
            +BinaryLeftConstInsn("JMP_LT_CR.i64", 0x066u, DataType.i64) { l, r -> l < r }
            +BinaryLeftConstInsn("JMP_LT_CR.u64", 0x067u, DataType.u64) { l, r -> l < r }
            +BinaryLeftConstInsn("JMP_LT_CR.f32", 0x068u, DataType.f32) { l, r -> l < r }
            +BinaryLeftConstInsn("JMP_LT_CR.f64", 0x069u, DataType.f64) { l, r -> l < r }
        }

        private fun le() {
            +BinaryInsn("JMP_LE.i8",  0x070u, DataType.i8 ) { l, r -> l <= r }
            +BinaryInsn("JMP_LE.u8",  0x071u, DataType.u8 ) { l, r -> l <= r }
            +BinaryInsn("JMP_LE.i16", 0x072u, DataType.i16) { l, r -> l <= r }
            +BinaryInsn("JMP_LE.u16", 0x073u, DataType.u16) { l, r -> l <= r }
            +BinaryInsn("JMP_LE.i32", 0x074u, DataType.i32) { l, r -> l <= r }
            +BinaryInsn("JMP_LE.u32", 0x075u, DataType.u32) { l, r -> l <= r }
            +BinaryInsn("JMP_LE.i64", 0x076u, DataType.i64) { l, r -> l <= r }
            +BinaryInsn("JMP_LE.u64", 0x077u, DataType.u64) { l, r -> l <= r }
            +BinaryInsn("JMP_LE.f32", 0x078u, DataType.f32) { l, r -> l <= r }
            +BinaryInsn("JMP_LE.f64", 0x079u, DataType.f64) { l, r -> l <= r }

            +BinaryRightConstInsn("JMP_LE_RC.i8",  0x080u, DataType.i8 ) { l, r -> l <= r }
            +BinaryRightConstInsn("JMP_LE_RC.u8",  0x081u, DataType.u8 ) { l, r -> l <= r }
            +BinaryRightConstInsn("JMP_LE_RC.i16", 0x082u, DataType.i16) { l, r -> l <= r }
            +BinaryRightConstInsn("JMP_LE_RC.u16", 0x083u, DataType.u16) { l, r -> l <= r }
            +BinaryRightConstInsn("JMP_LE_RC.i32", 0x084u, DataType.i32) { l, r -> l <= r }
            +BinaryRightConstInsn("JMP_LE_RC.u32", 0x085u, DataType.u32) { l, r -> l <= r }
            +BinaryRightConstInsn("JMP_LE_RC.i64", 0x086u, DataType.i64) { l, r -> l <= r }
            +BinaryRightConstInsn("JMP_LE_RC.u64", 0x087u, DataType.u64) { l, r -> l <= r }
            +BinaryRightConstInsn("JMP_LE_RC.f32", 0x088u, DataType.f32) { l, r -> l <= r }
            +BinaryRightConstInsn("JMP_LE_RC.f64", 0x089u, DataType.f64) { l, r -> l <= r }

            +BinaryLeftConstInsn("JMP_LE_CR.i8",  0x090u, DataType.i8 ) { l, r -> l <= r }
            +BinaryLeftConstInsn("JMP_LE_CR.u8",  0x091u, DataType.u8 ) { l, r -> l <= r }
            +BinaryLeftConstInsn("JMP_LE_CR.i16", 0x092u, DataType.i16) { l, r -> l <= r }
            +BinaryLeftConstInsn("JMP_LE_CR.u16", 0x093u, DataType.u16) { l, r -> l <= r }
            +BinaryLeftConstInsn("JMP_LE_CR.i32", 0x094u, DataType.i32) { l, r -> l <= r }
            +BinaryLeftConstInsn("JMP_LE_CR.u32", 0x095u, DataType.u32) { l, r -> l <= r }
            +BinaryLeftConstInsn("JMP_LE_CR.i64", 0x096u, DataType.i64) { l, r -> l <= r }
            +BinaryLeftConstInsn("JMP_LE_CR.u64", 0x097u, DataType.u64) { l, r -> l <= r }
            +BinaryLeftConstInsn("JMP_LE_CR.f32", 0x098u, DataType.f32) { l, r -> l <= r }
            +BinaryLeftConstInsn("JMP_LE_CR.f64", 0x099u, DataType.f64) { l, r -> l <= r }
        }

        private fun gt() {
            +BinaryInsn("JMP_GT.i8",  0x0a0u, DataType.i8 ) { l, r -> l > r }
            +BinaryInsn("JMP_GT.u8",  0x0a1u, DataType.u8 ) { l, r -> l > r }
            +BinaryInsn("JMP_GT.i16", 0x0a2u, DataType.i16) { l, r -> l > r }
            +BinaryInsn("JMP_GT.u16", 0x0a3u, DataType.u16) { l, r -> l > r }
            +BinaryInsn("JMP_GT.i32", 0x0a4u, DataType.i32) { l, r -> l > r }
            +BinaryInsn("JMP_GT.u32", 0x0a5u, DataType.u32) { l, r -> l > r }
            +BinaryInsn("JMP_GT.i64", 0x0a6u, DataType.i64) { l, r -> l > r }
            +BinaryInsn("JMP_GT.u64", 0x0a7u, DataType.u64) { l, r -> l > r }
            +BinaryInsn("JMP_GT.f32", 0x0a8u, DataType.f32) { l, r -> l > r }
            +BinaryInsn("JMP_GT.f64", 0x0a9u, DataType.f64) { l, r -> l > r }

            +BinaryRightConstInsn("JMP_GT_RC.i8",  0x0b0u, DataType.i8 ) { l, r -> l > r }
            +BinaryRightConstInsn("JMP_GT_RC.u8",  0x0b1u, DataType.u8 ) { l, r -> l > r }
            +BinaryRightConstInsn("JMP_GT_RC.i16", 0x0b2u, DataType.i16) { l, r -> l > r }
            +BinaryRightConstInsn("JMP_GT_RC.u16", 0x0b3u, DataType.u16) { l, r -> l > r }
            +BinaryRightConstInsn("JMP_GT_RC.i32", 0x0b4u, DataType.i32) { l, r -> l > r }
            +BinaryRightConstInsn("JMP_GT_RC.u32", 0x0b5u, DataType.u32) { l, r -> l > r }
            +BinaryRightConstInsn("JMP_GT_RC.i64", 0x0b6u, DataType.i64) { l, r -> l > r }
            +BinaryRightConstInsn("JMP_GT_RC.u64", 0x0b7u, DataType.u64) { l, r -> l > r }
            +BinaryRightConstInsn("JMP_GT_RC.f32", 0x0b8u, DataType.f32) { l, r -> l > r }
            +BinaryRightConstInsn("JMP_GT_RC.f64", 0x0b9u, DataType.f64) { l, r -> l > r }

            +BinaryLeftConstInsn("JMP_GT_CR.i8",  0x0c0u, DataType.i8 ) { l, r -> l > r }
            +BinaryLeftConstInsn("JMP_GT_CR.u8",  0x0c1u, DataType.u8 ) { l, r -> l > r }
            +BinaryLeftConstInsn("JMP_GT_CR.i16", 0x0c2u, DataType.i16) { l, r -> l > r }
            +BinaryLeftConstInsn("JMP_GT_CR.u16", 0x0c3u, DataType.u16) { l, r -> l > r }
            +BinaryLeftConstInsn("JMP_GT_CR.i32", 0x0c4u, DataType.i32) { l, r -> l > r }
            +BinaryLeftConstInsn("JMP_GT_CR.u32", 0x0c5u, DataType.u32) { l, r -> l > r }
            +BinaryLeftConstInsn("JMP_GT_CR.i64", 0x0c6u, DataType.i64) { l, r -> l > r }
            +BinaryLeftConstInsn("JMP_GT_CR.u64", 0x0c7u, DataType.u64) { l, r -> l > r }
            +BinaryLeftConstInsn("JMP_GT_CR.f32", 0x0c8u, DataType.f32) { l, r -> l > r }
            +BinaryLeftConstInsn("JMP_GT_CR.f64", 0x0c9u, DataType.f64) { l, r -> l > r }
        }

        private fun ge() {
            +BinaryInsn("JMP_GE.i8",  0x0d0u, DataType.i8 ) { l, r -> l >= r }
            +BinaryInsn("JMP_GE.u8",  0x0d1u, DataType.u8 ) { l, r -> l >= r }
            +BinaryInsn("JMP_GE.i16", 0x0d2u, DataType.i16) { l, r -> l >= r }
            +BinaryInsn("JMP_GE.u16", 0x0d3u, DataType.u16) { l, r -> l >= r }
            +BinaryInsn("JMP_GE.i32", 0x0d4u, DataType.i32) { l, r -> l >= r }
            +BinaryInsn("JMP_GE.u32", 0x0d5u, DataType.u32) { l, r -> l >= r }
            +BinaryInsn("JMP_GE.i64", 0x0d6u, DataType.i64) { l, r -> l >= r }
            +BinaryInsn("JMP_GE.u64", 0x0d7u, DataType.u64) { l, r -> l >= r }
            +BinaryInsn("JMP_GE.f32", 0x0d8u, DataType.f32) { l, r -> l >= r }
            +BinaryInsn("JMP_GE.f64", 0x0d9u, DataType.f64) { l, r -> l >= r }

            +BinaryRightConstInsn("JMP_GE_RC.i8",  0x0e0u, DataType.i8 ) { l, r -> l >= r }
            +BinaryRightConstInsn("JMP_GE_RC.u8",  0x0e1u, DataType.u8 ) { l, r -> l >= r }
            +BinaryRightConstInsn("JMP_GE_RC.i16", 0x0e2u, DataType.i16) { l, r -> l >= r }
            +BinaryRightConstInsn("JMP_GE_RC.u16", 0x0e3u, DataType.u16) { l, r -> l >= r }
            +BinaryRightConstInsn("JMP_GE_RC.i32", 0x0e4u, DataType.i32) { l, r -> l >= r }
            +BinaryRightConstInsn("JMP_GE_RC.u32", 0x0e5u, DataType.u32) { l, r -> l >= r }
            +BinaryRightConstInsn("JMP_GE_RC.i64", 0x0e6u, DataType.i64) { l, r -> l >= r }
            +BinaryRightConstInsn("JMP_GE_RC.u64", 0x0e7u, DataType.u64) { l, r -> l >= r }
            +BinaryRightConstInsn("JMP_GE_RC.f32", 0x0e8u, DataType.f32) { l, r -> l >= r }
            +BinaryRightConstInsn("JMP_GE_RC.f64", 0x0e9u, DataType.f64) { l, r -> l >= r }

            +BinaryLeftConstInsn("JMP_GE_CR.i8",  0x0f0u, DataType.i8 ) { l, r -> l >= r }
            +BinaryLeftConstInsn("JMP_GE_CR.u8",  0x0f1u, DataType.u8 ) { l, r -> l >= r }
            +BinaryLeftConstInsn("JMP_GE_CR.i16", 0x0f2u, DataType.i16) { l, r -> l >= r }
            +BinaryLeftConstInsn("JMP_GE_CR.u16", 0x0f3u, DataType.u16) { l, r -> l >= r }
            +BinaryLeftConstInsn("JMP_GE_CR.i32", 0x0f4u, DataType.i32) { l, r -> l >= r }
            +BinaryLeftConstInsn("JMP_GE_CR.u32", 0x0f5u, DataType.u32) { l, r -> l >= r }
            +BinaryLeftConstInsn("JMP_GE_CR.i64", 0x0f6u, DataType.i64) { l, r -> l >= r }
            +BinaryLeftConstInsn("JMP_GE_CR.u64", 0x0f7u, DataType.u64) { l, r -> l >= r }
            +BinaryLeftConstInsn("JMP_GE_CR.f32", 0x0f8u, DataType.f32) { l, r -> l >= r }
            +BinaryLeftConstInsn("JMP_GE_CR.f64", 0x0f9u, DataType.f64) { l, r -> l >= r }
        }
    }
}