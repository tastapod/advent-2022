package advent

typealias Crate = Char
typealias Stack = ArrayDeque<Crate>

fun Stack.push(crate: Crate) = addLast(crate)
fun Stack.pop() = removeLast()

object Day5 {
    fun parseDrawing(drawing: String): Map<Char, Stack> {
        val lines = drawing.split("\n").reversed()
        val stackNames = stackNamesByIndex(lines[0])
        val stacks = stackNames.values.fold(mapOf<Char, Stack>()) { acc, name -> acc + Pair(name, Stack()) }

        lines.drop(1).forEach { extractCratesForEachStack(it, stackNames, stacks) }
        return stacks
    }

    private fun extractCratesForEachStack(line: String, stackNames: Map<Int, Char>, stacks: Map<Char, Stack>) {
        stackNames.entries.forEach { entry ->
            val crate = line[entry.key]
            if (crate != ' ') stacks[entry.value]!!.push(crate)
        }
    }

    private fun stackNamesByIndex(line: String) = line.foldIndexed(mapOf<Int, Char>()) { i, acc, ch ->
        if (ch != ' ') acc + Pair(i, ch) else acc
    }

    fun moveCrates(count: Int, from: Char, to: Char): Map<Int, Stack> {
        TODO("Not yet implemented")
    }
}
