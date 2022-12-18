package advent.day7

sealed class Entry(open val name: String) {
    abstract val size: Int
}

data class File(override val name: String, override val size: Int) : Entry(name)

data class Dir(override val name: String, var contents: MutableSet<Entry> = mutableSetOf()) : Entry(name) {
    /**
     * These shenanigans are necessary to avoid the link to parent directory causing a stack overflow in `hashCode` etc.
     *
     * There may be a better way to exclude a data class property from `equals`, `hashCode`, `toString`
     */
    constructor(name: String, up: Dir? = null, contents: MutableSet<Entry> = mutableSetOf()) : this(name, contents) {
        this.up = up
    }

    var up: Dir? = null
        // only settable from constructor
        private set

    override val size: Int by lazy {
        contents.sumOf { entry -> entry.size }
    }

    private fun walkDirectories(): Iterable<Dir> =
        contents.filterIsInstance<Dir>().flatMap { it.walkDirectories() } + this

    fun sumDirectoriesBelowSize(limit: Int) = walkDirectories().filter { it.size <= limit }.sumOf { it.size }
}
