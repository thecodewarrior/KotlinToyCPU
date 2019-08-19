package dev.thecodewarrior.kotlincpu.computer.cpu.instructions

import dev.thecodewarrior.kotlincpu.common.Argument
import dev.thecodewarrior.kotlincpu.common.DataType
import dev.thecodewarrior.kotlincpu.common.Insn
import dev.thecodewarrior.kotlincpu.common.Instructions
import dev.thecodewarrior.kotlincpu.common.Register
import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap
import java.nio.ByteBuffer

object InstructionRegistry {
    val instructions: MutableList<Instruction> = mutableListOf()
//        SimpleInsn("NOP", 0x0u, 0u) { _, _ -> },
//        SimpleInsn("HALT", 0xFFFFu, 0u) { cpu, _ ->
//            cpu.computer.running = false
//        }

    val nop = +insn(Instructions.nop) { _, _ -> }

    val mov_imm = +insn(Instructions.mov_imm) { cpu, (value: UInt, dst: Register) ->
        cpu.registers[dst] = value
    }
    val mov_r = +insn(Instructions.mov_r) { cpu, (src: Register, dst: Register) ->
        cpu.registers[dst] = cpu.registers[src]
    }

    val ldr_imm = +insn(Instructions.ldr_imm) { cpu, (dst: Register, address: UInt) ->
        cpu.registers[dst] = cpu.computer.memory[address]
    }
    val ldr_imm_off_r = +insn(Instructions.ldr_imm_off_r) { cpu, (dst: Register, address: UInt, offset: Register) ->
        cpu.registers[dst] = cpu.computer.memory[address + cpu.registers[offset]]
    }

    val ldr_r = +insn(Instructions.ldr_r) { cpu, (dst: Register, address: Register) ->
        cpu.registers[dst] = cpu.computer.memory[cpu.registers[address]]
    }
    val ldr_r_off_imm = +insn(Instructions.ldr_r_off_imm) { cpu, (dst: Register, address: Register, offset: UInt) ->
        cpu.registers[dst] = cpu.computer.memory[cpu.registers[address] + offset]
    }
    val ldr_r_off_r = +insn(Instructions.ldr_r_off_r) { cpu, (dst: Register, address: Register, offset: Register) ->
        cpu.registers[dst] = cpu.computer.memory[cpu.registers[address] + cpu.registers[offset]]
    }

    val str_imm = +insn(Instructions.str_imm) { cpu, (src: Register, address: UInt) ->
        cpu.computer.memory[address] = cpu.registers[src]
    }
    val str_imm_off_r = +insn(Instructions.str_imm_off_r) { cpu, (src: Register, address: UInt, offset: Register) ->
        cpu.computer.memory[address + cpu.registers[offset]] = cpu.registers[src]
    }

    val str_r = +insn(Instructions.str_r) { cpu, (src: Register, address: Register) ->
        cpu.computer.memory[cpu.registers[address]] = cpu.registers[src]
    }
    val str_r_off_imm = +insn(Instructions.str_r_off_imm) { cpu, (src: Register, address: Register, offset: UInt) ->
        cpu.computer.memory[cpu.registers[address] + offset] = cpu.registers[src]
    }
    val str_r_off_r = +insn(Instructions.str_r_off_r) { cpu, (src: Register, address: Register, offset: Register) ->
        cpu.computer.memory[cpu.registers[address] + cpu.registers[offset]] = cpu.registers[src]
    }

    val cmp_imm = +insn(Instructions.cmp_imm) { cpu, (left: Register, right: UInt) ->
        cpu.flags.comparison = cpu.registers[left].compareTo(right)
    }
    val cmp_r = +insn(Instructions.cmp_r) { cpu, (left: Register, right: Register) ->
        cpu.flags.comparison = cpu.registers[left].compareTo(cpu.registers[right])
    }

    val jmp_imm = +insn(Instructions.jmp_imm) { cpu, (dst: UInt) ->
        cpu.pc = dst
    }
    val jmp_r = +insn(Instructions.jmp_r) { cpu, (dst: Register) ->
        cpu.pc = cpu.registers[dst]
    }

    val inc = +insn(Instructions.inc) { cpu, (reg: Register) ->
        cpu.registers[reg] = cpu.registers[reg] + 1u
    }
    val dec = +insn(Instructions.dec) { cpu, (reg: Register) ->
        cpu.registers[reg] = cpu.registers[reg] - 1u
    }

    val add_imm = +insn(Instructions.add_imm) { cpu, (left: Register, right: UInt, dst: Register) ->
        cpu.registers[dst] = cpu.registers[left] + right
    }
    val add_r = +insn(Instructions.add_r) { cpu, (left: Register, right: Register, dst: Register) ->
        cpu.registers[dst] = cpu.registers[left] + cpu.registers[right]
    }

    val sub_imm = +insn(Instructions.sub_imm) { cpu, (left: Register, right: UInt, dst: Register) ->
        cpu.registers[dst] = cpu.registers[left] - right
    }
    val sub_r = +insn(Instructions.sub_r) { cpu, (left: Register, right: Register, dst: Register) ->
        cpu.registers[dst] = cpu.registers[left] - cpu.registers[right]
    }

    val mul_imm = +insn(Instructions.mul_imm) { cpu, (left: Register, right: UInt, dst: Register) ->
        cpu.registers[dst] = cpu.registers[left] * right
    }
    val mul_r = +insn(Instructions.mul_r) { cpu, (left: Register, right: Register, dst: Register) ->
        cpu.registers[dst] = cpu.registers[left] * cpu.registers[right]
    }

