package dev.thecodewarrior.kotlincpu.assembler.tokenizer

class ParseException: RuntimeException {
    constructor(token: Token): super(formatMessage("", token)) {}
    constructor(token: Token, message: String): super(formatMessage(message, token)) {}
    constructor(token: Token, message: String, cause: Throwable): super(formatMessage(message, token), cause) {}
    constructor(token: Token, cause: Throwable): super(formatMessage("", token), cause) {}

    companion object {

        fun formatMessage(message: String, token: Token): String {
            return String.format("%s at %d:%d", message, token.line + 1, token.column + 1)
        }
    }
}
