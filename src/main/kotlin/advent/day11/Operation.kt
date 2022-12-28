package advent.day11

typealias Operation = (Long) -> Long

class PlusSelf : Operation {
    override fun invoke(old: Long) = old + old
}

class Plus(private val arg: Long) : Operation {
    override fun invoke(old: Long) = old + arg
}

class TimesSelf : Operation {
    override fun invoke(old: Long) = old * old
}

class Times(private val arg: Long) : Operation {
    override fun invoke(old: Long) = old * arg
}

class Modulo(private val modulus: Long, private val operation: Operation): Operation {
    override fun invoke(old: Long) = operation(old) % modulus
}
