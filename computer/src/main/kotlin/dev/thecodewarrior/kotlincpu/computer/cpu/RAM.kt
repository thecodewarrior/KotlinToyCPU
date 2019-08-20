package dev.thecodewarrior.kotlincpu.computer.cpu

import dev.thecodewarrior.kotlincpu.common.DataSize
import dev.thecodewarrior.kotlincpu.common.util.getUByte
import dev.thecodewarrior.kotlincpu.common.util.getUInt
import dev.thecodewarrior.kotlincpu.common.util.getUShort
import dev.thecodewarrior.kotlincpu.common.util.putUByte
import dev.thecodewarrior.kotlincpu.common.util.putUInt
import dev.thecodewarrior.kotlincpu.common.util.putUShort
import java.nio.ByteBuffer

class RAM(val computer: Computer, val size: Int) {
    val buffer: ByteBuffer = ByteBuffer.allocate(size)

    operator fun get(address: UInt): UInt = this[address, DataSize.WORD]
    operator fun set(address: UInt, value: UInt) { this[address, DataSize.WORD] = value }

    operator fun get(address: UInt, dataSize: DataSize): UInt {
        verifyAddress(address, dataSize)

        return when(dataSize) {
            DataSize.BYTE -> buffer.getUByte(address.toInt()).toUInt()
            DataSize.HALF -> buffer.getUShort(address.toInt()).toUInt()
            DataSize.WORD -> buffer.getUInt(address.toInt())
        }
    }

    operator fun set(address: UInt, dataSize: DataSize, value: UInt) {
        verifyAddress(address, dataSize)

        when(dataSize) {
            DataSize.BYTE -> buffer.putUByte(address.toInt(), value.toUByte())
            DataSize.HALF -> buffer.putUShort(address.toInt(), value.toUShort())
            DataSize.WORD -> buffer.putUInt(address.toInt(), value)
        }
    }

    private fun verifyAddress(address: UInt, dataSize: DataSize) {
        if(dataSize.bytes == 1) {
            if(address >= size.toUInt())
                throw IndexOutOfBoundsException("0x${address.toString(16)}")
        } else {
            val range = address until (address + dataSize.bytes.toUInt())
            if(range.last >= size.toUInt())
                throw IndexOutOfBoundsException("0x${range.first.toString(16)} - 0x${range.last.toString(16)}")
        }
    }
}