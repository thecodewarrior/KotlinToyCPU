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

// =========================================================

fun ByteBuffer.putUByte(index: Int, value: UByte) {
    put(index, value.toByte())
}
fun ByteBuffer.putUShort(index: Int, value: UShort) {
    putShort(index, value.toShort())
}
fun ByteBuffer.putUInt(index: Int, value: UInt) {
    putInt(index, value.toInt())
}
fun ByteBuffer.putULong(index: Int, value: ULong) {
    putLong(index, value.toLong())
}

// =====================================================================================================================

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

// =========================================================

fun ByteBuffer.getUByte(index: Int): UByte {
    return get(index).toUByte()
}
fun ByteBuffer.getUShort(index: Int): UShort {
    return getShort(index).toUShort()
}
fun ByteBuffer.getUInt(index: Int): UInt {
    return getInt(index).toUInt()
}
fun ByteBuffer.getULong(index: Int): ULong {
    return getLong(index).toULong()
}


var ByteBuffer.pos: Int
    get() = this.position()
    set(value) {
        this.position(value)
    }
