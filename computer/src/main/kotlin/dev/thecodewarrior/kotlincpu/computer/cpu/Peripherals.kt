package dev.thecodewarrior.kotlincpu.computer.cpu

import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap

class Peripherals(val computer: Computer) {
    val peripherals: Int2ObjectMap<Peripheral> = Int2ObjectOpenHashMap()

    operator fun get(pid: UInt): Peripheral? {
        return peripherals[pid.toInt()]
    }

    operator fun set(pid: UInt, peripheral: Peripheral) {
        peripherals[pid.toInt()] = peripheral
    }

    fun step() {
        peripherals.values.forEach { it.step() }
    }
}