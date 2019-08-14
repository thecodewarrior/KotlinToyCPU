package dev.thecodewarrior.kotlincpu.computer.cpu

import dev.thecodewarrior.kotlincpu.computer.cpu.instructions.Insn
import dev.thecodewarrior.kotlincpu.computer.cpu.instructions.InstructionRegistry
import dev.thecodewarrior.kotlincpu.computer.util.extensions.pos
import org.slf4j.LoggerFactory
import java.nio.ByteBuffer

class CPU(val computer: Computer) {
    val registers = Registers(this, Constants.registerCount)
    var pc: UInt
        get() = registers[registers.count-1]
        set(value) { registers[registers.count-1] = value }

    val programBuffer = ByteBuffer.wrap(computer.memory.buffer.array())

    fun instructionAt(address: Int): Insn {
        return InstructionRegistry.findInsn(programBuffer, address)
            ?: error("Unknown instruction at 0x${address.toString(16)}")
    }

    fun step() {
        val insnAddress = pc.toInt()
        try {
            val insn = instructionAt(insnAddress)
            programBuffer.pos = insn.dataStart(programBuffer, insnAddress)
            pc += insn.width.toUInt()
            insn.run(this, programBuffer)
        } catch(e: Exception) {
            logger.error("Error at insn 0x${insnAddress.toString(16)}", e)
            computer.running = false
        }
    }
}

val logger = LoggerFactory.getLogger(CPU::class.java)