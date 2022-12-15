package advent.day3

fun findDuplicateItem(rucksack: String) =
    rucksack.chunked(rucksack.length / 2) { it.toSet() }.reduce { acc, it -> acc.intersect(it) }.first()

private val itemPriorities = run {
    val items = listOf(' ') + ('a'..'z') + ('A'..'Z')
    items.foldIndexed(emptyMap()) { i: Int, acc: Map<Char, Int>, ch: Char ->
        acc.plus(Pair(ch, i))
    }
}

fun priorityFor(item: Char) = itemPriorities[item]!!

fun sumPriorities(rucksacks: Iterable<String>) = rucksacks.sumOf { priorityFor(findDuplicateItem(it)) }

fun findBadge(rucksacks: List<String>) =
    rucksacks.map { it.toSet() }.reduce { acc, it -> acc.intersect(it) }.first()

fun sumBadgePriorities(rucksacks: List<String>) = rucksacks.chunked(3) { priorityFor(findBadge(it)) }.sum()
