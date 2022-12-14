import Outcome.*

object Day2 : AOC<Int, Int> {

    private fun parseBattle(line: String): Battle {
        val (elf, _, me) = line.toList()
        return Battle(
            RpsMove.fromChar(me),
            RpsMove.fromChar(elf)
        )
    }

    private fun parseOpponentAndOutcome(line: String): Pair<RpsMove, Outcome> {
        val (elf, _, outcome) = line.toList()
        return Pair(
            RpsMove.fromChar(elf),
            Outcome.fromChar(outcome)
        )
    }

    override fun part1(input: String): Int = input
        .lines()
        .map(::parseBattle)
        .sumOf { (me, elf) ->
            val outcome = me battle elf
            me.points + outcome.points
        }

    override fun part2(input: String): Int = input
        .lines()
        .map(::parseOpponentAndOutcome)
        .sumOf { (elf, outcome) ->
            val me = elf battleMustEndWith outcome
            me.points + outcome.points
        }
}

private data class Battle(val me: RpsMove, val elf: RpsMove)

private enum class Outcome(val points: Int) {
    LOSE(0), DRAW(3), WIN(6);

    companion object {
        fun fromChar(char: Char) = when (char) {
            'X' -> LOSE
            'Y' -> DRAW
            'Z' -> WIN
            else -> error("Unexpected outcome `$char`")
        }
    }
}

private enum class RpsMove(val points: Int) {
    ROCK(1), PAPER(2), SCISSORS(3);

    infix fun battle(opponent: RpsMove): Outcome = when (this) {
        ROCK -> when (opponent) {
            ROCK -> DRAW
            PAPER -> LOSE
            SCISSORS -> WIN
        }

        PAPER -> when (opponent) {
            ROCK -> WIN
            PAPER -> DRAW
            SCISSORS -> LOSE
        }

        SCISSORS -> when (opponent) {
            ROCK -> LOSE
            PAPER -> WIN
            SCISSORS -> DRAW
        }
    }

    infix fun battleMustEndWith(result: Outcome): RpsMove = when (this) {
        ROCK -> when (result) {
            LOSE -> SCISSORS
            DRAW -> ROCK
            WIN -> PAPER
        }

        PAPER -> when (result) {
            LOSE -> ROCK
            DRAW -> PAPER
            WIN -> SCISSORS
        }

        SCISSORS -> when (result) {
            LOSE -> PAPER
            DRAW -> SCISSORS
            WIN -> ROCK
        }
    }

    companion object {
        fun fromChar(char: Char): RpsMove = when (char) {
            'A', 'X' -> ROCK
            'B', 'Y' -> PAPER
            'C', 'Z' -> SCISSORS
            else -> error("Unexpected Rps shape `$char`")
        }
    }
}