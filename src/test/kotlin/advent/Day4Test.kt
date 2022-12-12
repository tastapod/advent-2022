package advent

import kotlin.test.Test
import kotlin.test.assertEquals

class Day4Test {
    @Test
    fun `identifies fully contained range`() {
        assertEquals(false, Day4.fullyContained(Pair(2..4,6..8)))
        assertEquals(true, Day4.fullyContained(Pair(2..8, 3..7)))
    }

    private val sampleAssignments = """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent().split("\n")

    @Test
    fun `parses pair`() {
        assertEquals(Pair(2..4, 6..8), Day4.parseAssignments("2-4,6-8"))
    }

    @Test
    fun `counts fully contained ranges`() {
        assertEquals(2, Day4.countFullyContainedRanges(sampleAssignments))
    }

    @Test
    fun `detects overlapping ranges`() {
        assertEquals(true, Day4.overlaps(Pair(2..4, 4..8)))
        assertEquals(false, Day4.overlaps(Pair(2..4, 5..8)))
        assertEquals(true, Day4.overlaps(Pair(2..4, 1..2)))
    }

    @Test
    fun `counts overlapping ranges`() {
        assertEquals(4, Day4.countOverlappingRanges(sampleAssignments))
    }
}
