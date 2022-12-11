import advent.Day1
import advent.Input

fun main() {
    day1()
}

private fun day1() {
    val input = Input.fromResource("/advent/Day1.txt")
    val part1 = Day1.largestCalories(Day1.sumCalories(input))
    println("Day 1 part 1: $part1")

    val part2 = Day1.topThree(Day1.sumCalories(input))
    println("Day 1 part 2: $part2")
}
