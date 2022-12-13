package advent

import kotlin.test.Test
import kotlin.test.assertEquals

class Day5Test {
    @Test
    fun `parses drawing of crates`() {
        val sampleDrawing = """
                [D]    
            [N] [C]    
            [Z] [M] [P]
             1   2   3 
        """.trimIndent()

        val expected = mapOf(
            Pair(1, Stack(listOf('Z', 'N'))),
            Pair(5, Stack(listOf('M', 'C', 'D'))),
            Pair(9, Stack(listOf('P'))),
        )

        assertEquals(expected, Day5.parseDrawing(sampleDrawing))
    }
}
