import DistressSignal.SignalList
import kotlin.math.max

private typealias DistressSignalListPairs = List<Pair<DistressSignal, DistressSignal>>

object Day13 : AOC<Int, Int> {

    private fun parseSignalList(line: String): DistressSignal = Parser.parse(line)

    private fun parseInput(input: String): DistressSignalListPairs = input
        .split(System.lineSeparator().repeat(2))
        .map { line ->
            val (a, b) = line.split(System.lineSeparator())
            parseSignalList(a) to parseSignalList(b)
        }

    override fun part1(input: String): Int =
        parseInput(input).mapIndexedNotNull { index, (a, b) -> (index + 1).takeIf { a <= b } }.sum()

    override fun part2(input: String): Int =
        parseInput(input)
            .flatMap { it.toList() }
            .plus(
                listOf(
                    Parser.parse("[[2]]"),
                    Parser.parse("[[6]]")
                )
            )
            .sorted()
            .mapIndexedNotNull { index, distressSignal ->
                (index + 1).takeIf { distressSignal.toString() in listOf("[[2]]", "[[6]]") }
            }.reduce(Int::times)
}

private sealed interface DistressSignal : Comparable<DistressSignal> {
    @JvmInline
    value class SignalInteger(val int: Int) : DistressSignal {
        override fun compareTo(other: DistressSignal): Int = when (other) {
            is SignalInteger -> this.int compareTo other.int
            is SignalList -> SignalList(listOf(this)) compareTo other
        }

        override fun toString(): String = "$int"
    }

    @JvmInline
    value class SignalList(val list: List<DistressSignal>) : DistressSignal {
        override fun compareTo(other: DistressSignal): Int {
            return when (other) {
                is SignalList -> {
                    for (i in 0 until (max(this.list.size, other.list.size))) {
                        val a = this.list.getOrNull(i) ?: return -1
                        val b = other.list.getOrNull(i) ?: return 1
                        if (a compareTo b != 0) return a compareTo b
                    }
                    0
                }

                is SignalInteger -> this compareTo SignalList(listOf(other))
            }
        }

        override fun toString(): String = "$list"
    }
}

private sealed interface Lexeme {
    object OpenBracket : Lexeme
    object CloseBracket : Lexeme
    object Comma : Lexeme
    class Number(val int: Int) : Lexeme
}

private tailrec fun lexText(input: String, acc: List<Lexeme> = emptyList()): List<Lexeme> =
    when {
        input.isEmpty() -> acc
        input.first() == '[' -> lexText(input.drop(1), acc + Lexeme.OpenBracket)
        input.first() == ']' -> lexText(input.drop(1), acc + Lexeme.CloseBracket)
        input.first() == ',' -> lexText(input.drop(1), acc + Lexeme.Comma)
        input.first().isDigit() -> lexText(
            input.dropWhile(Char::isDigit),
            acc + Lexeme.Number(input.takeWhile(Char::isDigit).toInt())
        )

        else -> error("Unexpected lexeme `${input.first()}`")
    }

private class Parser(private val tokens: List<Lexeme>) {
    private var pos = 0

    private fun list(): List<DistressSignal> {
        consume<Lexeme.OpenBracket>()
        val res = mutableListOf<DistressSignal>()
        while (!match<Lexeme.CloseBracket>()) {
            res += distressSignal()
            match<Lexeme.Comma>()
        }
        return res
    }

    private fun distressSignal(): DistressSignal =
        when (val curr = tokens[pos]) {
            is Lexeme.Number -> {
                pos++
                DistressSignal.SignalInteger(curr.int)
            }

            Lexeme.OpenBracket -> SignalList(list())
            else -> error("expected Number|OpenBracket, got ${curr::class.simpleName}")
        }


    private inline fun <reified T : Lexeme> consume(): T =
        when (val curr = tokens[pos]) {
            is T -> {
                pos++
                curr
            }

            else -> error("Expected ${T::class.simpleName} got ${curr::class.simpleName}")
        }

    private inline fun <reified T : Lexeme> match(): Boolean {
        if (tokens[pos] !is T) return false
        pos++
        return true
    }

    companion object {
        fun parse(input: String): DistressSignal = Parser(lexText(input)).distressSignal()
    }
}
