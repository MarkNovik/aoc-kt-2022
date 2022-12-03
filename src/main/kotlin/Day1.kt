object Day1 : AOC {
    private fun String.separateElves() = split(System.lineSeparator().repeat(2))
    private val elfTotal = { elf: String ->
        elf
            .split(System.lineSeparator())
            .map(String::toInt)
            .sum()
    }

    override fun part1(input: String): Int = input
        .separateElves()
        .maxOf(elfTotal)

    override fun part2(input: String): Int = input
        .separateElves()
        .map(elfTotal)
        .sortedDescending()
        .take(3)
        .sum()
}