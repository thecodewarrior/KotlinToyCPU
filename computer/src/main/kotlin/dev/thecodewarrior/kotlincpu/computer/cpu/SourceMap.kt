package dev.thecodewarrior.kotlincpu.computer.cpu

import dev.thecodewarrior.kotlincpu.common.util.getUShort
import java.io.File
import java.nio.ByteBuffer

class SourceMap {
    val root: File
    val locations = mutableMapOf<Int, Location>()

    constructor() {
        root = File(".")
    }

    constructor(file: File) {
        val buffer = ByteBuffer.wrap(file.readBytes())

        val fileCount = buffer.getUShort().toInt()
        val files = (0 until fileCount).map {
            val length = buffer.getUShort().toInt()
            val stringBytes = ByteArray(length)
            buffer.get(stringBytes)
            return@map String(stringBytes)
        }

        while(buffer.hasRemaining()) {
            val address = buffer.getInt()
            val file = files[buffer.getUShort().toInt()]
            val line = buffer.getUShort().toInt()
            if(address in locations)
                error("duplicate address ${address.toString(16)}")
            locations[address] = Location(file, line)
        }

        root = file.parentFile
    }

    operator fun get(address: Int): Location? {
        return locations[address]
    }
}

data class Location(val file: String, val line: Int)