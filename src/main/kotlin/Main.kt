import advent.Day1
import advent.Day2
import advent.Input

fun main() {
    day1()
    day2()
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
