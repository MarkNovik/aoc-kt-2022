import me.mark.partikt.par
import me.mark.partikt.rep
import me.mark.partikt.rev

@OptIn(ExperimentalStdlibApi::class)
object Day8 : AOC<Int, Int> {
    private fun isVisible(forest: List<List<Int>>, y: Int, x: Int): Boolean {
        val theTree = forest[y][x]
        val shorter = { it: Int -> it < theTree }
        val onLine = { list: List<Int> -> list[x] }
        return listOf(
            forest[y].slice(0..<x).all(shorter),
            forest[y].slice((x + 1)..<forest[y].size).all(shorter),
            forest.slice(0..<y).map(onLine).all(shorter),
            forest.slice((y + 1)..<forest.size).map(onLine).all(shorter),
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

    private fun scenicScore(forest: List<List<Int>>, y: Int, x: Int): Int {
        val theTree = forest[y][x]
        val onLine = List<Int>::get.rep(x) //{ list: List<Int> -> list[x] }
        return listOf(
            forest[y].slice(0..<x).asReversed(),
            forest[y].slice((x + 1)..<forest[y].size),
            forest.slice(0..<y).map(onLine).asReversed(),
            forest.slice((y + 1)..<forest.size).map(onLine)
        )
            .map(::countVisible.rep(theTree))
            .reduce(Int::times)
    }

    override fun part1(input: String): Int {
        val matrix = input.lines().map(rev<_, _, List<Int>>(String::map).par(Char::digitToInt))
        return matrix.indices
            .sumOf { y -> matrix[y].indices.count(::isVisible.par(matrix, y)) }
    }


    override fun part2(input: String): Int {
        val matrix = input.lines().map(rev<_, _, List<Int>>(String::map).par(Char::digitToInt))
        return matrix.withIndex().maxOf { (y, line) ->
            line.indices.maxOf(::scenicScore.par(matrix, y))
        }
    }
}