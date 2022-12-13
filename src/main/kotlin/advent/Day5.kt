package advent

// Use familiar terms for stacks
typealias Stack<E> = ArrayDeque<E>
typealias StacksByIndex = Map<Int, Stack<Char>>

fun <E> Stack<E>.push(element: E) = addLast(element)
fun <E> Stack<E>.pop() = removeLast()

object Day5 {
    fun parseDrawing(drawing: String): StacksByIndex {
        val lines = drawing.split("\n").reversed()

        val stacks = identifyStacks(lines[0])
        lines.drop(1).forEach { extractCratesForEachStack(it, stacks) }
        return stacks
    }

    private fun extractCratesForEachStack(line: String, stacks: Map<Int, Stack<Char>>) {
        stacks.entries.forEach { entry ->
            val crate = line[entry.key]
            if (crate != ' ') entry.value.push(crate)
        }
    }

    private fun identifyStacks(line: String): StacksByIndex =
        line.foldIndexed(mapOf<Int, Stack<Char>>()) { i, acc, ch ->
            if (ch != ' ') acc + Pair(i, Stack()) else acc
        }
}
