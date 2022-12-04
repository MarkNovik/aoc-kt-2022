import org.junit.jupiter.api.Test
import kotlin.io.path.div
import kotlin.test.assertEquals

class Day4Tests {

    private val input = read(testResources / "Day4.txt")

    @Test
    fun part1() = assertEquals(
        2,
        Day4.part1(input)
    )

    @Test
    fun part2() = assertEquals(
        4,
        Day4.part2(input)
    )
}