    val div_imm = +insn(Instructions.div_imm) { cpu, (left: Register, right: UInt, dst: Register) ->
        cpu.registers[dst] = cpu.registers[left] / right
    }
    val div_r = +insn(Instructions.div_r) { cpu, (left: Register, right: Register, dst: Register) ->
        cpu.registers[dst] = cpu.registers[left] / cpu.registers[right]
    }
    val sdiv_imm = +insn(Instructions.sdiv_imm) { cpu, (left: Register, right: UInt, dst: Register) ->
        cpu.registers[dst] = (cpu.registers[left].toInt() / right.toInt()).toUInt()
    }
    val sdiv_r = +insn(Instructions.sdiv_r) { cpu, (left: Register, right: Register, dst: Register) ->
        cpu.registers[dst] = (cpu.registers[left].toInt() / cpu.registers[right].toInt()).toUInt()
    }

    val mod_imm = +insn(Instructions.mod_imm) { cpu, (left: Register, right: UInt, dst: Register) ->
        cpu.registers[dst] = cpu.registers[left] % right
    }
    val mod_r = +insn(Instructions.mod_r) { cpu, (left: Register, right: Register, dst: Register) ->
        cpu.registers[dst] = cpu.registers[left] % cpu.registers[right]
    }
    val smod_imm = +insn(Instructions.smod_imm) { cpu, (left: Register, right: UInt, dst: Register) ->
        cpu.registers[dst] = (cpu.registers[left].toInt() % right.toInt()).toUInt()
    }
    val smod_r = +insn(Instructions.smod_r) { cpu, (left: Register, right: Register, dst: Register) ->
        cpu.registers[dst] = (cpu.registers[left].toInt() % cpu.registers[right].toInt()).toUInt()
    }

    val shl_imm = +insn(Instructions.shl_imm) { cpu, (value: Register, shift: UInt, dst: Register) ->
        cpu.registers[dst] = cpu.registers[value] shl shift.toInt()
    }
    val shl_r = +insn(Instructions.shl_r) { cpu, (value: Register, shift: Register, dst: Register) ->
        cpu.registers[dst] = cpu.registers[value] shl cpu.registers[shift].toInt()
    }

    val shr_imm = +insn(Instructions.shr_imm) { cpu, (value: Register, shift: UInt, dst: Register) ->
        cpu.registers[dst] = cpu.registers[value] shr shift.toInt()
    }
    val shr_r = +insn(Instructions.shr_r) { cpu, (value: Register, shift: Register, dst: Register) ->
        cpu.registers[dst] = cpu.registers[value] shr cpu.registers[shift].toInt()
    }
    val sshr_imm = +insn(Instructions.sshr_imm) { cpu, (value: Register, shift: UInt, dst: Register) ->
        cpu.registers[dst] = (cpu.registers[value].toInt() shr shift.toInt()).toUInt()
    }
    val sshr_r = +insn(Instructions.sshr_r) { cpu, (value: Register, shift: Register, dst: Register) ->
        cpu.registers[dst] = (cpu.registers[value].toInt() shr cpu.registers[shift].toInt()).toUInt()
    }

    val and_imm = +insn(Instructions.and_imm) { cpu, (left: Register, right: UInt, dst: Register) ->
        cpu.registers[dst] = cpu.registers[left] and right
    }
    val and_r = +insn(Instructions.and_r) { cpu, (left: Register, right: Register, dst: Register) ->
        cpu.registers[dst] = cpu.registers[left] and cpu.registers[right]
    }
    val or_imm = +insn(Instructions.or_imm) { cpu, (left: Register, right: UInt, dst: Register) ->
        cpu.registers[dst] = cpu.registers[left] or right
    }
    val or_r = +insn(Instructions.or_r) { cpu, (left: Register, right: Register, dst: Register) ->
        cpu.registers[dst] = cpu.registers[left] or cpu.registers[right]
    }
    val xor_imm = +insn(Instructions.xor_imm) { cpu, (left: Register, right: UInt, dst: Register) ->
        cpu.registers[dst] = cpu.registers[left] xor right
    }
    val xor_r = +insn(Instructions.xor_r) { cpu, (left: Register, right: Register, dst: Register) ->
        cpu.registers[dst] = cpu.registers[left] xor cpu.registers[right]
    }

    val not = +insn(Instructions.not) { cpu, (value: Register, dst: Register) ->
        cpu.registers[dst] = cpu.registers[value].inv()
    }

    private val instructionMap: Short2ObjectMap<Instruction> = instructions.associateByTo(Short2ObjectOpenHashMap()) { it.insn.opcode.toShort() }
    fun findInsn(insn: Insn): Instruction = instructionMap.getValue(insn.opcode.toShort())

    private operator fun Instruction.unaryPlus(): Instruction {
        instructions.add(this)
        return this
    }

    private inline fun insn(insn: Insn, crossinline callback: (CPU, Arguments) -> Unit): Instruction {
        val payload = insn.payload.filter { it.type !is DataType.asm_const }
        return object : Instruction(insn) {
            override fun run(cpu: CPU, buffer: ByteBuffer) {
                val arguments = Arguments(payload.map { it.type.read(buffer) })
                callback(cpu, arguments)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private class Arguments(private val values: List<Any>) {
        operator fun <T: Any> get(index: Int): T {
            return values[index] as T
        }

        operator fun <T: Any> component1(): T = this[0]
        operator fun <T: Any> component2(): T = this[1]
        operator fun <T: Any> component3(): T = this[2]
        operator fun <T: Any> component4(): T = this[3]
        operator fun <T: Any> component5(): T = this[4]
        operator fun <T: Any> component6(): T = this[5]
        operator fun <T: Any> component7(): T = this[6]
        operator fun <T: Any> component8(): T = this[7]
        operator fun <T: Any> component9(): T = this[8]
    }
}