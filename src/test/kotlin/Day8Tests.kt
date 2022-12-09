import org.junit.jupiter.api.Test
import kotlin.io.path.div
import kotlin.test.assertEquals

class Day8Tests {

    private val input = read(testResources / "Day8.txt")

    @Test
    fun part1() = assertEquals(
        21,
        Day8.part1(input)
    )

    @Test
    fun part2() = assertEquals(
        8,
        Day8.part2(input)
    )
}