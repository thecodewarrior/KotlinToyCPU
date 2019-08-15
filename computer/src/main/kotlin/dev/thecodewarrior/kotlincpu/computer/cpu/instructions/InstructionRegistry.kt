package dev.thecodewarrior.kotlincpu.computer.cpu.instructions

import dev.thecodewarrior.kotlincpu.common.DataType
import dev.thecodewarrior.kotlincpu.common.Insn
import dev.thecodewarrior.kotlincpu.common.Instructions
import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import java.nio.ByteBuffer

object InstructionRegistry {
    val instructions: MutableList<Instruction> = mutableListOf()
//        SimpleInsn("NOP", 0x0u, 0u) { _, _ -> },
//        SimpleInsn("HALT", 0xFFFFu, 0u) { cpu, _ ->
//            cpu.computer.running = false
//        }

    val nop = +insn(Instructions.nop) { _, _ -> }

    val mov_imm = +insn(Instructions.mov_imm) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val value = DataType.u32.read(buffer)
        cpu.registers[dst] = value
    }
    val mov_r = +insn(Instructions.mov_r) { cpu, buffer ->
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

    val cmp_imm = +insn(Instructions.cmp_imm) { cpu, buffer ->
        val left = DataType.reg.read(buffer)
        val right = DataType.u32.read(buffer)
        cpu.flags.comparison = cpu.registers[left].compareTo(right)
    }
    val cmp_r = +insn(Instructions.cmp_r) { cpu, buffer ->
        val left = DataType.reg.read(buffer)
        val right = DataType.reg.read(buffer)
        cpu.flags.comparison = cpu.registers[left].compareTo(cpu.registers[right])
    }

    val jmp_imm = +insn(Instructions.jmp_imm) { cpu, buffer ->
        cpu.pc = DataType.u32.read(buffer)
    }
    val jmp_r = +insn(Instructions.jmp_r) { cpu, buffer ->
        cpu.pc = cpu.registers[DataType.reg.read(buffer)]
    }

    val inc = +insn(Instructions.inc) { cpu, buffer ->
        val reg = DataType.reg.read(buffer)
        cpu.registers[reg] = cpu.registers[reg] + 1u
    }
    val dec = +insn(Instructions.dec) { cpu, buffer ->
        val reg = DataType.reg.read(buffer)
        cpu.registers[reg] = cpu.registers[reg] - 1u
    }

    val add_imm = +insn(Instructions.add_imm) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val left = DataType.reg.read(buffer)
        val right = DataType.u32.read(buffer)
        cpu.registers[dst] = cpu.registers[left] + right
    }
    val add_r = +insn(Instructions.add_r) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val left = DataType.reg.read(buffer)
        val right = DataType.reg.read(buffer)
        cpu.registers[dst] = cpu.registers[left] + cpu.registers[right]
    }

    val sub_imm = +insn(Instructions.sub_imm) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val left = DataType.reg.read(buffer)
        val right = DataType.u32.read(buffer)
        cpu.registers[dst] = cpu.registers[left] - right
    }
    val sub_r = +insn(Instructions.sub_r) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val left = DataType.reg.read(buffer)
        val right = DataType.reg.read(buffer)
        cpu.registers[dst] = cpu.registers[left] - cpu.registers[right]
    }

    val mul_imm = +insn(Instructions.mul_imm) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val left = DataType.reg.read(buffer)
        val right = DataType.u32.read(buffer)
        cpu.registers[dst] = cpu.registers[left] * right
    }
    val mul_r = +insn(Instructions.mul_r) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val left = DataType.reg.read(buffer)
        val right = DataType.reg.read(buffer)
        cpu.registers[dst] = cpu.registers[left] * cpu.registers[right]
    }

    val div_imm = +insn(Instructions.div_imm) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val left = DataType.reg.read(buffer)
        val right = DataType.u32.read(buffer)
        cpu.registers[dst] = cpu.registers[left] / right
    }
    val div_r = +insn(Instructions.div_r) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val left = DataType.reg.read(buffer)
        val right = DataType.reg.read(buffer)
        cpu.registers[dst] = cpu.registers[left] / cpu.registers[right]
    }
    val sdiv_imm = +insn(Instructions.sdiv_imm) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val left = DataType.reg.read(buffer)
        val right = DataType.u32.read(buffer)
        cpu.registers[dst] = (cpu.registers[left].toInt() / right.toInt()).toUInt()
    }
    val sdiv_r = +insn(Instructions.sdiv_r) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val left = DataType.reg.read(buffer)
        val right = DataType.reg.read(buffer)
        cpu.registers[dst] = (cpu.registers[left].toInt() / cpu.registers[right].toInt()).toUInt()
    }

    val mod_imm = +insn(Instructions.mod_imm) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val left = DataType.reg.read(buffer)
        val right = DataType.u32.read(buffer)
        cpu.registers[dst] = cpu.registers[left] % right
    }
    val mod_r = +insn(Instructions.mod_r) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val left = DataType.reg.read(buffer)
        val right = DataType.reg.read(buffer)
        cpu.registers[dst] = cpu.registers[left] % cpu.registers[right]
    }
    val smod_imm = +insn(Instructions.smod_imm) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val left = DataType.reg.read(buffer)
        val right = DataType.u32.read(buffer)
        cpu.registers[dst] = (cpu.registers[left].toInt() % right.toInt()).toUInt()
    }
    val smod_r = +insn(Instructions.smod_r) { cpu, buffer ->
        val dst = DataType.reg.read(buffer)
        val left = DataType.reg.read(buffer)
        val right = DataType.reg.read(buffer)
        cpu.registers[dst] = (cpu.registers[left].toInt() % cpu.registers[right].toInt()).toUInt()
    }


    private val instructionMap = instructions.associateBy { it.insn }
    fun findInsn(opcode: Insn): Instruction = instructionMap.getValue(opcode)

    private operator fun Instruction.unaryPlus(): Instruction {
        instructions.add(this)
        return this
    }

    private inline fun insn(opcode: Insn, crossinline callback: (CPU, ByteBuffer) -> Unit): Instruction {
        return object : Instruction(opcode) {
            override fun run(cpu: CPU, buffer: ByteBuffer) {
                callback(cpu, buffer)
            }
        }
    }
}