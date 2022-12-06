object Day6 : AOC<Int> {

    private fun findConsecutiveDistinctCharactersIndex(source: String, length: Int): Int = source
        .withIndex()
        .windowed(length)
        .map { it.joinToString("") { (_, v) -> "$v" }.toSet() to (it.last().index + 1) }
        .first { (s, _) -> s.size == length }
        .second

    override fun part1(input: String): Int = findConsecutiveDistinctCharactersIndex(input, 4)
    override fun part2(input: String): Int = findConsecutiveDistinctCharactersIndex(input, 14)
}