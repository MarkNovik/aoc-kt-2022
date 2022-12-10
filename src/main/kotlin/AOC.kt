sealed interface AOC<TPart1 : Any, TPart2: Any> {
    fun part1(input: String): TPart1
    fun part2(input: String): TPart2
}