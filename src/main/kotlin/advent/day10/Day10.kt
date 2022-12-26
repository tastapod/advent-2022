package advent.day10

data class State(val range: IntRange, val x: Int)

sealed interface Instruction {
    val duration: Int
    fun execute(x: Int): Int

    companion object {
        fun parse(instruction: String) = when {
            instruction == "noop" -> Noop()
            instruction.startsWith("addx ") -> Addx(instruction.substring(5).toInt())
            else -> throw IllegalArgumentException("Unknown command: $instruction")
        }

        fun parse(program: List<String>) = program.map { parse(it) }
    }
}

data class Noop(override val duration: Int = 1) : Instruction {
    override fun execute(x: Int) = x
}

data class Addx(val increment: Int, override val duration: Int = 2) : Instruction {
    override fun execute(x: Int) = x + increment
}

class Series(lines: List<String>) {
    companion object {
        const val NUM_COLS = 40
        const val NUM_ROWS = 6

    }

    internal val states: List<State>

    init {
        val program = Instruction.parse(lines)
        var x = 1
        var nextCycle = 1
        states = program.map { instruction ->
            State(
                nextCycle until (nextCycle + instruction.duration).also { nextCycle = it },
                x = x.also { x = instruction.execute(x) })
        } + State(nextCycle..Int.MAX_VALUE, x)
    }

    fun xDuring(cycle: Int) = states.first() { cycle in it.range }.x

    private fun xSpriteDuring(cycle: Int) = xDuring(cycle).let { (it - 1)..(it + 1) }

    fun signalStrengthDuring(cycle: Int) = cycle * xDuring(cycle)

    fun signalStrengthDuring(cycle: Iterable<Int>) = cycle.sumOf { signalStrengthDuring(it) }

    fun renderLines() = (0 until NUM_ROWS).joinToString("\n") { row ->
        (0 until NUM_COLS).joinToString("") { col ->
            if (isLit(row, col)) "#" else "."
        }
    }

    fun isLit(row: Int, col: Int) = col in xSpriteDuring(row * NUM_COLS + col + 1)
}
