package dev.thecodewarrior.kotlincpu.common

class Insn(val name: String, val opcode: UShort, vararg payload: Argument<*>) {
    constructor(pseudo: Insn, vararg payload: Argument<*>): this(pseudo.name, pseudo.opcode, *payload)
    val payload: List<Argument<*>> = mutableListOf(*payload)

    val payloadWidth: Int
        get() = payload.sumBy { it.type.width }

    fun encoded(condition: Condition): UShort {
        val cmpBits = condition.ordinal shl 16 - Condition.CONDITION_BITS
        return opcode and OPCODE_MASK or cmpBits.toUShort()
    }

    override fun toString(): String {
        return "Insn(name='$name', opcode=$opcode, payload=$payload)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Insn) return false

        if (name != other.name) return false
        if (opcode != other.opcode) return false
        if (payload != other.payload) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + opcode.hashCode()
        result = 31 * result + payload.hashCode()
        return result
    }

    companion object {
        val OPCODE_MASK: UShort = Condition.CONDITION_MASK.inv()

        fun decode(opcode: UShort): Insn? = Instructions.decode(opcode)
    }
}