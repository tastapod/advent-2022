package advent.day7

sealed class Entry(open val name: String) {
    abstract val size: Int
}

data class File(override val name: String, override val size: Int) : Entry(name)

data class Dir(override val name: String, var contents: MutableSet<Entry> = mutableSetOf(), val capacity: Int) :
    Entry(name) {
    /**
     * These shenanigans are necessary to avoid the link to parent directory causing a stack overflow in `hashCode` etc.
     *
     * There may be a better way to exclude a data class property from `equals`, `hashCode`, `toString`
     */
    constructor(
        name: String,
        up: Dir? = null,
        contents: MutableSet<Entry> = mutableSetOf(),
        capacity: Int = 70_000_000) : this(name, contents, capacity) {
        this.up = up
    }

    var up: Dir? = null
        // only settable from constructor
        private set

    override val size: Int by lazy {
        contents.sumOf { entry -> entry.size }
    }

    fun walkDirectories(): Iterable<Dir> =
        contents.filterIsInstance<Dir>().flatMap { it.walkDirectories() } + this
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
            .windowed(2).find {it[0].size >= spaceNeeded && it[1].size < spaceNeeded}!!.first()
    }
}
