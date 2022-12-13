package advent

import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class Day5Test {
    private val sampleDrawing = """
                [D]    
            [N] [C]    
            [Z] [M] [P]
             1   2   3 
        """.trimIndent()

    @Test
    fun `parses drawing of crates`() {
        val expected = mapOf(
            Pair('1', Stack(listOf('Z', 'N'))),
            Pair('2', Stack(listOf('M', 'C', 'D'))),
            Pair('3', Stack(listOf('P'))),
        )

        assertEquals(expected, Day5.parseDrawing(sampleDrawing))
    }

    @Test
    fun `moves crates`() {
        val stacksByIndex = mapOf(
            Pair('1', Stack(listOf('A', 'B'))),
            Pair('2', Stack(listOf('C'))),
        )

        val expected = mapOf(
            Pair(3, Stack(listOf('A'))),
            Pair(7, Stack(listOf('C', 'B'))),
        )

        assertEquals(expected, Day5.moveCrates(1, '1', '2'))
    }

    @Test
    @Ignore
    fun `moves sample crates`() {
        val crateMaps = Day5.parseDrawing(sampleDrawing)
        val moves = """
            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
        """.trimIndent()
    }
}
