import advent.Input
import advent.day1.largestCalories
import advent.day1.sumCalories
import advent.day1.topThree
import advent.day10.Series
import advent.day11.Monkey
import advent.day11.monkeyBusiness
import advent.day11.playRound
import advent.day12.RouteMap
import advent.day13.correctPairs
import advent.day13.decoderKey
import advent.day13.parsePackets
import advent.day14.Cave
import advent.day2.playPart1Game
import advent.day2.playPart2Game
import advent.day3.sumPriorities
import advent.day4.countFullyContainedRanges
import advent.day4.countOverlappingRanges
import advent.day5.moveCrates9000
import advent.day5.moveCrates9001
import advent.day5.parseDrawing
import advent.day5.topCrates
import advent.day6.findStartOfMessage
import advent.day6.findStartOfPacket
import advent.day7.Shell
import advent.day8.Grid
import advent.day9.Segment

fun main() {
    day1()
    day2()
    day3()
    day4()
    day5()
    day6()
    day7()
    day8()
    day9()
    day10()
    day11()
    day12()
    day13()
    day14()
}

private fun day1() {
    val input = Input.forDay(1)
    val part1 = largestCalories(sumCalories(input))
    println("Day 1 part 1: $part1")

    val part2 = topThree(sumCalories(input))
    println("Day 1 part 2: $part2")
}

private fun day2() {
    val input = Input.forDay(2)
    val part1 = playPart1Game(input)
    println("Day 2 part 1: $part1")

    val part2 = playPart2Game(input)
    println("Day 2 part 2: $part2")
}

private fun day3() {
    val input = Input.forDay(3).lines()
    val part1 = sumPriorities(input)
    println("Day 3 part 1: $part1")

    val part2 = advent.day3.sumBadgePriorities(input)
    println("Day 3 part 2: $part2")
}

private fun day4() {
    val input = Input.forDay(4).lines()
    val part1 = countFullyContainedRanges(input)
    println("Day 4 part 1: $part1")

    val part2 = countOverlappingRanges(input)
    println("Day 4 part 2: $part2")
}

private fun day5() {
    val input = Input.forDay(5).split("\n\n")
    val drawing = input[0]
    val moves = input[1].lines()

    var stacks = parseDrawing(drawing)
    moveCrates9000(stacks, moves)
    val part1 = stacks.topCrates()
    println("Day 5 part 1: $part1")

    stacks = parseDrawing(drawing) // reset
    moveCrates9001(stacks, moves)
    val part2 = stacks.topCrates()
    println("Day 5 part 2: $part2")
}

private fun day6() {
    val input = Input.forDay(6)
    val part1 = findStartOfPacket(input)
    println("Day 6 part 1: $part1")

    val part2 = findStartOfMessage(input)
    println("Day 6 part 2: $part2")
}

private fun day7() {
    val input = Input.forDay(7)
    val shell = Shell().executeScript(input)
    val part1 = shell.fs.sumDirectoriesBelowSize(100_000)
    println("Day 7 part 1: $part1")

    val part2 = shell.fs.smallestDirToEnsureSpace(30_000_000).size
    println("Day 7 part 2: $part2")
}

private fun day8() {
    val input = Input.forDay(8).lines()
    val grid = Grid(input)
    val part1 = grid.visibleTrees().count()
    println("Day 8 part 1: $part1")

    val part2 = grid.maxScenicScore()
    println("Day 8 part 2: $part2")
}

private fun day9() {
    val input = Input.forDay(9).lines()
    val segment = Segment()
    segment.move(input)
    val part1 = segment.tailVisited.size
    println("Day 9 part 1: $part1")

    val end = Segment(name = "9")
    val rope = (8 downTo 0).fold(end) { acc, it -> Segment(name = it.toString()).attach(acc) }
    rope.move(input)
    val part2 = end.headVisited.size
    println("Day 9 part 2: $part2")
}

private fun day10() {
    val input = Input.forDay(10).lines()
    val series = Series(input)
    val part1 = series.signalStrengthDuring(20..220 step 40)
    println("Day 10 part 1: $part1")

    val part2 = series.renderLines()
    println("Day 10 part 2:\n$part2") // ZKGRKGRK
}

private fun day11() {
    val input = Input.forDay(11)
    var monkeys = Monkey.parseNotes(input)
    repeat(20) { monkeys.playRound() }
    val part1 = monkeys.monkeyBusiness()
    println("Day 11 part 1: $part1")

    monkeys = Monkey.parseNotes(input)
    repeat(10_000) { monkeys.playRound(worried = false) }
    val part2 = monkeys.monkeyBusiness()
    println("Day 11 part 2: $part2")
}

private fun day12() {
    val input = Input.forDay(12)
    val routeMap = RouteMap.parseMap(input)
    val part1 = routeMap.shortestRoute()
    println("Day 12 part 1: $part1")

    val part2 = routeMap.shortestRouteFromHeight(1)
    println("Day 12 part 2: $part2")
}

private fun day13() {
    val input = Input.forDay(13)
    val part1 = correctPairs(input.split("\n\n")).sum()
    println("Day 13 part 1: $part1")

    val part2 = decoderKey(parsePackets(input.lines()))
    println("Day 13 part 2: $part2")
}

private fun day14() {
    val input = Input.forDay(14)
    val cave1 = Cave.fromScan(input)
    val part1 = cave1.fillWithSand()
    println("Day 14 part 1: $part1")

    val cave2 = Cave.fromScan(input).withFloor()
    val part2 = cave2.fillWithSand()
    println("Day 14 part 2: $part2")
}
