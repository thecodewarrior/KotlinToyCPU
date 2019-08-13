package dev.thecodewarrior.kotlincpu.computer.cpu.instructions

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
