package dev.thecodewarrior.kotlincpu.computer.util.extensions

import java.nio.ByteBuffer

fun ByteBuffer.putUByte(value: UByte) {
    put(value.toByte())
}

fun ByteBuffer.putUShort(value: UShort) {
    putShort(value.toShort())
}

fun ByteBuffer.putUInt(value: UInt) {
    putInt(value.toInt())
}

fun ByteBuffer.putULong(value: ULong) {
    putLong(value.toLong())
}

fun ByteBuffer.getUByte(): UByte {
    return get().toUByte()
}

fun ByteBuffer.getUShort(): UShort {
    return getShort().toUShort()
}

fun ByteBuffer.getUInt(): UInt {
    return getInt().toUInt()
}

fun ByteBuffer.getULong(): ULong {
    return getLong().toULong()
}
