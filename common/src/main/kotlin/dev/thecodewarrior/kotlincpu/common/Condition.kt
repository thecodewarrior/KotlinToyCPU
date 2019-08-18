package dev.thecodewarrior.kotlincpu.common

enum class Condition {
    YES { override fun matches(comparison: Int): Boolean = true },
    LT { override fun matches(comparison: Int): Boolean = comparison < 0 },
    LE { override fun matches(comparison: Int): Boolean = comparison <= 0 },
    EQ { override fun matches(comparison: Int): Boolean = comparison == 0 },
    GE { override fun matches(comparison: Int): Boolean = comparison >= 0 },
    GT { override fun matches(comparison: Int): Boolean = comparison > 0 },
    NE { override fun matches(comparison: Int): Boolean = comparison != 0 },
    NO { override fun matches(comparison: Int): Boolean = false };

    abstract fun matches(comparison: Int): Boolean

    companion object {
        val CONDITION_BITS: Int = 3
        val CONDITION_MASK: UShort = ((1 shl CONDITION_BITS) - 1 shl 16 - CONDITION_BITS).toUShort()

        fun decode(opcode: UShort): Condition {
            val bits = (opcode and CONDITION_MASK).toInt() shr (16 - 3)
            return values()[bits]
        }
    }
}