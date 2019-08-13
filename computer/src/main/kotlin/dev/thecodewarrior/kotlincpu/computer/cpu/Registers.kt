package dev.thecodewarrior.kotlincpu.computer.cpu

class Registers(val cpu: CPU, val count: Int) {
    val values = ULongArray(count)

    operator fun get(register: UByte): ULong = this[register.toInt()]
    operator fun get(register: Int): ULong = values[register]

    operator fun set(register: UByte, value: ULong) {
        this[register.toInt()] = value
    }

    operator fun set(register: Int, value: ULong) {
        values[register] = value
    }
}