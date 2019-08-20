package dev.thecodewarrior.kotlincpu.common

enum class DataSize(val token: String, val bytes: Int) {
    BYTE("'b", 1),
    HALF("'h", 2),
    WORD("'w", 4);

    override fun toString(): String {
        return token
    }
}
