package advent

typealias Crate = Char
typealias Stack = ArrayDeque<Crate>
typealias StacksByLabel = Map<Char, Stack>

fun Stack.push(crate: Crate) = addLast(crate)
fun Stack.pop() = removeLast()

fun StacksByLabel.moveCrates9000(count: Int, from: Char, to: Char) {
    repeat(count) { this[to]!!.push(this[from]!!.pop()) }
}

fun StacksByLabel.moveCrates9001(count: Int, from: Char, to: Char) {
    val source = this[from]!!
    val sourceLen = source.size
    val target = this[to]!!

    target.addAll(source.subList(sourceLen - count, sourceLen))
    repeat(count) { source.pop() }
}

fun StacksByLabel.topCrates() =
    toSortedMap().values.fold("") { acc, it -> acc + it.last() }

object Day5 {
    fun parseDrawing(drawing: String): StacksByLabel {
        val lines = drawing.split("\n").reversed()
        val stackNames = stackNamesByIndex(lines[0])
        val stacks = stackNames.values.fold(mapOf<Char, Stack>()) { acc, name -> acc + Pair(name, Stack()) }

        lines.drop(1).forEach { extractCratesForEachStack(it, stackNames, stacks) }
        return stacks
    }

    private fun extractCratesForEachStack(line: String, stackNames: Map<Int, Char>, stacks: StacksByLabel) {
        stackNames.entries.forEach { entry ->
            val crate = line[entry.key]
            if (crate != ' ') stacks[entry.value]!!.push(crate)
        }
    }

    private fun stackNamesByIndex(line: String) = line.foldIndexed(mapOf<Int, Char>()) { i, acc, ch ->
        if (ch != ' ') acc + Pair(i, ch) else acc
    }

    /**
     * "move n from A to B"
     */
    fun moveCrates9000(stacksByLabel: StacksByLabel, moves: List<String>) {
        moves.forEach { move ->
            val words = move.split(' ')
            stacksByLabel.moveCrates9000(words[1].toInt(), words[3][0], words[5][0])
        }
    }

    /**
     * "move n from A to B"
     */
    fun moveCrates9001(stacksByLabel: StacksByLabel, moves: List<String>) {
        moves.forEach { move ->
            val words = move.split(' ')
            stacksByLabel.moveCrates9001(words[1].toInt(), words[3][0], words[5][0])
        }
    }
}
