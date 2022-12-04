object Day1 : AOC {

    private val elfTotal = { elf: String ->
        elf
            .split(System.lineSeparator())
            .map(String::toInt)
            .sum()
    }

    private fun String.separateElves() = split(System.lineSeparator().repeat(2))
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