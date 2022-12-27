package advent.day11

typealias ItemList = ArrayDeque<Int>
typealias Receivers = Map<Boolean, Int>

data class Throw(val item: Int, val targetMonkey: Int)


class Monkey(
    val index: Int, items: List<Int>, val operation: Operation, val divisor: Int, val receivers: Receivers) {

    var inspections: Int = 0
    val items = ItemList(items)

    fun inspectNextItem() = inspectItem(items.removeFirst())
    fun takeTurn() = items.map { inspectItem(it) }.also { items.clear() }

    private fun inspectItem(item: Int) = (operation(item) / 3).let {
        inspections++
        Throw(it, receivers[it % divisor == 0]!!)
    }

    companion object {
        /**
         * Parses:
         *
         *     Monkey 1:
         *       Starting items: 54, 65, 75, 74
         *       Operation: new = old + 6
         *       Test: divisible by 19
         *         If true: throw to monkey 2
         *         If false: throw to monkey 0
         */
        fun parse(note: String): Monkey {
            fun List<String>.trailingInt(prefix: String) =
                this.first { it.startsWith(prefix) }.run { split(" ").last().toInt() }

            val lines = note.lines().map { it.trim() }

            val index =
                lines.first { it.startsWith("Monkey") }.run { split(' ', ':')[1].toInt() }
            val items =
                lines.first { it.startsWith("Starting items") }.run { split(": ")[1].split(", ").map { it.toInt() } }
            val operation =
                lines.first { it.startsWith("Operation") }.run { parseOperation(split(": ")[1]) }
            val divisor = lines.trailingInt("Test:")
            val receivers: Receivers = mapOf(
                true to lines.trailingInt("If true"),
                false to lines.trailingInt("If false")
            )

            return Monkey(index, items, operation, divisor, receivers)
        }

        fun parseOperation(operation: String): Operation {
            val (op, arg) = operation.split(" ").takeLast(2)
            return when (op) {
                "+" -> if (arg == "old") PlusSelf() else Plus(arg.toInt())
                "*" -> if (arg == "old") TimesSelf() else Times(arg.toInt())
                else -> throw IllegalArgumentException("Unknown operation: $operation")
            }
        }
    }
}
