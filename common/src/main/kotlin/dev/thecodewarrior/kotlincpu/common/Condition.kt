package dev.thecodewarrior.kotlincpu.common

enum class Condition(private val _matches: (Int) -> Boolean) {
    YES({ true }),
    LT({ it < 0 }),
    LE({ it <= 0 }),
    EQ({ it == 0 }),
    GE({ it >= 0 }),
    GT({ it > 0 }),
    NE({ it != 0 }),
    NO({ false });

    fun matches(comparison: Int): Boolean = _matches(comparison)

    companion object {
        val CONDITION_BITS: Int = 3
        val CONDITION_MASK: UShort = ((1 shl CONDITION_BITS) - 1 shl 16 - CONDITION_BITS).toUShort()

        fun decode(opcode: UShort): Condition {
            val bits = (opcode and CONDITION_MASK).toInt() shr (16 - 3)
            return values()[bits]
        }
    }
}