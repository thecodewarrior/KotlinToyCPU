package dev.thecodewarrior.kotlincpu.computer.cpu

class Registers(val cpu: CPU, val count: Int) {
    val values = ULongArray(count)
}