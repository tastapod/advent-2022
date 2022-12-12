package advent

object Input {
    fun forDay(day: Int) = javaClass.getResource("/advent/Day$day.txt")!!.readText().trim()
}
