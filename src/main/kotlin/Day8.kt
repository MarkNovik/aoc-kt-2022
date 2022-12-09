@OptIn(ExperimentalStdlibApi::class)
object Day8 : AOC<Int> {
    private fun List<List<Int>>.isVisible(y: Int, x: Int): Boolean {
        val theTree = this[y][x]
        val shorter = { it: Int -> it < theTree }
        val onLine = { list: List<Int> -> list[x] }
        return listOf(
            this[y].slice(0..<x).all(shorter),
            this[y].slice((x + 1)..<this[y].size).all(shorter),
            this.slice(0..<y).map(onLine).all(shorter),
            this.slice((y + 1)..<size).map(onLine).all(shorter),
        ).any { it }
    }

    private fun countVisible(forList: List<Int>, fromHeight: Int): Int {
        var count = 0
        for (height in forList) {
            count++
            if (height >= fromHeight) break
        }
        return count
    }

    private fun List<List<Int>>.scenicScore(y: Int, x: Int): Int {
        val theTree = this[y][x]
        val onLine = { list: List<Int> -> list[x] }
        return listOf(
            this[y].slice(0..<x).asReversed(),
            this[y].slice((x + 1)..<this[y].size),
            slice(0..<y).map(onLine).asReversed(),
            slice((y + 1)..<size).map(onLine)
        )
            .map { countVisible(it, theTree) }
            .reduce(Int::times)
    }

    override fun part1(input: String): Int {
        val matrix = input.lines().map { it.map(Char::digitToInt) }
        return matrix.indices
            .sumOf { y ->
                matrix[y].indices
                    .count { x ->
                        matrix.isVisible(y, x)
                    }
            }
    }


    override fun part2(input: String): Int {
        val matrix = input.lines().map { it.map(Char::digitToInt) }
        return matrix.withIndex().maxOf { (y, line) ->
            line.indices.maxOf { x ->
                matrix.scenicScore(y, x)
            }
        }
    }
}