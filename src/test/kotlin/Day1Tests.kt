import kotlin.io.path.div
import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Tests {
    @Test
    fun part1() = assertEquals(
        24000,
        Day1.part1(read(testResources / "Day1.txt"))
    )

    @Test
    fun part2() = assertEquals(
        45000,
        Day1.part2(read(testResources / "Day1.txt"))
    )
}