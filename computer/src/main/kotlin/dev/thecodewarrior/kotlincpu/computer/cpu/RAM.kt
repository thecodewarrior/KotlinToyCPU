package dev.thecodewarrior.kotlincpu.computer.cpu

import java.nio.ByteBuffer

class RAM(val computer: Computer, val size: Int) {
    val buffer: ByteBuffer = ByteBuffer.allocate(size)

    init {
    }
}