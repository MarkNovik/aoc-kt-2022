import org.junit.jupiter.api.Test
import kotlin.io.path.div
import kotlin.test.assertEquals

class Day10Tests {
    private val input = read(testResources / "Day10.txt")

    @Test
    fun part1() = assertEquals(
        13140,
        Day10.part1(input)
    )

    @Test
    fun part2() = assertEquals(
        """
##..##..##..##..##..##..##..##..##..##..
###...###...###...###...###...###...###.
####....####....####....####....####....
#####.....#####.....#####.....#####.....
######......######......######......####
#######.......#######.......#######.....""",
        Day10.part2(input)
    )
}