package advent

object Day4 {
    private fun IntRange.containsRange(other: IntRange) =
        other.first >= first && other.last <= last

    private fun IntRange.overlaps(other: IntRange) =
        containsRange(other) || contains(other.first) || contains(other.last)

    fun fullyContained(ranges: Pair<IntRange, IntRange>) =
        ranges.first.containsRange(ranges.second) || ranges.second.containsRange(ranges.first)

    fun parseAssignments(assignmentPair: String) = assignmentPair.split(",").map {
        it.split("-").map {
            it.toInt()
        }.let {
            it[0]..it[1]
        }
    }.let {
        Pair(it[0], it[1])
    }

    fun countFullyContainedRanges(assignments: List<String>) =
        assignments.map { parseAssignments(it) }.count { fullyContained(it) }

    fun overlaps(ranges: Pair<IntRange, IntRange>) =
        ranges.first.overlaps(ranges.second)

    fun countOverlappingRanges(assignments: List<String>) =
        assignments.map { parseAssignments(it) }.count { it.first.overlaps(it.second) || it.second.overlaps(it.first) }
}
