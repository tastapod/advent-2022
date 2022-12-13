import advent.*

fun main() {
    day1()
    day2()
    day3()
    day4()
    day5()
}

private fun day1() {
    val input = Input.forDay(1)
    val part1 = Day1.largestCalories(Day1.sumCalories(input))
    println("Day 1 part 1: $part1")

    val part2 = Day1.topThree(Day1.sumCalories(input))
    println("Day 1 part 2: $part2")
}

private fun day2() {
    val input = Input.forDay(2)
    val part1 = Day2.playPart1Game(input)
    println("Day 2 part 1: $part1")

    val part2 = Day2.playPart2Game(input)
    println("Day 2 part 2: $part2")
}

private fun day3() {
    val input = Input.forDay(3).split("\n")
    val part1 = Day3.sumPriorities(input)
    println("Day 3 part 1: $part1")

    val part2 = Day3.sumBadgePriorities(input)
    println("Day 3 part 2: $part2")
}

private fun day4() {
    val input = Input.forDay(4).split("\n")
    val part1 = Day4.countFullyContainedRanges(input)
    println("Day 4 part 1: $part1")

    val part2 = Day4.countOverlappingRanges(input)
    println("Day 4 part 2: $part2")
}

private fun day5() {
    val input = Input.forDay(5).split("\n\n")
    val drawing = input[0]
    val moves = input[1].split("\n")

    var stacks = Day5.parseDrawing(drawing)
    Day5.moveCrates9000(stacks, moves)
    val part1 = stacks.topCrates()
    println("Day 5 part 1: $part1")

    stacks = Day5.parseDrawing(drawing) // reset
    Day5.moveCrates9001(stacks, moves)
    val part2 = stacks.topCrates()
    println("Day 5 part 2: $part2")
}
