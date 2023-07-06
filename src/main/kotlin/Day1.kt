object Day1 : AOC<Int, Int> {
    private val elfTotal = { elf: String ->
        elf
            .split(System.lineSeparator())
            .map(String::toInt)
            .sum()
    }

    private fun separateElves(s: String) = s.split(System.lineSeparator().repeat(2))

    override fun part1(input: String): Int = input
        .let(::separateElves)
        .maxOf(elfTotal)

    override fun part2(input: String): Int = input
        .let(::separateElves)
        .map(elfTotal)
        .sortedDescending()
        .take(3)
        .sum()
}