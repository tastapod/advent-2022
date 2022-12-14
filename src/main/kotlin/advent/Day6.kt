package advent

object Day6 {
    fun findStartOfPacket(sequence: String) = findMarker(sequence, 4)

    /**
     * Use the fact that a sequence of chars is the same size as a set of the chars only if they are all different
     */
    private fun findMarker(sequence: String, length: Int) =
        sequence.windowed(length).takeWhile { it.toSet().size < length }.count() + length

    fun findStartOfMessage(sequence: String) = findMarker(sequence, 14)
}
