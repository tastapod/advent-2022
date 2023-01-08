package advent.day13

import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day13Test {
    @Test
    fun `parses packet`() {
        val packet = parsePacket("[10, 20, 30]")
        assertEquals(TList(1, listOf(TValue(10), TValue(20), TValue(30))), packet)

        val bigger = parsePacket("[10, 20, [], [30, [40, [], 50]]]")
        assertEquals(
            TList(
                1, listOf(
                    TValue(10),
                    TValue(20),
                    TList(2),
                    TList(
                        2, listOf(
                            TValue(30),
                            TList(
                                3, listOf(
                                    TValue(40),
                                    TList(4),
                                    TValue(50)
                                ))
                        )))), bigger)
    }

    @Test
    fun `fails on unexpected character`() {
        assertThrows<IllegalArgumentException> {
            parsePacket("[10, 20, x]")
        }
    }

    private val sampleInput = """
        [1,1,3,1,1]
        [1,1,5,1,1]

        [[1],[2,3,4]]
        [[1],4]

        [9]
        [[8,7,6]]

        [[4,4],4,4]
        [[4,4],4,4,4]

        [7,7,7,7]
        [7,7,7]

        []
        [3]

        [[[]]]
        [[]]

        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]
    """.trimIndent()

    @Test
    fun `compares two lists`() {
        assertTrue(parsePacket("[1,1,3,1,1]") < parsePacket("[1,1,5,1,1]"))
        assertTrue(parsePacket("[[1],[2,3,4]]") < parsePacket("[[1],4]"))
        assertTrue(parsePacket("[[4,4],4,4]") < parsePacket("[[4,4],4,4,4]"))
        assertTrue(parsePacket("[1,[2,[3,[4,[5,6,7]]]],8,9]") > parsePacket("[1,[2,[3,[4,[5,6,0]]]],8,9]"))
    }

    @Test
    fun `finds incorrect pairs`() {
        val pairs = sampleInput.split("\n\n")
        assertEquals(listOf(1, 2, 4, 6), correctPairs(pairs))
    }
}
