import advent.Day1
import advent.Day2
import advent.Day3
import advent.Input

fun main() {
    day1()
    day2()
    day3()
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
