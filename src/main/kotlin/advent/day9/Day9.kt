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

class Rope(initial: Pos = Pos(0, 0)) {
    var head = initial
        private set(value) {
            field = value.also { tail = tail.follow(it) }
        }
    var tail: Pos = initial
        private set(value) {
            field = value.also { tailVisited.add(it) }
        }
    val tailVisited = mutableSetOf(tail)

    fun move(motion: String) {
        val direction = Direction.valueOf(motion[0])
        val count = motion.substring(2).toInt()

        repeat(count) {
            head = head.move(direction)
        }
    }

    fun move(motions: List<String>) = motions.forEach { move(it) }
}

data class Pos(val x: Int, val y: Int) {
    fun move(direction: Direction) = direction.of(this)

    fun follow(head: Pos): Pos {
        val xDist = (x - head.x).absoluteValue
        val yDist = (y - head.y).absoluteValue

        return when (xDist + yDist) {
            2 -> if (xDist == yDist) this else Pos((x + head.x) / 2, (y + head.y) / 2)
            3 -> Pos(x + (head.x - x).sign, y + (head.y - y).sign)
            else -> this
        }
    }
}
