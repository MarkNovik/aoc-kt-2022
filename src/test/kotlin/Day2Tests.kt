import org.junit.jupiter.api.Test
import kotlin.io.path.div
import kotlin.test.assertEquals

class Day2Tests {
    private val input = read(testResources / "Day2.txt")

    @Test
    fun part1() = assertEquals(
        15,
        Day2.part1(input)
    )

    @Test
    fun part2() = assertEquals(
        12,
        Day2.part2(input)
    )
}