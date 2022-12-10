object Day4 : AOC<Int, Int> {
    private fun parseLine(line: String): Pair<IntRange, IntRange> = line
        .split(",", "-")
        .map(String::toInt)
        .let { (a, b, c, d) ->
            a..b to c..d
        }

    private inline fun calculate(input: String, block: (IntRange, IntRange) -> Boolean): Int = input
        .lines()
        .map(::parseLine)
        .count { (a, b) -> block(a, b) }

    override fun part1(input: String): Int = calculate(input) { a, b -> a in b || b in a }

    override fun part2(input: String): Int = calculate(input, IntRange::overlaps)
}

private infix fun IntRange.overlaps(other: IntRange): Boolean =
    (this intersect other).isNotEmpty()

private operator fun IntRange.contains(other: IntRange): Boolean =
    (this intersect other) == other.toSet()
