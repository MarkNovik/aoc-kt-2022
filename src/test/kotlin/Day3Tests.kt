import org.junit.jupiter.api.Test
import kotlin.io.path.div
import kotlin.test.assertEquals

class Day3Tests {
    private val input = read(testResources / "Day3.txt")



    @Test
    fun part1() = assertEquals(
        157,
        Day3.part1(input)
    )

    @Test
    fun part2() = assertEquals(
        70,
        Day3.part2(input)
    )
}