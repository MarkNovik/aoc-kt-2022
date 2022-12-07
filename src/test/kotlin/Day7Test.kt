import org.junit.jupiter.api.Test
import kotlin.io.path.div
import kotlin.test.assertEquals

class Day7Test {
    val input = read(testResources / "Day7.txt")

    @Test
    fun part1() = assertEquals(
        95437,
        Day7.part1(input)
    )

    @Test
    fun part2() = assertEquals(
        24933642,
        Day7.part2(input)
    )
}