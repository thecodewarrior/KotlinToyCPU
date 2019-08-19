package dev.thecodewarrior.kotlincpu.computer.cpu

import dev.thecodewarrior.kotlincpu.common.util.getUInt
import dev.thecodewarrior.kotlincpu.common.util.putUInt
import java.nio.ByteBuffer

class RAM(val computer: Computer, val size: Int) {
    val buffer: ByteBuffer = ByteBuffer.allocate(size)

    operator fun get(address: UInt): UInt {
        return buffer.getUInt(address.toInt())
    }
    operator fun set(address: UInt, value: UInt) {
        return buffer.putUInt(address.toInt(), value)
    }
}