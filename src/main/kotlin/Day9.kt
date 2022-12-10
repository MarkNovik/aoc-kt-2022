import kotlin.math.abs

object Day9 : AOC<Int, Int> {
    private fun ropeMoves(tailKnots: Int, input: String) = input
        .lines()
        .flatMap(Direction::fromCodedString)
        .scan(Rope(tailKnots)) { rope, movement ->
            when (movement) {
                Direction.UP -> rope.moveUp()
                Direction.DOWN -> rope.moveDown()
                Direction.LEFT -> rope.moveLeft()
                Direction.RIGHT -> rope.moveRight()
            }
        }.distinctBy(Rope::tail).size


    override fun part1(input: String): Int = ropeMoves(1, input)
    override fun part2(input: String): Int = ropeMoves(9, input)
}

private enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    companion object {
        fun fromChar(char: Char) = when (char) {
            'U' -> UP
            'D' -> DOWN
            'L' -> LEFT
            'R' -> RIGHT
            else -> error("Unexpected side `$char`")
        }

        fun fromCodedString(str: String): List<Direction> = str
            .split(" ")
            .let { (side, amount) ->
                List(amount.toInt()) { fromChar(side.single()) }
            }
    }
}

private data class Knot(val x: Int, val y: Int) {
    fun move(towards: Knot): Knot {
        val dx = towards.x - x
        val dy = towards.y - y
        return when {
            abs(dx) == 2 && abs(dy) <= 1 -> Knot(x.approach(towards.x), towards.y)
            abs(dx) <= 1 && abs(dy) == 2 -> Knot(towards.x, y.approach(towards.y))
            abs(dx) == 2 && abs(dy) == 2 -> Knot(x.approach(towards.x), y.approach(towards.y))
            else -> this
        }
    }
}

private class Rope private constructor(val head: Knot, val knots: List<Knot>) {
    val tail get() = knots.last()

    constructor(tailKnots: Int) : this(
        Knot(0, 0),
        List(tailKnots) { Knot(0, 0) }
    )

    private fun move(hx: Int, hy: Int): Rope = knots
        .fold(listOf(Knot(hx, hy))) { acc, next -> acc + (next.move(acc.last())) }
        .run { Rope(first(), drop(1)) }

    fun moveUp() = move(head.x, head.y + 1)
    fun moveDown() = move(head.x, head.y - 1)
    fun moveLeft() = move(head.x - 1, head.y)
    fun moveRight() = move(head.x + 1, head.y)
}

private fun Int.approach(other: Int) = when {
    this > other -> other + 1
    this < other -> other - 1
    else -> other
}