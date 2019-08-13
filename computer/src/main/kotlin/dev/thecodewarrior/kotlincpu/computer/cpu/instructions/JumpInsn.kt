package dev.thecodewarrior.kotlincpu.computer.cpu.instructions

import dev.thecodewarrior.kotlincpu.computer.util.extensions.getULong

class JumpInsn {
    companion object {
        val instructions = mutableListOf<Insn>()

        init {
            unconditional()
        }

        private fun unconditional() {
            instructions.add(SimpleInsn("JMP", 0x30u, 8u) { cpu, buffer ->
                val address = buffer.getULong()
                cpu.ctr = address.toUInt()
            })
        }
    }
}