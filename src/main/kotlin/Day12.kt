object Day12 : AOC<Int, Int> {
    override fun part1(input: String): Int {
        val (list, from, to) = parseInput(input)
        return bfs(from) {
            val (x, y) = it
            val height = list[it]
            listOf(
                Point(x + 1, y),
                Point(x - 1, y),
                Point(x, y + 1),
                Point(x, y - 1),
            ).filter { (x, y) -> y in list.indices && x in list[y].indices }
                .filter { list[it] <= height + 1 }
        }.find { (_, point) -> point == to }?.index ?: -1
    }

    override fun part2(input: String): Int {
        val (list, _, from) = parseInput(input)
        return bfs(from) {
            val (x, y) = it
            val height = list[it]
            listOf(
                Point(x + 1, y),
                Point(x - 1, y),
                Point(x, y + 1),
                Point(x, y - 1),
            ).filter { (x, y) -> y in list.indices && x in list[y].indices }
                .filter { list[it] >= height - 1 }
        }.filter { (_, point) -> list[point] == 0 }.minOf { it.index }
    }
}

private data class Point(
    val x: Int,
    val y: Int
)

private fun parseInput(input: String): Triple<List<List<Int>>, Point, Point> {
    var s: Point? = null
    var e: Point? = null
    return Triple(
        input
            .lines()
            .mapIndexed { y, str ->
                str.mapIndexed { x, it ->
                    when (it) {
                        'S' -> {
                            s = Point(x, y)
                            0
                        }

                        'E' -> {
                            e = Point(x, y)
                            26
                        }

                        else -> it - 'a'
                    }
                }
            },
        s!!,
        e!!
    )
}

private operator fun List<List<Int>>.get(p: Point) = this[p.y][p.x]

private fun <T> bfs(start: T, next: (T) -> Iterable<T>): Sequence<IndexedValue<T>> = sequence {
    val seen = mutableSetOf(start)
    val queue = ArrayDeque(listOf(IndexedValue(0, start)))
    while (queue.isNotEmpty()) {
        val a = queue.removeFirst()
        yield(a)
        for (b in next(a.value)) {
            if (seen.add(b)) {
                queue.add(IndexedValue(a.index + 1, b))
            }
        }
    }
}