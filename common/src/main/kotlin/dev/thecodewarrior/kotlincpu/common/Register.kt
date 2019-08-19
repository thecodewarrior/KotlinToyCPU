package dev.thecodewarrior.kotlincpu.common

enum class Register {
    r0,
    r1,
    r2,
    r3,
    r4,
    r5,
    r6,
    r7,
    r8,
    r9,
    r10,
    r11,
    r12,
    r13,
    r14,
    r15,
    sp,
    fp,
    lr,
    pc;

    val index = ordinal.toUByte()

    companion object {
        operator fun get(name: String): Register? {
            return values().find { it.name == name.toLowerCase() }
        }

        operator fun get(index: UByte): Register {
            return values()[index.toInt()]
        }
    }
}