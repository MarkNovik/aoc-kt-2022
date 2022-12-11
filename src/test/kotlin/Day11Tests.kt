import org.junit.jupiter.api.Test
import kotlin.io.path.div
import kotlin.test.assertEquals

class Day11Tests {

    private val input = read(testResources / "Day11.txt")

    @Test
    fun part1() = assertEquals(
        10605,
        Day11.part1(input)
    )

    @Test
    fun part2() = assertEquals(
        2713310158,
        Day11.part2(input)
    )
}