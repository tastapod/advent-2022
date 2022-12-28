package advent.day11

import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {
    @Test
    fun `inspects an item`() {
        // given
        val monkey = Monkey(0, listOf(79, 98), { it * 19 }, 23, mapOf(true to 2, false to 3))

        // then
        assertEquals(Throw(500, 3), monkey.inspectNextItem())
        assertEquals(Throw(620, 3), monkey.inspectNextItem())
    }

    @Test
    fun `takes a turn`() {
        // given
        val monkey = Monkey(0, listOf(79, 98), { it * 19 }, 23, mapOf(true to 2, false to 3))

        // when
        val result = monkey.takeTurn()

        // then
        assertEquals(listOf(Throw(500, 3), Throw(620, 3)), result)
    }

    @Test
    fun `parses operation`() {
        val plus6 = Monkey.parseOperation("new = old + 6")
        assertEquals(16, plus6(10))

        val plusSelf = Monkey.parseOperation("new = old + old")
        assertEquals(20, plusSelf(10))

        val times6 = Monkey.parseOperation("new = old * 6")
        assertEquals(60, times6(10))

        val timesSelf = Monkey.parseOperation("new = old * old")
        assertEquals(100, timesSelf(10))
    }

    @Test
    fun `parses monkey note`() {
        // given
        val note = """
            Monkey 1:
              Starting items: 54, 65, 75, 74
              Operation: new = old + 6
              Test: divisible by 19
                If true: throw to monkey 2
                If false: throw to monkey 0
        """.trimIndent()

        // when
        val monkey = Monkey.parseNote(note)

        // then
        assertEquals(1, monkey.index)
        assertEquals(listOf<Long>(54, 65, 75, 74), monkey.items)
        assertEquals(19, monkey.divisor)
        assertEquals(mapOf(true to 2, false to 0), monkey.receivers)
        assertEquals(16, monkey.operation(10))
    }

    private val sampleNotes = """
        Monkey 0:
          Starting items: 79, 98
          Operation: new = old * 19
          Test: divisible by 23
            If true: throw to monkey 2
            If false: throw to monkey 3
        
        Monkey 1:
          Starting items: 54, 65, 75, 74
          Operation: new = old + 6
          Test: divisible by 19
            If true: throw to monkey 2
            If false: throw to monkey 0
        
        Monkey 2:
          Starting items: 79, 60, 97
          Operation: new = old * old
          Test: divisible by 13
            If true: throw to monkey 1
            If false: throw to monkey 3
        
        Monkey 3:
          Starting items: 74
          Operation: new = old + 3
          Test: divisible by 17
            If true: throw to monkey 0
            If false: throw to monkey 1
    """.trimIndent()

    @Test
    fun `watches monkeys`() {
        // given
        val monkeys = Monkey.parseNotes(sampleNotes)

        // when
        monkeys.playRound()

        // then
        assertEquals(listOf<Long>(20, 23, 27, 26), monkeys[0].items)
        assertEquals(listOf<Long>(2080, 25, 167, 207, 401, 1046), monkeys[1].items)
        assertEquals(emptyList(), monkeys[2].items)
        assertEquals(emptyList(), monkeys[3].items)

        // when
        repeat(19) { monkeys.playRound() }

        // then
        assertEquals(listOf<Long>(10, 12, 14, 26, 34), monkeys[0].items)
        assertEquals(listOf<Long>(245, 93, 53, 199, 115), monkeys[1].items)
        assertEquals(emptyList(), monkeys[2].items)
        assertEquals(emptyList(), monkeys[3].items)
    }

    @Test
    fun `counts monkey activity`() {
        // given
        val monkeys = Monkey.parseNotes(sampleNotes)

        // when
        repeat(20) { monkeys.playRound() }

        // then
        assertEquals(listOf(101, 95, 7, 105), monkeys.map { it.inspections })
    }

    @Test
    fun `calculates monkey business`() {
        // given
        val monkeys = Monkey.parseNotes(sampleNotes)

        // when
        repeat(20) { monkeys.playRound() }

        // then
        assertEquals(10605, monkeys.monkeyBusiness())
    }

    @Test
    fun `watches monkeys with no worries`() {
        // given
        val monkeys = Monkey.parseNotes(sampleNotes)

        // when
        monkeys.playRound(false)

        // then
        assertEquals(listOf(2, 4, 3, 6), monkeys.map { it.inspections })

        // when
        repeat(19) { monkeys.playRound(false) }

        // then
        assertEquals(listOf(99, 97, 8, 103), monkeys.map { it.inspections })

        // when
        repeat(980) { monkeys.playRound(false) }

        // then
        assertEquals(listOf(5204, 4792, 199, 5192), monkeys.map { it.inspections })

        // when
        repeat(9000) { monkeys.playRound(false) }

        // then
        assertEquals(2713310158, monkeys.monkeyBusiness())
    }
}
