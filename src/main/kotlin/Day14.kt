import kotlin.math.max
import kotlin.math.min

private typealias RockMap = MutableMap<Int, MutableSet<Int>>

object Day14 : AOC<Int, Int> {
    private fun parseRocks(input: String): Triple<Set<Pair<Int, Int>>, Pair<Int, Int>, Int> {
        val paths = input
            .lines()
            .map { path ->
                path
                    .split(" -> ").map { coordinate ->
                        coordinate
                            .split(",").let { (a, b) ->
                                a.toInt() to b.toInt()
                            }
                    }
            }
        val maxX = paths.maxOf { it.maxOf(Pair<Int, Int>::first) }
        val minX = paths.minOf { it.minOf(Pair<Int, Int>::first) }
        val maxY = paths.maxOf { it.maxOf(Pair<Int, Int>::second) }
        val rocks = paths.flatMap(::fullSteps).toSet()

        return Triple(rocks, minX to maxX, maxY)
    }

    override fun part1(input: String): Int {
        val (rockset, _, height) = parseRocks(input)
        val rocks = rockset.toRockMap()
        var sandCount = 0
        var currX = 500
        var currY = 0
        while (true) {
            val (newX, newY) = when (val np = rocks.tryMoveDown(currX, currY)) {
                null -> {
                    rocks.getOrPut(currX) { mutableSetOf() }.add(currY)
                    currX = 500
                    currY = 0
                    sandCount++
                    continue
                }

                else -> np
            }
            currX = newX
            currY = newY
            if (currY > height) return sandCount
        }
    }

    override fun part2(input: String): Int {
        val (rockset, _, height) = parseRocks(input)
        val rocks = rockset.toRockMap()
        var sandCount = 0
        var currX = 500
        var currY = 0
        while (true) {
            val coords = rocks.tryMoveDown(currX, currY, height + 2)
            if (coords == null) {
                sandCount++
                if (currX == 500 && currY == 0) return sandCount
                rocks.getOrPut(currX) { mutableSetOf() }.add(currY)
                currX = 500
                currY = 0
                continue
            }
            currX = coords.first
            currY = coords.second
        }
    }
}

private fun Set<Pair<Int, Int>>.toRockMap(): RockMap = groupBy { it.first }
    .mapValues { (_, v) -> v.map { it.second }.toMutableSet() }
    .toMutableMap()


private fun fullSteps(list: List<Pair<Int, Int>>): List<Pair<Int, Int>> = buildList {
    list.zipWithNext { (x1, y1), (x2, y2) ->
        val xs = min(x1, x2)..max(x1, x2)
        val ys = min(y1, y2)..max(y1, y2)

        xs.forEach { x ->
            ys.forEach { y ->
                add(x to y)
            }
        }
    }
}

private fun RockMap.tryMoveDown(x: Int, y: Int, floor: Int? = null): Pair<Int, Int>? {
    val down = (x to (y + 1)).takeIf { (_, y) -> floor == null || y < floor }
    val downLeft = ((x - 1) to (y + 1)).takeIf { (_, y) -> floor == null || y < floor }
    val downRight = ((x + 1) to (y + 1)).takeIf { (_, y) -> floor == null || y < floor }
    return listOf(down, downLeft, downRight)
        .firstOrNull { it != null && (this[it.first]?.contains(it.second) != true) }
}