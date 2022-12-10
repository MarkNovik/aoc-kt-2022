object Day6 : AOC<Int, Int> {

    private fun findConsecutiveDistinctCharactersIndex(source: String, length: Int): Int = source
        .withIndex()
        .windowed(length)
        .first { it.distinctBy(IndexedValue<Char>::value).size == length }
        .last().index + 1

    override fun part1(input: String): Int = findConsecutiveDistinctCharactersIndex(input, 4)
    override fun part2(input: String): Int = findConsecutiveDistinctCharactersIndex(input, 14)
}