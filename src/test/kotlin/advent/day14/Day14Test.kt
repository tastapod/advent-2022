package advent.day14

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day14Test {
    @Test
    fun `builds map of points`() {
        var cave = Cave.fromPaths(listOf(P(498, 4), P(498, 6), P(496, 6)))
        assertTrue(P(498, 5) in cave)
        assertTrue(P(497, 6) in cave)

        cave = Cave.fromPaths(
            listOf(P(498, 4), P(498, 6), P(496, 6)),
            listOf(P(503, 4), P(502, 4), P(502, 9), P(494, 9)))
        assertTrue(P(499, 9) in cave)
    }

    private val sampleScan = """
            498,4 -> 498,6 -> 496,6
            503,4 -> 502,4 -> 502,9 -> 494,9
        """.trimIndent()

    @Test
    fun `parses scan`() {
        val cave = Cave.fromScan(sampleScan)

        assertEquals(
            Cave.fromPaths(
                listOf(P(498, 4), P(498, 6), P(496, 6)),
                listOf(P(503, 4), P(502, 4), P(502, 9), P(494, 9))),
            cave)
    }

    @Test
    fun `drops sand`() {
        // given
        val cave = Cave.fromScan(sampleScan)
        assertFalse(P(500, 8) in cave)

        // when
        cave.dropSand()

        // then
        assertEquals(Obstacle.Sand, cave[P(500, 8)])
    }

    @Test
    fun `fills with sand`() {
        val cave = Cave.fromScan(sampleScan)

        // then
        assertEquals(24, cave.fillWithSand())
    }

    @Test
    fun `fills with sand with floor`() {
        val cave = Cave.fromScan(sampleScan).withFloor()
        assertEquals(93, cave.fillWithSand())
    }
}
