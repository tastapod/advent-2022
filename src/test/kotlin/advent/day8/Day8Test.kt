package advent.day8

import kotlin.test.Test
import kotlin.test.assertEquals

class Day8Test {
    @Test
    fun `parses map of trees`() {
        // given
        val input = """
            862
            413
            957
        """.trimIndent().lines()

        // when
        val grid = Grid(input)

        // then
        assertEquals(
            listOf(
                Tree(row = 1, col = 1, height = 8),
                Tree(row = 2, col = 1, height = 4),
                Tree(row = 3, col = 1, height = 9),
            ), grid.lookingDown[0], "downwards")

        assertEquals(
            listOf(
                Tree(row = 3, col = 3, height = 7),
                Tree(row = 2, col = 3, height = 3),
                Tree(row = 1, col = 3, height = 2),
            ), grid.lookingUp[2], "upwards")

        assertEquals(
            listOf(
                Tree(row = 1, col = 1, height = 8),
                Tree(row = 1, col = 2, height = 6),
                Tree(row = 1, col = 3, height = 2),
            ), grid.lookingRight[0], "forwards")

        assertEquals(
            listOf(
                Tree(row = 3, col = 3, height = 7),
                Tree(row = 3, col = 2, height = 5),
                Tree(row = 3, col = 1, height = 9),
            ), grid.lookingLeft[2], "backwards")
    }

    private val sampleInput = """
            30373
            25512
            65332
            33549
            35390
        """.trimIndent().lines()

    @Test
    fun `finds exposed trees in sample`() {
        // given
        val grid = Grid(sampleInput)

        val expected = setOf(
            // edges
            Tree(row = 1, col = 1, height = 3),
            Tree(row = 1, col = 2, height = 0),
            Tree(row = 1, col = 3, height = 3),
            Tree(row = 1, col = 4, height = 7),
            Tree(row = 1, col = 5, height = 3),
            Tree(row = 2, col = 1, height = 2),
            Tree(row = 2, col = 5, height = 2),
            Tree(row = 3, col = 1, height = 6),
            Tree(row = 3, col = 5, height = 2),
            Tree(row = 4, col = 1, height = 3),
            Tree(row = 4, col = 5, height = 9),
            Tree(row = 5, col = 1, height = 3),
            Tree(row = 5, col = 2, height = 5),
            Tree(row = 5, col = 3, height = 3),
            Tree(row = 5, col = 4, height = 9),
            Tree(row = 5, col = 5, height = 0),

            // visible trees
            Tree(row = 2, col = 2, height = 5),
            Tree(row = 2, col = 3, height = 5),
            Tree(row = 3, col = 2, height = 5),
            Tree(row = 3, col = 4, height = 3),
            Tree(row = 4, col = 3, height = 5),
        )

        // when
        val visibleTrees = grid.visibleTrees()

        // then
        assertEquals(expected, visibleTrees)
    }

    @Test
    fun `calculates scenic score`() {
        val grid = Grid(sampleInput)
        assertEquals(4, grid.scenicScore(row=2, col=3))
    }

    @Test
    fun `finds largest scenic score`() {
        val grid = Grid(sampleInput)
        assertEquals(8, grid.maxScenicScore())
    }
}
