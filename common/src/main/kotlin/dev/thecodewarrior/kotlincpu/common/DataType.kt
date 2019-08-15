@file:Suppress("ClassName")

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

sealed class DataType<T: Any>(val name: String, val width: Int) {
    abstract fun put(buffer: ByteBuffer, value: T)
    abstract fun parse(value: String): T?
    abstract fun read(buffer: ByteBuffer): T

    override fun toString(): String {
        return "${this.javaClass.simpleName}('$name')"
    }

    class dynamic(name: String): DataType<Any>(name, 0) {
        override fun put(buffer: ByteBuffer, value: Any) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun parse(value: String): Any? {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun read(buffer: ByteBuffer): Any {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        companion object {
            fun parse(value: String): Any? {
                TODO("not implemented")
            }
        }
    }

    open class reg(name: String): DataType<UByte>(name, 1) {
        override fun put(buffer: ByteBuffer, value: UByte) {
            buffer.putUByte(value)
        }

        override fun parse(value: String): UByte? {
            return registers[value.toLowerCase(Locale.ROOT)]
        }

        override fun read(buffer: ByteBuffer): UByte {
            return buffer.getUByte()
        }

        companion object: reg("") {
            val registers: Map<String, UByte> = mapOf(
                *(0 .. 15).map { "r$it" to it }.toTypedArray(),
                "pc" to 0xf0
            ).mapValues { it.value.toUByte() }
        }
    }

    open class label(name: String): DataType<Any>(name, 4) {
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

        companion object: label("") {
            val pattern = "^[a-zA-Z][a-zA-Z0-9]*$".toRegex()
        }
    }

    open class u8(name: String): DataType<UByte>(name, 1) {
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
        companion object: u8("")
    }

    open class i8(name: String): DataType<Byte>(name, 1) {
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

        companion object: i8("")
    }

    open class u16(name: String): DataType<UShort>(name, 2) {
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

        companion object: u16("")
    }

    open class i16(name: String): DataType<Short>(name, 2) {
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

        companion object: i16("")
    }

    open class u32(name: String): DataType<UInt>(name, 4) {
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

        companion object: u32("")
    }

    open class i32(name: String): DataType<Int>(name, 4) {
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

        companion object: i32("")
    }

    open class u64(name: String): DataType<ULong>(name, 8) {
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

        companion object: u64("")
    }

    open class i64(name: String): DataType<Long>(name, 8) {
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

        companion object: i64("")
    }

    open class f32(name: String): DataType<Float>(name, 4) {
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

        companion object: f32("")
    }

    open class f64(name: String): DataType<Double>(name, 8) {
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

        companion object: f64("")
    }
}