package dev.thecodewarrior.kotlincpu.common

class Insn(val name: String, val opcode: UShort, vararg payload: Argument<*>) {
    constructor(pseudo: Insn, vararg payload: Argument<*>): this(pseudo.name, pseudo.opcode, *payload)

    val argsHelp: String = run {
        fun needsComma(index: Int): Boolean {
            if(payload[index].type is DataType.asm_const) return false
            if(index + 1 >= payload.size || payload[index + 1].type is DataType.asm_const) return false
            return true
        }

        "${payload.mapIndexed { i, it ->
            it.name + if(needsComma(i)) ", " else " "
        }.joinToString("")}; ${payload.mapIndexed { i, it ->
            (if(it.type is DataType.asm_const) it.name else "${it.type}") + if(needsComma(i)) ", " else " "
        }.joinToString("")}"
    }

    var help: String = "${opcode.toString(16).padStart(4, '0')}: $name $argsHelp"

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