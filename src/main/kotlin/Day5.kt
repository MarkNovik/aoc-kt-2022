import kotlin.collections.ArrayDeque

object Day5 : AOC<String> {

    private fun parseMoves(moves: String) =
        Regex("""move (\d+) from (\d+) to (\d+)""")
            .findAll(moves)
            .map {
                val (_, amount, from, to) = it.groupValues
                Move(
                    amount.toInt(),
                    from.toInt() - 1,
                    to.toInt() - 1
                )
            }.toList()

    private fun parseStacks(stacksStr: String): List<ArrayDeque<Char>> {
        val lines = stacksStr.lines()
        val indices = lines.last()
        val stacks = List(indices.maxOf { it.digitToIntOrNull() ?: 0 }) { ArrayDeque<Char>() }
        val stacksMap = lines.dropLast(1).map(String::withIndex).map {
            it.mapNotNull { (i, v) ->
                indices.getOrNull(i)?.digitToIntOrNull()?.let { stack ->
                    v.takeIf(Char::isLetter)?.let { crate ->
                        IndexedValue(stack - 1, crate)
                    }
                }
            }
        }
        stacksMap.asReversed().forEach { line ->
            line.forEach { (index, char) ->
                stacks[index].addLast(char)
            }
        }
        return stacks
    }

    private fun parseInput(input: String): Pair<List<ArrayDeque<Char>>, List<Move>> = input
        .split(System.lineSeparator().repeat(2))
        .let { (stacks, moves) ->
            parseStacks(stacks) to parseMoves(moves)
        }

    private fun List<ArrayDeque<Char>>.move(move: Move) {
        val (amount, from, to) = move
        val temp = ArrayDeque<Char>()
        repeat(amount) {
            temp.addLast(this[from].removeLast())
        }
        repeat(amount) {
            this[to].addLast(temp.removeLast())
        }
    }

    override fun part1(input: String): String {
        val (stacks, moves) = parseInput(input)
        moves.forEach { (amount, from, to) ->
            repeat(amount) {
                stacks[to].addLast(stacks[from].removeLast())
            }
        }
        return stacks.map(List<Char>::last).joinToString("")
    }

    override fun part2(input: String): String {
        val (stacks, moves) = parseInput(input)
        moves.forEach { move ->
            stacks.move(move)
        }
        return stacks.map(ArrayDeque<Char>::removeLast).joinToString("")
    }

    private data class Move(
        val amount: Int,
        val from: Int,
        val to: Int
    )
}