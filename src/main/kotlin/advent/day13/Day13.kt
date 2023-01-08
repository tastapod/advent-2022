package advent.day13

sealed class TExpr {
    abstract operator fun compareTo(that: TExpr): Int
}

data class TValue(val value: Int) : TExpr() {
    override operator fun compareTo(that: TExpr) = when (that) {
        is TValue -> value.compareTo(that.value)
        is TList -> toTList().compareTo(that)
    }
    fun toTList() = TList(0, listOf(this))
}

data class TList(val depth: Int, val ts: List<TExpr> = emptyList()) : TExpr() {
    override operator fun compareTo(that: TExpr): Int {
        when (that) {
            is TValue -> return compareTo(that.toTList())

            is TList -> {
                val thisIter = ts.iterator()
                val thatIter = that.ts.iterator()

                while (thisIter.hasNext()) {
                    val mine = thisIter.next()
                    if (!thatIter.hasNext()) {
                        // I am longer
                        return 1
                    } else {
                        val yours = thatIter.next()
                        mine.compareTo(yours).let {
                            if (it != 0) return it
                        }
                    }
                }

                // ran out of elements
                return if (thatIter.hasNext()) -1 else 0
            }
        }
    }
}

fun parsePacket(line: String) = parseList(1, line.toMutableList())

private fun parseValue(chars: MutableList<Char>) = TValue(
    generateSequence {
        if (chars.first().isDigit()) chars.removeFirst() else null
    }.toList().toCharArray().concatToString().toInt())

private fun parseList(depth: Int, chars: MutableList<Char>): TList {
    val contents = mutableListOf<TExpr>()
    chars.removeFirst()

    while (true) {
        when (val next = chars.first()) {
            ']' -> return TList(depth, contents.toList()).also { chars.removeFirst() } // done
            in '0'..'9' -> contents.add(parseValue(chars))
            '[' -> contents.add(parseList(depth + 1, chars))
            ',', ' ' -> chars.removeFirst()
            else -> throw IllegalArgumentException("Unexpected character: $next")
        }
    }
}

fun correctPairs(pairs: List<String>) = pairs.withIndex().filter { pair ->
    val (left, right) = pair.value.split("\n")
    parsePacket(left) < parsePacket(right)
}.map { it.index + 1 }
