package advent.day8

data class Tree(val row: Int, val col: Int, val height: Int) {
    override fun toString(): String {
        return "($row,$col)=$height"
    }
}

class Grid(input: List<String>) {
    internal val lookingDown: List<List<Tree>>
    internal val lookingUp: List<List<Tree>>
    internal val lookingRight: List<List<Tree>>
    internal val lookingLeft: List<List<Tree>>

    private val numCols = input.first().length
    private val numRows = input.size

    init {

        lookingDown = List(numCols) { col0 ->
            List(numRows) { row0 ->
                Tree(row = row0 + 1, col = col0 + 1, height = input[row0][col0].toString().toInt())
            }
        }
        lookingUp = lookingDown.map { it.asReversed() }

        lookingRight = List(numRows) { row0 ->
            List(numCols) { col0 ->
                lookingDown[col0][row0]
            }
        }
        lookingLeft = lookingRight.map { it.asReversed() }
    }

    fun visibleTrees() =
        listOf(lookingRight, lookingLeft, lookingDown, lookingUp).flatten().fold(emptySet<Tree>()) { acc, treeLine ->
            var highest = -1
            acc + treeLine.filter { tree ->
                tree.height > highest.also {
                    highest = highest.coerceAtLeast(tree.height)
                }
            }
        }

    private fun List<Tree>.viewingDistance(height: Int) =
        indexOfFirst { it.height >= height }.let { if (it >= 0) it + 1 else size }

    fun scenicScore(row: Int, col: Int): Int {
        val row0 = row - 1
        val col0 = col - 1
        val height = lookingDown[col0][row0].height

        return lookingDown[col0].subList(row0 + 1, numRows).viewingDistance(height) *
                lookingUp[col0].subList(numRows - row0, numRows).viewingDistance(height) *
                lookingRight[row0].subList(col0 + 1, numCols).viewingDistance(height) *
                lookingLeft[row0].subList(numCols - col0, numCols).viewingDistance(height)
    }

    fun maxScenicScore() =
        (1..numRows).map { row -> (1..numCols).map { col -> scenicScore(row, col) }
        }.flatten().max()
}
