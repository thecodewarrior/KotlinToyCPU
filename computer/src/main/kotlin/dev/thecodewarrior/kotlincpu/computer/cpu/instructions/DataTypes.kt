package dev.thecodewarrior.kotlincpu.computer.cpu.instructions

import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUByte
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUInt
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getULong
import dev.thecodewarrior.kotlincpu.computer.util.extensions.getUShort
import java.nio.ByteBuffer

val ULong.u8: UByte get() = this.toUByte()
val ULong.u16: UShort get() = this.toUShort()
val ULong.u32: UInt get() = this.toUInt()
val ULong.u64: ULong get() = this

val UByte.u64: ULong get() = this.toULong()
val UShort.u64: ULong get() = this.toULong()
val UInt.u64: ULong get() = this.toULong()

val ULong.i8: Byte get() = this.toByte()
val ULong.i16: Short get() = this.toShort()
val ULong.i32: Int get() = this.toInt()
val ULong.i64: Long get() = this.toLong()

val Byte.u64: ULong get() = this.toULong()
val Short.u64: ULong get() = this.toULong()
val Int.u64: ULong get() = this.toULong()
val Long.u64: ULong get() = this.toULong()

val ULong.f32: Float get() = Float.fromBits(this.toInt())
val ULong.f64: Double get() = Double.fromBits(this.toLong())

val Float.u64: ULong get() = this.toRawBits().toULong()
val Double.u64: ULong get() = this.toRawBits().toULong()

// `UShort + UShort = UInt`, so this allows easy conversion back
val UInt.u8: UByte get() = this.toUByte()
val UInt.u16: UShort get() = this.toUShort()
val Int.i8: Byte get() = this.toByte()
val Int.i16: Short get() = this.toShort()

sealed class DataType<T> {
    abstract fun get(buffer: ByteBuffer): T
    abstract fun fromULong(value: ULong): T
    abstract fun toULong(value: T): ULong

    object u8: DataType<UByte>() {
        override fun get(buffer: ByteBuffer): UByte = buffer.getUByte()
        override fun fromULong(value: ULong): UByte = value.u8
        override fun toULong(value: UByte): ULong = value.u64
    }
    object i8: DataType<Byte>() {
        override fun get(buffer: ByteBuffer): Byte = buffer.get()
        override fun fromULong(value: ULong): Byte = value.i8
        override fun toULong(value: Byte): ULong = value.u64
    }

    object u16: DataType<UShort>() {
        override fun get(buffer: ByteBuffer): UShort = buffer.getUShort()
        override fun fromULong(value: ULong): UShort = value.u16
        override fun toULong(value: UShort): ULong = value.u64
    }
    object i16: DataType<Short>() {
        override fun get(buffer: ByteBuffer): Short = buffer.getShort()
        override fun fromULong(value: ULong): Short = value.i16
        override fun toULong(value: Short): ULong = value.u64
    }

    object u32: DataType<UInt>() {
        override fun get(buffer: ByteBuffer): UInt = buffer.getUInt()
        override fun fromULong(value: ULong): UInt = value.u32
        override fun toULong(value: UInt): ULong = value.u64
    }
    object i32: DataType<Int>() {
        override fun get(buffer: ByteBuffer): Int = buffer.getInt()
        override fun fromULong(value: ULong): Int = value.i32
        override fun toULong(value: Int): ULong = value.u64
    }

    object u64: DataType<ULong>() {
        override fun get(buffer: ByteBuffer): ULong = buffer.getULong()
        override fun fromULong(value: ULong): ULong = value.u64
        override fun toULong(value: ULong): ULong = value.u64
    }
    object i64: DataType<Long>() {
        override fun get(buffer: ByteBuffer): Long = buffer.getLong()
        override fun fromULong(value: ULong): Long = value.i64
        override fun toULong(value: Long): ULong = value.u64
    }

    object f32: DataType<Float>() {
        override fun get(buffer: ByteBuffer): Float = buffer.getFloat()
        override fun fromULong(value: ULong): Float = value.f32
        override fun toULong(value: Float): ULong = value.u64
    }
    object f64: DataType<Double>() {
        override fun get(buffer: ByteBuffer): Double = buffer.getDouble()
        override fun fromULong(value: ULong): Double = value.f64
        override fun toULong(value: Double): ULong = value.u64
    }
}
