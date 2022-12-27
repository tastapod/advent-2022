package advent.day11

typealias Operation = (Int) -> Int

class PlusSelf : Operation {
    override fun invoke(old: Int) = old + old
}

class Plus(private val arg: Int) : Operation {
    override fun invoke(old: Int) = old + arg
}

class TimesSelf : Operation {
    override fun invoke(old: Int) = old * old
}

class Times(private val arg: Int) : Operation {
    override fun invoke(old: Int) = old * arg
}
