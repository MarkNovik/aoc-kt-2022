import org.junit.jupiter.api.Test
import kotlin.io.path.div
import kotlin.test.assertEquals

class Day12Tests {

    private val input = read(testResources / "Day12.txt")

    @Test
    fun part1() = assertEquals(
        31,
        Day12.part1(input)
    )

    @Test
    fun part2() = assertEquals(
        29,
        Day12.part2(input)
    )
}