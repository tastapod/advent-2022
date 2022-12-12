package advent

import kotlin.test.Test
import kotlin.test.assertEquals

class Day3Test {
    private val sampleRucksacks = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent().split("\n")

    @Test
    fun `finds duplicate item`() {
        assertEquals('c', Day3.findDuplicateItem("abcdeABcDE"))
        assertEquals('p', Day3.findDuplicateItem("vJrwpWtwJgWrhcsFMMfFFhFp"))
    }

    @Test
    fun `finds priority`() {
        assertEquals(26, Day3.priorityFor('z'))
        assertEquals(27, Day3.priorityFor('A'))
        assertEquals(52, Day3.priorityFor('Z'))
    }

    @Test
    fun `sums priorities`() {
        assertEquals(157, Day3.sumPriorities(sampleRucksacks))
    }

    @Test
    fun `finds badge`() {
        assertEquals('a', Day3.findBadge(listOf("abc", "BCa", "XCa")))
    }

    @Test
    fun `sums badge priorities`() {
        assertEquals(70, Day3.sumBadgePriorities(sampleRucksacks))
    }
}
