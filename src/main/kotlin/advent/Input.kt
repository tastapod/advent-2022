package advent

object Input {
    fun fromResource(name: String) = javaClass.getResource(name)!!.readText().trim()

}
