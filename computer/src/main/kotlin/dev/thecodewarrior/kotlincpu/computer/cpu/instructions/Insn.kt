package dev.thecodewarrior.kotlincpu.computer.cpu.instructions

import dev.thecodewarrior.kotlincpu.computer.cpu.CPU
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUShort
import java.nio.ByteBuffer

abstract class Insn {
    val name: String
    val opcode: UShort
    val qualifier: UShort?
    val width: UInt

    constructor(name: String, opcode: UShort, dataWidth: UInt) {
        this.name = name
        this.opcode = opcode
        this.qualifier = null
        this.width = dataWidth + 2u
    }

    constructor(name: String, opcode: UShort, qualifier: UShort, dataWidth: UInt) {
        this.name = name
        this.opcode = opcode
        this.qualifier = qualifier
        this.width = dataWidth + 4u
    }

    open fun matches(buffer: ByteBuffer, address: Int): Boolean {
        if(buffer.getUShort(address) != this.opcode) return false
        this.qualifier?.also { qualifier ->
            if(buffer.getUShort(address + 2) != qualifier) return false
        }
        return true
    }

    open fun dataStart(buffer: ByteBuffer, address: Int): Int {
        return address + 2 + if(this.qualifier != null) 2 else 0
    }

    abstract fun run(cpu: CPU, buffer: ByteBuffer)
}