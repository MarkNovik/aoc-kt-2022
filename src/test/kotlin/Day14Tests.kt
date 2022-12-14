import org.junit.jupiter.api.Test
import kotlin.io.path.div
import kotlin.test.assertEquals

class Day14Tests {

    private val input = read(testResources / "Day14.txt")

    @Test
    fun part1() = assertEquals(
        24,
        Day14.part1(input)
    )

    @Test
    fun part2() = assertEquals(
        93,
        Day14.part2(input)
    )
}