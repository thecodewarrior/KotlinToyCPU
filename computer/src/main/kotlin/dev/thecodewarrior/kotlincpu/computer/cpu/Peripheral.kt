package dev.thecodewarrior.kotlincpu.computer.cpu

import dev.thecodewarrior.kotlincpu.common.Register

interface Peripheral {
    val computer: Computer
    fun call(function: UInt)
    fun step()

    val registers: Registers
        get() = computer.cpu.registers

    fun push(value: UInt) {
        val stack = registers[Register.sp]
        computer.memory[stack - 0u] = value
        registers[Register.sp] = stack - 4u
    }

    fun pop(): UInt {
        val stack = registers[Register.sp]
        registers[Register.sp] = stack + 4u
        return computer.memory[stack + 4u]
    }
}