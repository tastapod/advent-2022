package advent.day13

sealed class TExpr : Comparable<TExpr>

data class TValue(val value: Int) : TExpr() {
    override operator fun compareTo(other: TExpr) = when (other) {
        is TValue -> value.compareTo(other.value)
        is TList -> toTList().compareTo(other)
    }

    fun toTList() = TList(0, listOf(this))
}

data class TList(val depth: Int, val ts: List<TExpr> = emptyList()) : TExpr() {
    override operator fun compareTo(other: TExpr): Int {
        when (other) {
            is TValue -> return compareTo(other.toTList())

            is TList -> {
                val thisIter = ts.iterator()
                val thatIter = other.ts.iterator()

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

typealias Packet = TList

fun parsePacket(line: String) = parseList(1, line.toMutableList())

fun parsePackets(lines: List<String>) = lines.filter { it.isNotEmpty() }.map { parsePacket(it) }

private fun parseValue(chars: MutableList<Char>) = TValue(
    generateSequence {
        if (chars.first().isDigit()) chars.removeFirst() else null
    }.toList().toCharArray().concatToString().toInt()
)

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

val DIVIDERS = """
    [[2]]
    [[6]]
    """.trimIndent().split("\n").map { parsePacket(it) }.toSet()

fun dividerPositions(packets: List<Packet>) =
    (packets + DIVIDERS).asSequence().sorted().withIndex().filter { packet -> packet.value in DIVIDERS }.map { it.index + 1 }

fun decoderKey(packets: List<Packet>) =
    dividerPositions(packets).reduce { acc, i -> acc * i }
