package advent

object Day6 {
    fun findStartOfPacket(sequence: String) = findMarker(sequence, 4)

    private fun findMarker(sequence: String, size: Int) =
        sequence.windowed(size).takeWhile { it.toSet().size < size }.count() + size

    fun findStartOfMessage(sequence: String) = findMarker(sequence, 14)
}
