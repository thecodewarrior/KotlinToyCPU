package dev.thecodewarrior.kotlincpu.assembler.tokenizer

import java.util.Objects

class Token(val value: String, val whitespaceBefore: String?, val whitespaceAfter: String?, val line: Int, val column: Int) {
    constructor(value: String, line: Int, column: Int) : this(value, null, null, line, column)

    override fun toString(): String {
        return value
    }

    fun toQuotedString(): String {
        if (value.length < 2 || value[0] != '"' || value[value.length - 1] != '"')
            throw ParseException(this, "`$value` is not a double quoted string")
        return value.substring(1, value.length - 1)
    }

    fun toInt(): Int {
        try {
            return Integer.parseInt(value)
        } catch (e: NumberFormatException) {
            throw ParseException(this, e)
        }
    }

    fun toFloat(): Float {
        try {
            return java.lang.Float.parseFloat(value)
        } catch (e: NumberFormatException) {
            throw ParseException(this, e)
        }
    }

    fun expectLine() {
        expect("\n")
    }

    fun expect(token: String) {
        if (!test(token)) throw ParseException(this, "Expected `$token`, found `$value`")
    }

    fun testLine(): Boolean {
        return test("\n")
    }

    fun test(token: String): Boolean {
        return token == value
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Token) return false
        val token = o as Token?
        return line == token!!.line &&
            column == token.column &&
            value == token.value
    }

    override fun hashCode(): Int {
        return Objects.hash(value, line, column)
    }
}
