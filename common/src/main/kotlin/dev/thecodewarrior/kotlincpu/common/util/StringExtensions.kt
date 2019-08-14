package dev.thecodewarrior.kotlincpu.common.util

fun String.toByteDetectRadix(): Byte {
    val (digits, radix) = this.splitRadix()
    return digits.toByte(radix)
}

fun String.toUByteDetectRadix(): UByte {
    val (digits, radix) = this.splitRadix()
    return digits.toUByte(radix)
}

fun String.toShortDetectRadix(): Short {
    val (digits, radix) = this.splitRadix()
    return digits.toShort(radix)
}

fun String.toUShortDetectRadix(): UShort {
    val (digits, radix) = this.splitRadix()
    return digits.toUShort(radix)
}

fun String.toIntDetectRadix(): Int {
    val (digits, radix) = this.splitRadix()
    return digits.toInt(radix)
}

fun String.toUIntDetectRadix(): UInt {
    val (digits, radix) = this.splitRadix()
    return digits.toUInt(radix)
}

fun String.toLongDetectRadix(): Long {
    val (digits, radix) = this.splitRadix()
    return digits.toLong(radix)
}

fun String.toULongDetectRadix(): ULong {
    val (digits, radix) = this.splitRadix()
    return digits.toULong(radix)
}

fun String.splitRadix(): Pair<String, Int>{
    val negative = this.startsWith("-")
    val number = this.removePrefix("-")
    val radix = if(number.length < 2) 10 else
        when(number.substring(0, 2)) {
            "0x" -> 16
            "0b" -> 2
            else -> 10
        }
    val digits = (if(negative) "-" else "") + (if(radix != 10) number.substring(2) else number)

    return digits to radix
}
