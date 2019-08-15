package dev.thecodewarrior.kotlincpu.common

class Insn(val name: String, val opcode: UShort, val typed: Boolean, vararg payload: DataType<*>) {
    val payload: List<DataType<*>> = mutableListOf(*payload)

    val payloadWidth: Int
        get() = payload.sumBy { it.width }

    fun encoded(condition: Condition): UShort {
        val cmpBits = condition.ordinal shl 16 - Condition.CONDITION_BITS
        return opcode and OPCODE_MASK or cmpBits.toUShort()
    }

    override fun toString(): String {
        return "Insn(name='$name', opcode=$opcode, typed=$typed, payload=$payload)"
    }

    companion object {
        val OPCODE_MASK: UShort = Condition.CONDITION_MASK.inv()

        fun decode(opcode: UShort): Insn? = Instructions.decode(opcode)
    }
}