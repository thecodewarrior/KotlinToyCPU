package dev.thecodewarrior.kotlincpu.computer.cpu.instructions

import dev.thecodewarrior.kotlincpu.common.DataType
import dev.thecodewarrior.kotlincpu.common.Opcode
import dev.thecodewarrior.kotlincpu.common.Opcodes
import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUByte
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUInt
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getULong
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUShort
import java.nio.ByteBuffer

object InstructionRegistry {
    val instructions: MutableList<Insn> = mutableListOf()
//        SimpleInsn("NOP", 0x0u, 0u) { _, _ -> },
//        SimpleInsn("HALT", 0xFFFFu, 0u) { cpu, _ ->
//            cpu.computer.running = false
//        }

    val nop = +insn(Opcodes.nop) { _, _ -> }

    val mov_imm = +insn(Opcodes.mov_imm) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val value = DataType.u32.read(buffer)
        cpu.registers[dst] = value
    }
    val mov_r = +insn(Opcodes.mov_imm) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val src = DataType.reg.read(buffer)
        cpu.registers[dst] = cpu.registers[src]
    }

//    val ldr_imm = +//Opcode("ldr_imm", opcodes.create(), false, reg("dst"), u32("address"))
//    val ldr_imm_off_r = +//Opcode("ldr_imm_off_r", opcodes.create(), false, reg("dst"), u32("address"), reg("offset"))
//    val ldr_r = +//Opcode("ldr_r", opcodes.create(), false, reg("dst"), reg("address"))
//    val ldr_r_off_imm = +//Opcode("ldr_r_off_imm", opcodes.create(), false, reg("dst"), reg("address"), u32("offset"))
//    val ldr_r_off_r = +//Opcode("ldr_r_off_r", opcodes.create(), false, reg("dst"), reg("address"), reg("offset"))
//
//    val str_imm = +//Opcode("str_imm", opcodes.create(), false, reg("src"), u32("address"))
//    val str_imm_off_r = +//Opcode("str_imm_off_r", opcodes.create(), false, reg("src"), u32("address"), reg("offset"))
//    val str_r = +//Opcode("str_r", opcodes.create(), false, reg("src"), reg("address"))
//    val str_r_off_imm = +//Opcode("str_r_off_imm", opcodes.create(), false, reg("src"), reg("address"), u32("offset"))
//    val str_r_off_r = +//Opcode("str_r_off_r", opcodes.create(), false, reg("src"), reg("address"), reg("offset"))

    val add_imm = +insn(Opcodes.add_imm) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val left = DataType.reg.read(buffer)
        val right = DataType.u32.read(buffer)
        cpu.registers[dst] = cpu.registers[left] + right
    }
    val add_r = +insn(Opcodes.add_r) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val left = DataType.reg.read(buffer)
        val right = DataType.reg.read(buffer)
        cpu.registers[dst] = cpu.registers[left] + cpu.registers[right]
    }

    fun findInsn(buffer: ByteBuffer, address: Int): Insn? = instructions.find { it.matches(buffer, address) }

    private operator fun Insn.unaryPlus(): Insn {
        instructions.add(this)
        return this
    }

    private inline fun insn(opcode: Opcode, crossinline callback: (CPU, ByteBuffer) -> Unit): Insn {
        return object : Insn(opcode) {
            override fun run(cpu: CPU, buffer: ByteBuffer) {
                callback(cpu, buffer)
            }
        }
    }
}