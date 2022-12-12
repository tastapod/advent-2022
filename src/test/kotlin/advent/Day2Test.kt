package advent

import kotlin.test.Test
import kotlin.test.assertEquals

class Day2Test {
    @Test
    fun `calculates score`() {
        assertEquals(Result.Lose, Play.Rock.vs(Play.Paper))
        assertEquals(Result.Draw, Play.Paper.vs(Play.Paper))

        assertEquals(6+2, Day2.score(Play.Paper, Play.Rock))
        assertEquals(3+1, Day2.score(Play.Rock, Play.Rock))
    }

    @Test
    fun `parses play`() {
        assertEquals(Play.Rock, Play.fromChar('X'))
        assertEquals(Play.Paper, Play.fromChar('Y'))
        assertEquals(Play.Scissors, Play.fromChar('Z'))

        assertEquals(Play.Paper, Play.fromChar('B'))
    }

    @Test
    fun `calculates score from chars`() {
        assertEquals(6+2, Day2.score('B', 'X'))
        assertEquals(3+1, Day2.score('A', 'X'))
    }

    private val sampleGame = """
            A Y
            B X
            C Z
        """.trimIndent()

    @Test
    fun `scores part 1 game`() {
        assertEquals(15, Day2.playPart1Game(sampleGame))
    }

    @Test
    fun `works out what to play`() {
        assertEquals(Play.Rock, Result.Win.against(Play.Scissors))
    }

    @Test
    fun `scores part 2 game`() {
        assertEquals(12, Day2.playPart2Game(sampleGame))
    }
}
