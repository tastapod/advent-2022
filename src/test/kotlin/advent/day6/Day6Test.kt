package advent.day6

import kotlin.test.Test
import kotlin.test.assertEquals

class Day6Test {
    @Test
    fun `finds start of packet`() {
        assertEquals(10, findStartOfPacket("ababababcdabab"))
    }

    @Test
    fun `finds start of packet in samples`() {
        assertEquals(7, findStartOfPacket("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(5, findStartOfPacket("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(6, findStartOfPacket("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(10, findStartOfPacket("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(11, findStartOfPacket("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    }

    @Test
    fun `finds start of message in samples`() {
        assertEquals(19, findStartOfMessage("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(23, findStartOfMessage("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(23, findStartOfMessage("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(29, findStartOfMessage("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(26, findStartOfMessage("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    }
}
