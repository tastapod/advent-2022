package advent.day12

import java.util.*

val HEIGHTS = ('a'..'z').zip(1..26).toMap() + mapOf('S' to 1, 'E' to 26)

fun Char.toHeight() = HEIGHTS[this] ?: Int.MAX_VALUE

data class Position(val row: Int, val col: Int) {
    override fun toString() = "(row=$row, col=$col)"
}

data class Elevation(val pos: Position, val height: Int) {
    override fun toString() = "$pos=$height"
}

private const val BIG_VALUE = Int.MAX_VALUE / 2 // big but not too big

data class RouteMap(
    val start: Elevation,
    val target: Elevation,
    val numRows: Int,
    val numCols: Int,
    private val elevations: List<Elevation>) {

    private val elevationsByPosition = elevations.associateBy { it.pos }

    fun neighbours(pos: Position): Set<Elevation> =
        mutableSetOf<Elevation>().apply {
            with(pos) {
                elevationsByPosition[copy(row = row - 1)]?.let { add(it) }
                elevationsByPosition[copy(row = row + 1)]?.let { add(it) }
                elevationsByPosition[copy(col = col - 1)]?.let { add(it) }
                elevationsByPosition[copy(col = col + 1)]?.let { add(it) }
            }
        }

    private fun dijkstra(
        start: Elevation = this.start,
        canReach: (here: Elevation, neighbour: Elevation) -> Boolean
    ): Map<Elevation, Int> {
        val distances = elevations.associateWith { if (it == start) 0 else BIG_VALUE }.toMutableMap()
        val remaining =
            PriorityQueue<Elevation> { e1, e2 -> distances[e1]!! - distances[e2]!! }.apply { addAll(elevations) }
        val visited = mutableSetOf<Elevation>()

        while (remaining.isNotEmpty()) {
            val here = remaining.remove()
            visited += here

            neighbours(here.pos).minus(visited).forEach { neighbour ->
                val cost = if (canReach(here, neighbour)) distances[here]!! + 1 else BIG_VALUE
                if (cost < distances[neighbour]!!) {
                    distances[neighbour] = cost
                    remaining.remove(neighbour)
                    remaining.add(neighbour)
                }
            }
        }

        return distances.toMap()
    }

    fun shortestRoute() = dijkstra(start) { here, neighbour -> neighbour.height - here.height <= 1 }.getValue(target)

    fun heightAt(row: Int, col: Int) = elevationsByPosition[Position(row, col)]!!.height

    fun shortestRouteFromHeight(height: Int) =
        dijkstra(target) { here, neighbour -> neighbour.height - here.height >= -1 }
            .filterKeys { it.height == height }
            .minOf { it.value }

companion object {
    fun parseMap(sourceMap: String): RouteMap {
        val rows = sourceMap.lines()
        val numRows = rows.size
        val numCols = rows[0].length

        var start: Elevation? = null
        var target: Elevation? = null

        val heights = rows.mapIndexed { row, rowChars ->
            rowChars.mapIndexed { col, ch ->
                Elevation(Position(row, col), ch.toHeight()).also {
                    when (ch) {
                        'S' -> start = it
                        'E' -> target = it
                    }
                }
            }
        }.flatten()

        return RouteMap(start!!, target!!, numRows, numCols, heights)
    }
}
}

