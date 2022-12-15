package advent.day1

import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Test {
    private val input = """
        10
        20
        30
        
        10
        20
    """.trimIndent()

    @Test
    fun `gathers food`() {

        val totals = sumCalories(input)

        assertEquals(totals, listOf(60, 30))
    }

    @Test
    fun `finds largest calories`() {
        val largest = largestCalories(listOf(60, 30))
        assertEquals(60, largest)
    }

    @Test
    fun `sums top three largest`() {
        val topThree = topThree(listOf(100, 3, 300, 4, 5, 200, 6))
        assertEquals(600, topThree)
    }
}
