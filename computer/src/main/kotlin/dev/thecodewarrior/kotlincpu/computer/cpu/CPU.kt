package dev.thecodewarrior.kotlincpu.computer.cpu

import dev.thecodewarrior.kotlincpu.computer.cpu.instructions.Insn
import dev.thecodewarrior.kotlincpu.computer.cpu.instructions.InstructionRegistry
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUShort
import org.slf4j.LoggerFactory
import java.nio.ByteBuffer

class CPU(val computer: Computer) {
    val registers = Registers(this, 256)
    var ctr: UInt
        get() = registers.values[0xFF].toUInt()
        set(value) { registers.values[0xFF] = value.toULong() }

    val programBuffer = ByteBuffer.wrap(computer.memory.buffer.array())

    fun instructionAt(address: Int): Insn {
        programBuffer.position(address)
        val opcode = programBuffer.getUShort()
        return InstructionRegistry.instructionMap.getValue(opcode)
    }

    fun step() {
        val insnAddress = ctr.toInt()
        try {
            val insn = instructionAt(insnAddress)
            programBuffer.position(insnAddress + 2)
            ctr += 2u + insn.width
            insn.run(this, programBuffer)
        } catch(e: Exception) {
            logger.error("Error at insn 0x${insnAddress.toString(16)}", e)
            computer.running = false
        }
    }
}

val logger = LoggerFactory.getLogger(CPU::class.java)