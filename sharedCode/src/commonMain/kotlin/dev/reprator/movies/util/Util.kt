package dev.reprator.movies.util

fun String.unescapeUnicode(): String {
    return this.replace("\\\\u([0-9A-Fa-f]{4})".toRegex()) { matchResult ->
        val hexValue = matchResult.groupValues[1]
        val intValue = hexValue.toInt(radix = 16)
        intValue.toChar().toString()
    }
}
