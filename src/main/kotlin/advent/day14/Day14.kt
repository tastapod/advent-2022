package advent.day14

typealias Path = List<P>

data class P(val x: Int, val y: Int)

data class Cave(val inlet: P = P(500, 0), private val rocks: Set<P>) {
    operator fun contains(p: P) = p in rocks

    private val obstructions: MutableSet<P> = rocks.toMutableSet() // will fill with sand

    companion object {
        fun fromScan(scan: String) = fromPaths(scan.lines().map { scanLine ->
            scanLine.split(" -> ").map { coord ->
                val (x, y) = coord.split(",").map { it.toInt() }
                P(x, y)
            }
        })

        fun fromPaths(vararg path: Path) = fromPaths(listOf(*path))

        private fun fromPaths(paths: Iterable<Path>) = Cave(rocks = segmentEnds(paths).fold(emptySet()) { acc, (p1, p2) ->
            fun range(a: Int, b: Int) = if (a < b) a..b else b..a

            acc + range(p1.x, p2.x).map { x ->
                range(p1.y, p2.y).map { y ->
                    P(x, y)
                }
            }.flatten()
        })

        /**
         * converts zero or more paths (list of intermediate points) into flattened list of Pairs representing
         * start and end of path segments
         */
        private fun segmentEnds(paths: Iterable<Path>) = paths.map { path -> path.zipWithNext() }.flatten()
    }
}
