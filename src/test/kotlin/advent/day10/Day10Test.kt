package advent.day10

import advent.Input
import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Test {
    @Test
    fun `parses instruction`() {
        assertEquals(Noop(), Instruction.parse("noop"))
        assertEquals(Addx(3), Instruction.parse("addx 3"))
        assertEquals(Addx(-2), Instruction.parse("addx -2"))
    }

    @Test
    fun `creates time series`() {

        // given
        val program = """
            noop
            addx 3
            addx -5
        """.trimIndent().lines()

        // when
        val series = Series(program)

        // then
        assertEquals(
            listOf(
                State(1..1, 1),
                State(2..3, 1),
                State(4..5, 4),
            ), series.states.take(3))

        assertEquals(1, series.xDuring(1))
        assertEquals(1, series.xDuring(3))
        assertEquals(4, series.xDuring(4))
        assertEquals(4, series.xDuring(5))
        assertEquals(-1, series.xDuring(6))
    }

    @Test
    fun `calculates signal strengths from sample program`() {

        // given
        val input = Input.forDay(10, "-sample").lines()

        // when
        val series = Series(input)

        // then
        assertEquals(1140, series.signalStrengthDuring(60))
        assertEquals(1800, series.signalStrengthDuring(100))

        assertEquals(13140, series.signalStrengthDuring(20..220 step 40))
    }

    @Test
//    @Ignore
    fun `draws pixels`() {
        // given
        val input = Input.forDay(10, "-sample").lines()
        val series = Series(input)

        // when
        val output = series.renderLines()

        // then
        val expected = """
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
        """.trimIndent()

        assertEquals(expected, output)

    }
}
