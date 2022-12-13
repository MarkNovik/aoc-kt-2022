import org.junit.jupiter.api.Test
import kotlin.io.path.div
import kotlin.test.assertEquals

class Day13Tests {

    private val input = read(testResources / "Day13.txt")

    @Test
    fun part1() = assertEquals(
        13,
        Day13.part1(input)
    )

    @Test
    fun part2() = assertEquals(
        140,
        Day13.part2(input)
    )
}