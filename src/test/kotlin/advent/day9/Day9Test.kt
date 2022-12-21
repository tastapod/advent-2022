package advent.day9

import kotlin.test.Test
import kotlin.test.assertEquals

class Day9Test {
    @Test
    fun `tail does not move if close to head`() {
        // Given
        val tail = Pos(2, 2)

        // Within 1 space is a no-op
        assertEquals(tail, tail.follow(Pos(2, 2)))
        assertEquals(tail, tail.follow(Pos(2, 1)))
        assertEquals(tail, tail.follow(Pos(2, 3)))
        assertEquals(tail, tail.follow(Pos(1, 2)))
        assertEquals(tail, tail.follow(Pos(3, 2)))
    }

    @Test
    fun `tail moves towards head in a line`() {
        // Given
        val tail = Pos(2, 2)

        // Then
        assertEquals(Pos(3, 2), tail.follow(Pos(4, 2)))
        assertEquals(Pos(1, 2), tail.follow(Pos(0, 2)))

        assertEquals(Pos(2, 3), tail.follow(Pos(2, 4)))
        assertEquals(Pos(2, 1), tail.follow(Pos(2, 0)))
    }

    @Test
    fun `tail moves towards head diagonally`() {
        // Given
        val tail = Pos(2, 2)

        // Then

        // head was at (1, 3)
        assertEquals(Pos(1, 3), tail.follow(Pos(1, 4)))
        assertEquals(Pos(1, 3), tail.follow(Pos(0, 3)))

        // head was at (3, 3)
        assertEquals(Pos(3, 3), tail.follow(Pos(4, 3)))
        assertEquals(Pos(3, 3), tail.follow(Pos(3, 4)))

        // head was at (1, 1)
        assertEquals(Pos(1, 1), tail.follow(Pos(1, 0)))
        assertEquals(Pos(1, 1), tail.follow(Pos(0, 1)))

        // head was at (3, 1)
        assertEquals(Pos(3, 1), tail.follow(Pos(4, 1)))
        assertEquals(Pos(3, 1), tail.follow(Pos(3, 0)))
    }

    @Test
    fun `moves rope`() {
        val rope = Rope(Pos(0, 0))

        rope.move("R 4")
        assertEquals(Pos(4, 0), rope.head)
        assertEquals(Pos(3, 0), rope.tail)

        rope.move("U 4")
        assertEquals(Pos(4, 4), rope.head)
        assertEquals(Pos(4, 3), rope.tail)
    }

    private val sampleRoute = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent().lines()

    @Test
    fun `follows sample route`() {
        val rope = Rope()
        rope.move(sampleRoute)

        assertEquals(Pos(2, 2), rope.head)
        assertEquals(Pos(1, 2), rope.tail)

        assertEquals(13, rope.tailVisited.size)
    }
}

