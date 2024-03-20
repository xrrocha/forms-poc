
fun String.normalizeSpace(): String =
    trim()
        .split("\\s+".toRegex())
        .joinToString(" ")