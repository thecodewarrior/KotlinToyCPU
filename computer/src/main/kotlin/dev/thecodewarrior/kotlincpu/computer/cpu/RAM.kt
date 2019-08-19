package dev.thecodewarrior.kotlincpu.computer.cpu

import dev.thecodewarrior.kotlincpu.common.util.getUInt
import dev.thecodewarrior.kotlincpu.common.util.putUInt
import java.nio.ByteBuffer

class RAM(val computer: Computer, val size: Int) {
    val buffer: ByteBuffer = ByteBuffer.allocate(size)

    operator fun get(address: UInt): UInt {
        if(address >= size.toUInt())
            throw IndexOutOfBoundsException("0x${address.toString(16)}")
        return buffer.getUInt(address.toInt())
    }
    operator fun set(address: UInt, value: UInt) {
        if(address >= size.toUInt())
            throw IndexOutOfBoundsException("0x${address.toString(16)}")
        return buffer.putUInt(address.toInt(), value)
    }
}