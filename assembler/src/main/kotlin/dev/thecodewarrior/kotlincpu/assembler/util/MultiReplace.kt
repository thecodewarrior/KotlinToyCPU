package dev.thecodewarrior.kotlincpu.assembler.util

fun String.multiReplace(vararg replacements: Pair<String, (MatchResult) -> String>): String {
    val result = StringBuilder()

    val patterns = replacements.associate { it.first.toRegex() to it.second }

    var i = 0
    val nextMatches = mutableMapOf<Regex, MatchResult>()
    patterns.forEach { (pattern, _) ->
        nextMatches[pattern] = pattern.find(this, i) ?: return@forEach
    }

    while(i < this.length && nextMatches.isNotEmpty()) {
        val (pattern, match) = nextMatches.minBy { it.value.range.first } ?: break
        if(match.range.first != i) {
            result.append(this, i, match.range.first)
        }
        result.append(patterns.getValue(pattern)(match))
        i = match.range.last + 1

        nextMatches.forEach { (pattern, match) ->
            if(match.range.first < i) {
                val newMatch = pattern.find(this, i)
                if(newMatch == null)
                    nextMatches.remove(pattern)
                else
                    nextMatches[pattern] = newMatch
            }
        }
    }

    if(i < this.length)
        result.append(this, i, this.length)

    return result.toString()
}