object Day4 : AOC {

    private operator fun IntRange.contains(other: IntRange): Boolean =
        (this intersect other) == other.toSet()

    private infix fun IntRange.overlaps(other: IntRange): Boolean =
        (this intersect other).isNotEmpty()

    private fun parseLine(line: String): Pair<IntRange, IntRange> = line
        .split(",", "-")
        .map(String::toInt)
        .let { (a, b, c, d) ->
            a..b to c..d
        }

    private fun String.calculate(block: (IntRange, IntRange) -> Boolean): Int = this
        .lines()
        .map(::parseLine)
        .count { (a, b) -> block(a, b) }

    override fun part1(input: String): Int = input
        .calculate { a, b -> a in b || b in a }

    override fun part2(input: String): Int = input
        .calculate { a, b -> a.overlaps(b) }
}

