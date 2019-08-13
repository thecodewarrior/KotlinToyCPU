package dev.thecodewarrior.kotlincpu.assembler.tokenizer

import java.util.ArrayList
import java.util.regex.Pattern

class Tokenizer(data: String) {

    private var index = 0
    private val tokens = ArrayList<Token>()

    init {
        val initialWhitespacePattern = Pattern.compile("^\\s*")
        val tokenPattern = Pattern.compile("(\"[^\"]*\"|\\S+)(\\s*)")

        val lines = data.split("\\r?\\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (lineNum in lines.indices) {
            val line = lines[lineNum]
            val matcher = tokenPattern.matcher(line)
            val initialWhitespace = initialWhitespacePattern.matcher(line)
            var whitespaceBefore = if (initialWhitespace.find()) initialWhitespace.group() else ""
            while (matcher.find()) {
                tokens.add(Token(matcher.group(1), whitespaceBefore, matcher.group(2), lineNum, matcher.start()))
                whitespaceBefore = matcher.group(2)
            }
            var end = line.indexOf('\r')
            if (end < 0) end = line.indexOf('\n')
            if (end < 0) end = line.length
            tokens.add(Token("\n", lineNum, end))
        }
    }

    fun reset() {
        index = 0
    }

    fun getIndex(): Int {
        return index
    }

    fun setIndex(index: Int) {
        if (index < 0) {
            this.index = 0
        } else if (index > tokens.size) {
            this.index = tokens.size
        } else {
            this.index = index
        }
    }

    fun back(): Tokenizer {
        if (index < 0)
            throw ParseException(tokens[0], "Beginning of file reached")
        setIndex(getIndex() - 1)
        return this
    }

    fun forward(): Tokenizer {
        if (index >= tokens.size)
            throw ParseException(tokens[tokens.size - 1], "End of file reached")
        setIndex(getIndex() + 1)
        return this
    }

    /**
     * Gets the token at the current index.
     * @return The token at the current index
     */
    fun peek(): Token {
        return tokens[index]
    }

    /**
     * Gets the token at the current index and advances to the next token.
     * @return The token at the current index
     */
    fun pop(): Token {
        if (index >= tokens.size)
            throw ParseException(tokens[tokens.size - 1], "End Of File reached")
        return tokens[index++]
    }

    /**
     * @return true if all tokens have been consumed
     */
    fun eof(): Boolean {
        return index == tokens.size
    }
}
