package dev.thecodewarrior.kotlincpu.assembler.util

import java.nio.ByteBuffer

private val bufferPool: Pool<ByteBuffer> = Pool { ByteBuffer.allocate(0x10000) }

fun buildBytes(build: (ByteBuffer) -> Unit): ByteArray {
    return bufferPool.use { buffer ->
        buffer.rewind()

        build(buffer)

        val pos = buffer.position()
        val array = ByteArray(pos)

        buffer.rewind()
        buffer.get(array, 0, pos)

        return@use array
    }
}