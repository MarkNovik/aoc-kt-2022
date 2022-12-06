import org.junit.jupiter.api.Test
import kotlin.io.path.div
import kotlin.test.assertEquals

class Day6Tests {

    private val input = read(testResources / "Day6.txt")

    private val part1Ans = listOf(7, 5, 6, 10, 11)
    private val part2Ans = listOf(19, 23, 23, 29, 26)

    private fun test(ans: List<Int>, func: (String) -> Int) = input
        .lines()
        .zip(ans)
        .forEach { (input, answer) ->
            assertEquals(
                answer,
                func(input)
            )
        }

    @Test
    fun part1() = test(part1Ans, Day6::part1)
    @Test
    fun part2() = test(part2Ans, Day6::part2)

}