package advent.day14

enum class Obstacle(private val symbol: String) {
    Rock("#"), Sand("o");

    override fun toString() = symbol
}

typealias Path = List<P>

data class P(val x: Int, val y: Int) {
    fun down() = copy(y = y + 1)
    fun downLeft() = copy(x = x - 1, y = y + 1)
    fun downRight() = copy(x = x + 1, y = y + 1)
}

data class Cave(val inlet: P = P(500, 0), private val rocks: Map<P, Obstacle>) {
    private val depth = rocks.maxOf { it.key.y }
    private val obstacles = rocks.toMutableMap() // will fill with sand

    operator fun contains(p: P) = p in rocks
    operator fun get(p: P) = obstacles[p]

    private fun firstClear(vararg p: P) = p.firstOrNull { it !in obstacles }

    fun dropSand(): Boolean {
        var sandP = inlet

        while (sandP.y < depth && sandP !in obstacles) {
            firstClear(sandP.down(), sandP.downLeft(), sandP.downRight()).let { next ->
                if (next == null) {
                    // nowhere to go
                    obstacles[sandP] = Obstacle.Sand
                    return true
                } else {
                    sandP = next
                }
            }
        }

        // fell through
        return false
    }

    fun fillWithSand(): Int {
        do {
            val landed = dropSand()
        } while (landed)
        return obstacles.size - rocks.size
    }

    fun withFloor() = (depth + 2).let {
        copy(rocks = rocks + buildMapOfRocks(listOf(P(inlet.x - it - 2, it), P(inlet.x + it + 2, it))))
    }

    companion object {
        fun fromScan(scan: String) = fromPaths(*scan.lines().map { scanLine ->
            scanLine.split(" -> ").map { coord ->
                val (x, y) = coord.split(",").map { it.toInt() }
                P(x, y)
            }
        }.toTypedArray())

        fun fromPaths(vararg paths: Path) = Cave(rocks = buildMapOfRocks(*paths))

        /**
         * build a map of all the rocks in all the paths
         */
        private fun buildMapOfRocks(vararg paths: Path): Map<P, Obstacle> =
            paths.asList().toSegmentPairs().fold(emptyMap()) { acc, (p1, p2) ->
                fun range(a: Int, b: Int) = if (a < b) a..b else b..a

                acc + range(p1.x, p2.x).map { x ->
                    range(p1.y, p2.y).map { y ->
                        P(x, y) to Obstacle.Rock
                    }
                }.flatten()
            }

        /**
         * convert zero or more paths (list of intermediate points) into flattened list of Pairs representing
         * start and end of path segments
         */
        private fun List<Path>.toSegmentPairs() = map { path -> path.zipWithNext() }.flatten()
    }

}
