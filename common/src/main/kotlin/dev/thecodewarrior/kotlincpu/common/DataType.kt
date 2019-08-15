package dev.thecodewarrior.kotlincpu.common

import dev.thecodewarrior.kotlincpu.common.util.getUByte
import dev.thecodewarrior.kotlincpu.common.util.getUInt
import dev.thecodewarrior.kotlincpu.common.util.getULong
import dev.thecodewarrior.kotlincpu.common.util.getUShort
import dev.thecodewarrior.kotlincpu.common.util.putUByte
import dev.thecodewarrior.kotlincpu.common.util.putUInt
import dev.thecodewarrior.kotlincpu.common.util.putULong
import dev.thecodewarrior.kotlincpu.common.util.putUShort
import dev.thecodewarrior.kotlincpu.common.util.toByteDetectRadix
import dev.thecodewarrior.kotlincpu.common.util.toIntDetectRadix
import dev.thecodewarrior.kotlincpu.common.util.toLongDetectRadix
import dev.thecodewarrior.kotlincpu.common.util.toShortDetectRadix
import dev.thecodewarrior.kotlincpu.common.util.toUByteDetectRadix
import dev.thecodewarrior.kotlincpu.common.util.toUIntDetectRadix
import dev.thecodewarrior.kotlincpu.common.util.toULongDetectRadix
import dev.thecodewarrior.kotlincpu.common.util.toUShortDetectRadix
import java.nio.ByteBuffer
import java.util.Locale

sealed class DataType<T: Any>(val width: Int) {
    abstract fun put(buffer: ByteBuffer, value: T)
    abstract fun parse(value: String): T?
    abstract fun read(buffer: ByteBuffer): T

    override fun toString(): String {
        return this.javaClass.simpleName
    }

    object reg: DataType<UByte>(1) {
        override fun put(buffer: ByteBuffer, value: UByte) {
            buffer.putUByte(value)
        }

        override fun parse(value: String): UByte? {
            return registers[value.toLowerCase(Locale.ROOT)]
        }

        override fun read(buffer: ByteBuffer): UByte {
            return buffer.getUByte()
        }

        private val registers: Map<String, UByte> = mapOf(
            *(0 .. 15).map { "r$it" to it }.toTypedArray(),
            "pc" to 0xf0
        ).mapValues { it.value.toUByte() }
    }

    object label: DataType<Any>(4) {
        override fun put(buffer: ByteBuffer, value: Any) {
            value as Int
            buffer.putUInt(value.toUInt())
        }

        override fun parse(value: String): Any? {
            return if(value.matches(pattern)) value else null
        }

        override fun read(buffer: ByteBuffer): Any {
            return buffer.getUInt()
        }

        private val pattern = "^[a-zA-Z][a-zA-Z0-9]*$".toRegex()
    }

    object u8: DataType<UByte>(1) {
        override fun put(buffer: ByteBuffer, value: UByte) {
            buffer.putUByte(value)
        }

        override fun parse(value: String): UByte? {
            return if(value.startsWith("#"))
                value.removePrefix("#").toUByteDetectRadix()
            else
                null
        }

        override fun read(buffer: ByteBuffer): UByte {
            return buffer.getUByte()
        }
    }

    object i8: DataType<Byte>(1) {
        override fun put(buffer: ByteBuffer, value: Byte) {
            buffer.put(value)
        }

        override fun parse(value: String): Byte? {
            return if(value.startsWith("#"))
                value.removePrefix("#").toByteDetectRadix()
            else
                null
        }

        override fun read(buffer: ByteBuffer): Byte {
            return buffer.get()
        }
    }

    object u16: DataType<UShort>(2) {
        override fun put(buffer: ByteBuffer, value: UShort) {
            buffer.putUShort(value)
        }

        override fun parse(value: String): UShort? {
            return if(value.startsWith("#"))
                value.removePrefix("#").toUShortDetectRadix()
            else
                null
        }

        override fun read(buffer: ByteBuffer): UShort {
            return buffer.getUShort()
        }
    }

    object i16: DataType<Short>(2) {
        override fun put(buffer: ByteBuffer, value: Short) {
            buffer.putShort(value)
        }

        override fun parse(value: String): Short? {
            return if(value.startsWith("#"))
                value.removePrefix("#").toShortDetectRadix()
            else
                null
        }

        override fun read(buffer: ByteBuffer): Short {
            return buffer.getShort()
        }
    }

    object u32: DataType<UInt>(4) {
        override fun put(buffer: ByteBuffer, value: UInt) {
            buffer.putUInt(value)
        }

        override fun parse(value: String): UInt? {
            return if(value.startsWith("#"))
                value.removePrefix("#").toUIntDetectRadix()
            else
                null
        }

        override fun read(buffer: ByteBuffer): UInt {
            return buffer.getUInt()
        }
    }

    object i32: DataType<Int>(4) {
        override fun put(buffer: ByteBuffer, value: Int) {
            buffer.putInt(value as Int)
        }

        override fun parse(value: String): Int? {
            return if(value.startsWith("#"))
                value.removePrefix("#").toIntDetectRadix()
            else
                null
        }

        override fun read(buffer: ByteBuffer): Int {
            return buffer.getInt()
        }
    }

    object u64: DataType<ULong>(8) {
        override fun put(buffer: ByteBuffer, value: ULong) {
            buffer.putULong(value)
        }

        override fun parse(value: String): ULong? {
            return if(value.startsWith("#"))
                value.removePrefix("#").toULongDetectRadix()
            else
                null
        }

        override fun read(buffer: ByteBuffer): ULong {
            return buffer.getULong()
        }
    }

    object i64: DataType<Long>(8) {
        override fun put(buffer: ByteBuffer, value: Long) {
            buffer.putLong(value as Long)
        }

        override fun parse(value: String): Long? {
            return if(value.startsWith("#"))
                value.removePrefix("#").toLongDetectRadix()
            else
                null
        }

        override fun read(buffer: ByteBuffer): Long {
            return buffer.getLong()
        }
    }

    object f32: DataType<Float>(4) {
        override fun put(buffer: ByteBuffer, value: Float) {
            buffer.putFloat(value as Float)
        }

        override fun parse(value: String): Float? {
            return if(value.startsWith("#"))
                value.removePrefix("#").toFloat()
            else
                null
        }

        override fun read(buffer: ByteBuffer): Float {
            return buffer.getFloat()
        }
    }

    object f64: DataType<Double>(8) {
        override fun put(buffer: ByteBuffer, value: Double) {
            buffer.putDouble(value)
        }

        override fun parse(value: String): Double? {
            return if(value.startsWith("#"))
                value.removePrefix("#").toDouble()
            else
                null
        }

        override fun read(buffer: ByteBuffer): Double {
            return buffer.getDouble()
        }
    }
}
