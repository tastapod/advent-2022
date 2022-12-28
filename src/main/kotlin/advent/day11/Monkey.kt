package advent.day11

typealias ItemList = ArrayDeque<Long>
typealias Receivers = Map<Boolean, Int>
typealias Monkeys = List<Monkey>

data class Throw(val item: Long, val targetMonkey: Int)

data class Monkey(
    val index: Int, val items: ItemList, val operation: Operation, val divisor: Long, val receivers: Receivers) {

    constructor(index: Int, items: List<Long>, operation: Operation, divisor: Long, receivers: Receivers) :
            this(index, ItemList(items), operation, divisor, receivers)

    var inspections: Int = 0

    fun inspectNextItem() = inspectItem(items.removeFirst())

    fun takeTurn(worried: Boolean = true) = items.map { inspectItem(it, worried) }.also { items.clear() }

    private fun inspectItem(item: Long, worried: Boolean = true) = (operation(item) / if (worried) 3 else 1).let {
        inspections++
        Throw(it, receivers[it % divisor == 0L]!!)
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
        fun parseNote(note: String): Monkey {
            fun List<String>.trailingInt(prefix: String) =
                this.first { it.startsWith(prefix) }.run { split(" ").last().toInt() }

            val lines = note.lines().map { it.trim() }

            return Monkey(

                lines.first { it.startsWith("Monkey") }.run { split(' ', ':')[1].toInt() },
                lines.first { it.startsWith("Starting items") }.run { split(": ")[1].split(", ").map { it.toLong() } },
                lines.first { it.startsWith("Operation") }.run { parseOperation(split(": ")[1]) },
                lines.first { it.startsWith("Test:") }.run { split(" ").last().toLong() },
                mapOf(
                    true to lines.trailingInt("If true"),
                    false to lines.trailingInt("If false")
                ))
        }

        fun parseNotes(notes: String) = notes.split("\n\n").map { parseNote(it) }
            .makeOperationResultsModuloTheLcmOfTestDivisors()

        fun parseOperation(operation: String): Operation {
            val (op, arg) = operation.split(" ").takeLast(2)
            return when (op) {
                "+" -> if (arg == "old") PlusSelf() else Plus(arg.toLong())
                "*" -> if (arg == "old") TimesSelf() else Times(arg.toLong())
                else -> throw IllegalArgumentException("Unknown operation: $operation")
            }
        }
    }
}

fun Monkeys.makeOperationResultsModuloTheLcmOfTestDivisors(): List<Monkey> {
    // from https://stackoverflow.com/a/49722579
    fun gcd(a: Long, b: Long): Long = if (a > 0) gcd(b % a, a) else b
    fun lcm(a: Long, b: Long) = a * b / gcd(a, b)

    val modulus = map { it.divisor }.reduce(::lcm)

    return map { it.copy(operation = Modulo(modulus, it.operation)) }
}

fun Monkeys.playRound(worried: Boolean = true) {
    forEach { monkey ->
        monkey.takeTurn(worried).forEach { this[it.targetMonkey].items.add(it.item) }
    }
}

fun Monkeys.monkeyBusiness() =
    sortedByDescending { it.inspections }.take(2).let { (m1, m2) -> m1.inspections.toLong() * m2.inspections.toLong() }
