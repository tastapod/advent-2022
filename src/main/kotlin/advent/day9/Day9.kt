package advent.day9

import kotlin.math.absoluteValue
import kotlin.math.sign

enum class Direction {
    Right {
        override fun of(pos: Pos) = pos.copy(x = pos.x + 1)
    },
    Left {
        override fun of(pos: Pos) = pos.copy(x = pos.x - 1)
    },
    Up {
        override fun of(pos: Pos) = pos.copy(y = pos.y + 1)
    },
    Down {
        override fun of(pos: Pos) = pos.copy(y = pos.y - 1)
    };

    abstract fun of(pos: Pos): Pos

    companion object {
        fun valueOf(letter: Char) = when (letter) {
            'R' -> Right
            'L' -> Left
            'U' -> Up
            'D' -> Down
            else -> throw IllegalArgumentException("Unknown direction: $letter")
        }
    }
}

class Segment(initial: Pos = Pos(0, 0), private val name: String = ".") {
    var head = initial
        private set(value) {
            field = value
            headVisited.add(value)
            tail = tail.follow(value)
        }

    var tail: Pos = initial
        private set(value) {
            field = value
            tailVisited.add(value)
            next?.head = value
        }

    val tailVisited = mutableSetOf(tail)
    val headVisited = mutableSetOf(head)

    private var next: Segment? = null

    fun move(motion: String) {
        val direction = Direction.valueOf(motion[0])
        val count = motion.substring(2).toInt()

        repeat(count) {
            head = head.move(direction)
        }
    }

    fun move(motions: List<String>) = motions.forEach { move(it) }

    fun attach(segment: Segment): Segment {
        this.next = segment
        return this
    }

    fun renderHeads(grid: Grid) {
        next?.renderHeads(grid)
        grid.set(head, name[0])
    }

    fun renderHeadVisited(grid: Grid) {
        headVisited.forEach() { pos -> grid.set(pos, '#') }
    }

    fun renderTailVisited(grid: Grid) {
        tailVisited.forEach() { pos -> grid.set(pos, '#') }
    }

    override fun toString(): String {
        return "$name(head=$head, tail=$tail,\n        next=$next)"
    }
}

class Grid(rows: Int, cols: Int) {
    private val cells = Array(rows) { Array(cols) { '.' } }

    fun set(pos: Pos, ch: Char) {
        cells[pos.y][pos.x] = ch
    }

    fun printHeads(segment: Segment) {
        segment.renderHeads(this)
        printCells("Knot heads")
    }

    fun printTailVisited(segment: Segment) {
        segment.renderTailVisited(this)
        printCells("Tail visited")
    }

    fun printHeadVisited(segment: Segment) {
        segment.renderHeadVisited(this)
        printCells("Head visited")
    }

    private fun printCells(title: String) {
        println("\n$title")
        cells.reversed().forEach { row -> println(row.joinToString("")) }
    }
}

data class Pos(val x: Int, val y: Int) {
    fun move(direction: Direction) = direction.of(this)

    fun follow(head: Pos): Pos {
        val xDist = (x - head.x).absoluteValue
        val yDist = (y - head.y).absoluteValue

        return when (xDist + yDist) {
            0, 1 -> this
            2 -> if (xDist == yDist) this else Pos((x + head.x) / 2, (y + head.y) / 2)
            3, 4 -> Pos(x + (head.x - x).sign, y + (head.y - y).sign)
            else -> throw IllegalStateException("tail = $this following head = $head")
        }
    }
}
