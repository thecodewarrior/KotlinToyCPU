package dev.thecodewarrior.kotlincpu.computer.cpu

import dev.thecodewarrior.kotlincpu.common.Register

class Registers(val cpu: CPU, val count: Int) {
    val values = UIntArray(count)

    operator fun get(register: Register): UInt = this[register.ordinal]
    operator fun get(register: UByte): UInt = this[register.toInt()]
    operator fun get(register: Int): UInt = values[register]

    operator fun set(register: Register, value: UInt) {
        this[register.ordinal] = value
    }

    operator fun set(register: UByte, value: UInt) {
        this[register.toInt()] = value
    }

    operator fun set(register: Int, value: UInt) {
        values[register] = value
    }
}