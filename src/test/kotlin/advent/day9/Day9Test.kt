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
        assertEquals(Pos(1, 3), tail.follow(Pos(0, 4)))

        // head was at (3, 3)
        assertEquals(Pos(3, 3), tail.follow(Pos(4, 3)))
        assertEquals(Pos(3, 3), tail.follow(Pos(3, 4)))
        assertEquals(Pos(3, 3), tail.follow(Pos(4, 4)))

        // head was at (1, 1)
        assertEquals(Pos(1, 1), tail.follow(Pos(1, 0)))
        assertEquals(Pos(1, 1), tail.follow(Pos(0, 1)))
        assertEquals(Pos(1, 1), tail.follow(Pos(0, 0)))

        // head was at (3, 1)
        assertEquals(Pos(3, 1), tail.follow(Pos(4, 1)))
        assertEquals(Pos(3, 1), tail.follow(Pos(3, 0)))
        assertEquals(Pos(3, 1), tail.follow(Pos(4, 0)))
    }

    @Test
    fun `moves rope`() {
        val segment = Segment(Pos(0, 0))

        segment.move("R 4")
        assertEquals(Pos(4, 0), segment.head)
        assertEquals(Pos(3, 0), segment.tail)

        segment.move("U 4")
        assertEquals(Pos(4, 4), segment.head)
        assertEquals(Pos(4, 3), segment.tail)
    }

    private val smallRoute = """
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
    fun `follows small sample route`() {
        // given
        val segment = Segment(Pos(0, 0))

        // when
        segment.move(smallRoute)

        // then
        assertEquals(Pos(2, 2), segment.head)
        assertEquals(Pos(1, 2), segment.tail)

        assertEquals(13, segment.tailVisited.size)
    }

    @Test
    fun `moves successive knots`() {
        // given
        val segment1 = Segment(Pos(0, 0))
        val segment2 = Segment(Pos(0, 0)).attach(segment1)
        val segment3 = Segment(Pos(0, 0)).attach(segment2)

        // when
        segment3.move("R 6")

        // then
        assertEquals(Pos(6, 0), segment3.head)
        assertEquals(Pos(5, 0), segment3.tail)
        assertEquals(Pos(4, 0), segment2.tail)
        assertEquals(Pos(3, 0), segment1.tail)
    }

    @Test
    fun `follows small sample route with 10 segments`() {
        // given
        val end = Segment(name="9")
        val rope = (8 downTo 0).fold(end) { acc, i -> Segment(name=if (i == 0) "H" else i.toString()).attach(acc) }

        // when
        smallRoute.forEach { motion ->
            rope.move(motion)
        }

        // then
        assertEquals(1, end.tailVisited.size)
    }

    private val bigRoute = """
            R 5
            U 8
            L 8
            D 3
            R 17
            D 10
            L 25
            U 20
        """.trimIndent().lines()

    @Test
    fun `follows big sample route with 10 segments`() {
        // given
        val end = Segment(Pos(11, 5), "9")
        val rope = (8 downTo 0).fold(end) { acc, i -> Segment(Pos(11, 5), name=if (i == 0) "H" else i.toString()).attach(acc) }

        // when
        bigRoute.forEach { motion ->
            rope.move(motion)
        }

        // then
        assertEquals(36, end.headVisited.size)
    }
}
