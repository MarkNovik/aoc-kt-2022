sealed interface AOC<T : Any> {
    fun part1(input: String): T
    fun part2(input: String): T
}