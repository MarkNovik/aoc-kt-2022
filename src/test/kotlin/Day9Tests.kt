import org.junit.jupiter.api.Test
import kotlin.io.path.div
import kotlin.test.assertEquals

class Day9Tests {

    private val input1 = read(testResources / "Day9A.txt")
    private val input2 = read(testResources / "Day9B.txt")

    @Test
    fun part1() = assertEquals(
        13,
        Day9.part1(input1)
    )

    @Test
    fun part2() = assertEquals(
        36,
        Day9.part2(input2)
    )
}