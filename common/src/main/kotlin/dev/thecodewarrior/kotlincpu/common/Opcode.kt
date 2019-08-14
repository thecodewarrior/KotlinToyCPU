package dev.thecodewarrior.kotlincpu.common

class Opcode(val name: String, val opcode: UShort, val typed: Boolean, vararg payload: DataType<*>) {
    val payload: List<DataType<*>> = mutableListOf(*payload)

    val payloadWidth: Int
        get() = payload.sumBy { it.width }
}