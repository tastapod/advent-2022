package advent

import kotlin.test.Test
import kotlin.test.assertEquals

class Day6Test {
    @Test
    fun `finds start of packet`() {
        assertEquals(10, Day6.findStartOfPacket("ababababcdabab"))
    }

    @Test
    fun `finds start of packet in samples`() {
        assertEquals(7, Day6.findStartOfPacket("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(5, Day6.findStartOfPacket("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(6, Day6.findStartOfPacket("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(10, Day6.findStartOfPacket("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(11, Day6.findStartOfPacket("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    }

    @Test
    fun `finds start of message in samples`() {
        assertEquals(19, Day6.findStartOfMessage("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(23, Day6.findStartOfMessage("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(23, Day6.findStartOfMessage("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(29, Day6.findStartOfMessage("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(26, Day6.findStartOfMessage("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    }
}
