package advent.day8

data class Tree(val row: Int, val col: Int, val height: Int) {
    override fun toString(): String {
        return "($row,$col)=$height"
    }
}

class Grid(private val input: List<String>) {
    val downwards: List<List<Tree>>
    val upwards: List<List<Tree>>
    val forwards: List<List<Tree>>
    val backwards: List<List<Tree>>

    init {
        val numCols = input.first().length
        val numRows = input.size

        downwards = List(numCols) { col0 ->
            List(numRows) { row0 ->
                Tree(row = row0 + 1, col = col0 + 1, height = input[row0][col0].toString().toInt())
            }
        }
        upwards = downwards.map { it.asReversed() }

        forwards = List(numRows) { row0 ->
            List(numCols) { col0 ->
                Tree(row = row0 + 1, col = col0 + 1, height = input[row0][col0].toString().toInt())
            }
        }
        backwards = forwards.map { it.asReversed() }
    }

    fun visibleTrees() =
        listOf(forwards, backwards, downwards, upwards).flatten().fold(emptySet<Tree>()) { acc, treeLine ->
            var highest = -1
            acc + treeLine.filter { tree ->
                tree.height > highest.also {
                    highest = highest.coerceAtLeast(tree.height)
                }
            }
        }
}
