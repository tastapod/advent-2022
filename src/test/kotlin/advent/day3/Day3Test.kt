package advent.day3

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
        assertEquals('c', findDuplicateItem("abcdeABcDE"))
        assertEquals('p', findDuplicateItem("vJrwpWtwJgWrhcsFMMfFFhFp"))
    }

    @Test
    fun `finds priority`() {
        assertEquals(26, priorityFor('z'))
        assertEquals(27, priorityFor('A'))
        assertEquals(52, priorityFor('Z'))
    }

    @Test
    fun `sums priorities`() {
        assertEquals(157, sumPriorities(sampleRucksacks))
    }

    @Test
    fun `finds badge`() {
        assertEquals('a', findBadge(listOf("abc", "BCa", "XCa")))
    }

    @Test
    fun `sums badge priorities`() {
        assertEquals(70, sumBadgePriorities(sampleRucksacks))
    }
}
