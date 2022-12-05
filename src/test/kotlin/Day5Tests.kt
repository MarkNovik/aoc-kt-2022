import org.junit.jupiter.api.Test
import kotlin.io.path.div
import kotlin.test.assertEquals

class Day5Tests {

    private val input = read(testResources / "Day5.txt")

    @Test
    fun part1() = assertEquals(
        "CMZ",
        Day5.part1(input)
    )

    @Test
    fun part2() = assertEquals(
        "MCD",
        Day5.part2(input)
    )

}