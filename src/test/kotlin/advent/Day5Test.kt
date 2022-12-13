package advent

import kotlin.test.Test
import kotlin.test.assertEquals

class Day5Test {
    private val sampleDrawing = """
                [D]    
            [N] [C]    
            [Z] [M] [P]
             1   2   3 
        """.trimIndent()

    private fun stack(label: Char, vararg crates: Crate) = Pair(label, Stack(crates.asList()))

    @Test
    fun `parses drawing of crates`() {
        val expected = mapOf(
            stack('1', 'Z', 'N'),
            stack('2', 'M', 'C', 'D'),
            stack('3', 'P'),
        )

        assertEquals(expected, Day5.parseDrawing(sampleDrawing))
    }

    @Test
    fun `moves crates`() {
        val stacks = mapOf(
            stack('1', 'A', 'B'),
            stack('2', 'C'),
        )

        val expected = mapOf(
            stack('1', 'A'),
            stack('2', 'C', 'B'),
        )

        stacks.moveCrates9000(1, '1', '2')

        assertEquals(expected, stacks)
    }

    @Test
    fun `finds top crates`() {
        val stacks = mapOf(
            stack('1', 'A', 'B'),
            stack('2', 'C'),
        )

        assertEquals("BC", stacks.topCrates())
    }

    @Test
    fun `moves sample crates`() {
        val stacksByLabel = Day5.parseDrawing(sampleDrawing)

        val moves = """
            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
        """.trimIndent().split("\n")

        val expected = mapOf(
            stack('1', 'C'),
            stack('2', 'M'),
            stack('3', 'P', 'D', 'N', 'Z')
        )

        Day5.moveCrates9000(stacksByLabel, moves)

        assertEquals(expected, stacksByLabel)
    }
}
