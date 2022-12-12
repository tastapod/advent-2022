package advent


enum class Result(val score: Int) {
    Win(6) {
        override fun against(play: Play) = when (play) {
            Play.Rock -> Play.Paper
            Play.Paper -> Play.Scissors
            Play.Scissors -> Play.Rock
        }
    },
    Draw(3) {
        override fun against(play: Play) = when (play) {
            Play.Rock -> Play.Rock
            Play.Paper -> Play.Paper
            Play.Scissors -> Play.Scissors
        }
    },
    Lose(0) {
        override fun against(play: Play) = when (play) {
            Play.Rock -> Play.Scissors
            Play.Paper -> Play.Rock
            Play.Scissors -> Play.Paper
        }
    };

    abstract fun against(play: Play): Play

    companion object {
        fun fromChar(ch: Char) = when (ch) {
            'X' -> Lose
            'Y' -> Draw
            'Z' -> Win

            else -> throw IllegalArgumentException("Unknown throw: $ch")
        }
    }
}

enum class Play(val score: Int) {
    Rock(1) {
        override fun vs(other: Play) = when (other) {
            Rock -> Result.Draw
            Paper -> Result.Lose
            Scissors -> Result.Win
        }
    },
    Paper(2) {
        override fun vs(other: Play) = when (other) {
            Rock -> Result.Win
            Paper -> Result.Draw
            Scissors -> Result.Lose
        }
    },
    Scissors(3) {
        override fun vs(other: Play) = when (other) {
            Rock -> Result.Lose
            Paper -> Result.Win
            Scissors -> Result.Draw
        }
    };

    abstract fun vs(other: Play): Result

    companion object {
        fun fromChar(ch: Char) = when (ch) {
            'A', 'X' -> Rock
            'B', 'Y' -> Paper
            'C', 'Z' -> Scissors

            else -> throw IllegalArgumentException("Unknown throw: $ch")
        }
    }
}

object Day2 {
    fun score(me: Play, you: Play) = me.score + me.vs(you).score
    fun score(me: Char, you: Char) = score(Play.fromChar(me), Play.fromChar(you))

    fun playPart1Game(game: String) = game.split("\n").map { score(it[2], it[0]) }.sum()

    fun playPart2Game(game: String) = game.split("\n").map {
        val you = Play.fromChar(it[0])
        val result = Result.fromChar(it[2])
        val me = result.against(you)
        score(me, you)
    }.sum()
}
