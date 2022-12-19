package advent.day7

sealed class Entry {
    abstract val name: String
    abstract val size: Int
}

data class File(override val name: String, override val size: Int) : Entry()

class Dir(override val name: String, val up: Dir? = null, val contents: MutableSet<Entry> = mutableSetOf()) : Entry() {

    private val parentName = up?.name ?: "/"

    override val size: Int by lazy {
        contents.sumOf { entry -> entry.size }
    }

    fun walkDirectories(): Iterable<Dir> =
        contents.filterIsInstance<Dir>().flatMap { it.walkDirectories() } + this

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Dir

        if (name != other.name) return false
        if (parentName != other.parentName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + parentName.hashCode()
        return result
    }

    override fun toString(): String {
        return "Dir(name='$name', parentName='$parentName')"
    }
}

data class Filesystem(val root: Dir = Dir("/"), val capacity: Int = DEFAULT_CAPACITY) {
    fun sumDirectoriesBelowSize(limit: Int) = root.walkDirectories().filter { it.size <= limit }.sumOf { it.size }

    companion object {
        const val DEFAULT_CAPACITY = 70_000_000
    }

    fun smallestDirToEnsureSpace(spaceRequired: Int): Dir {
        val spaceNeeded: Int
        // capacity = 1000, used = 400, required = 700
        // spaceNeeded = 700 - (1000 - 400)
        return root.walkDirectories().sortedByDescending { it.size }
            .also { spaceNeeded = spaceRequired + it.first().size - capacity }
            .windowed(2).find { it[0].size >= spaceNeeded && it[1].size < spaceNeeded }!!.first()
    }
}
