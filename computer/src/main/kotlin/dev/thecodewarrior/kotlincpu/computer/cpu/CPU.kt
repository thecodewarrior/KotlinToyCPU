package dev.thecodewarrior.kotlincpu.computer.cpu

import dev.thecodewarrior.kotlincpu.common.Condition
import dev.thecodewarrior.kotlincpu.common.Insn
import dev.thecodewarrior.kotlincpu.common.Register
import dev.thecodewarrior.kotlincpu.common.util.getUShort
import dev.thecodewarrior.kotlincpu.common.util.pos
import dev.thecodewarrior.kotlincpu.computer.cpu.instructions.InstructionRegistry
import org.slf4j.LoggerFactory
import java.nio.ByteBuffer

class CPU(val computer: Computer) {
    val registers = Registers(this, Register.values().size)
    val flags = Flags(this)
    var pc: UInt
        get() = registers[Register.pc]
        set(value) { registers[Register.pc] = value }

    val programBuffer = ByteBuffer.wrap(computer.memory.buffer.array())

    fun step() {
        val insnAddress = pc.toInt()
        try {
            programBuffer.pos = insnAddress
            val opcode = programBuffer.getUShort()

            val insn = Insn.decode(opcode)
                ?: error("Unknown opcode 0x${opcode.toString(16).padStart(4, '0')}")
            val condition = Condition.decode(opcode)

            pc += 2u + insn.payloadWidth.toUInt()
            if(condition.matches(flags.comparison)) {
                InstructionRegistry.findInsn(insn).run(this, programBuffer)
            }
        } catch(e: Exception) {
            logger.error("Error at 0x${insnAddress.toString(16)}", e)
            throw e
        }
    }
}

val logger = LoggerFactory.getLogger(CPU::class.java)