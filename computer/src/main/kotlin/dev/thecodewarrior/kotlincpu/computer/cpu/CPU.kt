package dev.thecodewarrior.kotlincpu.computer.cpu

import dev.thecodewarrior.kotlincpu.common.Condition
import dev.thecodewarrior.kotlincpu.common.Insn
import dev.thecodewarrior.kotlincpu.computer.cpu.instructions.InstructionRegistry
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUShort
import dev.thecodewarrior.kotlincpu.computer.util.extensions.pos
import org.slf4j.LoggerFactory
import java.nio.ByteBuffer

class CPU(val computer: Computer) {
    val registers = Registers(this, Constants.registerCount)
    val flags = Flags(this)
    var pc: UInt
        get() = registers[registers.count-1]
        set(value) { registers[registers.count-1] = value }

    val programBuffer = ByteBuffer.wrap(computer.memory.buffer.array())

    fun step() {
        var insnAddress = pc.toInt()
        try {
            insnAddress = pc.toInt()
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
            computer.running = false
        }
    }
}

val logger = LoggerFactory.getLogger(CPU::class.java)