@file:Suppress("ClassName")

package dev.thecodewarrior.kotlincpu.common

class Argument<T: Any>(val name: String, val type: DataType<T>) {
    override fun toString(): String {
        return "$type('$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Argument<*>) return false

        if (name != other.name) return false
        if (type != other.type) return false
        if (other.javaClass != this.javaClass) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + javaClass.hashCode()
        return result
    }
}

object Arguments {
    fun asm_const(constant: String): Argument<Unit> = Argument(constant, DataType.asm_const(constant))
    fun reg(name: String): Argument<Register> = Argument(name, DataType.reg)
    fun label(name: String): Argument<Any> = Argument(name, DataType.label)
    fun u8(name: String): Argument<UByte> = Argument(name, DataType.u8)
    fun i8(name: String): Argument<Byte> = Argument(name, DataType.i8)
    fun u16(name: String): Argument<UShort> = Argument(name, DataType.u16)
    fun i16(name: String): Argument<Short> = Argument(name, DataType.i16)
    fun u32(name: String): Argument<UInt> = Argument(name, DataType.u32)
    fun i32(name: String): Argument<Int> = Argument(name, DataType.i32)
    fun u64(name: String): Argument<ULong> = Argument(name, DataType.u64)
    fun i64(name: String): Argument<Long> = Argument(name, DataType.i64)
    fun f32(name: String): Argument<Float> = Argument(name, DataType.f32)
    fun f64(name: String): Argument<Double> = Argument(name, DataType.f64)
}
