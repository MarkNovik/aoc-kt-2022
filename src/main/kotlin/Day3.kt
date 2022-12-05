object Day3 : AOC<Int> {
    private fun priority(char: Char) =
        (('a'..'z') + ('A'..'Z')).indexOf(char) + 1

    override fun part1(input: String): Int = input
        .lines()
        .map(::Rucksack)
        .map(Rucksack::commonItems)
        .sumOf {
            it.sumOf(::priority)
        }

    override fun part2(input: String): Int = input
        .lines()
        .chunked(3)
        .map {
            it.fold(it.first().toSet()) { acc, next ->
                acc intersect next.toSet()
            }.single()
        }
        .sumOf(::priority)

    private class Rucksack(items: String) {
        val compartment1: String = items.substring(0, items.length / 2)
        val compartment2: String = items.substring(items.length / 2)

        fun commonItems(): Set<Char> =
            compartment1.toSet() intersect compartment2.toSet()
    }
}