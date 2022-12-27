package advent.day11

class Monkeys(notes: String) {
    private val monkeys: List<Monkey>

    init {
        monkeys = notes.split("\n\n").map { Monkey.parse(it) }
    }

    fun playRound() {
        monkeys.forEach { monkey ->
            monkey.takeTurn().forEach { monkeys[it.targetMonkey].items.add(it.item) }
        }
    }

    fun at(index: Int) = monkeys[index]
    fun monkeyBusiness() =
        monkeys.sortedByDescending { it.inspections }.take(2).let { (m1, m2) -> m1.inspections * m2.inspections }
}
