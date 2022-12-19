package advent.day1

fun sumCalories(input: String) = input.split("\n\n") // into blocks
    .map { it.lines() } // into lists of strings
    .map { it.fold(0) { acc, str -> acc + str.toInt() } }

fun largestCalories(totals: List<Int>) = totals.max()

fun topThree(totals: List<Int>) = totals.sortedDescending().take(3).sum()
