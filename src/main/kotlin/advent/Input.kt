package advent

object Input {
    fun forDay(day: Int, suffix: String = "") = javaClass.getResource("/advent/Day$day$suffix.txt")!!.readText().trimEnd()
}
