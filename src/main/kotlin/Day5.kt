import java.util.*

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

    private fun parseStacks(stacksStr: String): List<Stack<Char>> {
        val lines = stacksStr.lines()
        val indices = lines.last()
        val stacks = List(indices.maxOf { it.digitToIntOrNull() ?: 0 }) { Stack<Char>() }
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
                stacks[index].push(char)
            }
        }
        return stacks
    }

    private fun parseInput(input: String): Pair<List<Stack<Char>>, List<Move>> = input
        .split(System.lineSeparator().repeat(2))
        .let { (stacks, moves) ->
            parseStacks(stacks) to parseMoves(moves)
        }

    private fun List<Stack<Char>>.move(move: Move) {
        val (amount, from, to) = move
        val temp = Stack<Char>()
        repeat(amount) {
            temp.push(this[from].pop())
        }
        repeat(amount) {
            this[to].push(temp.pop())
        }
    }

    override fun part1(input: String): String {
        val (stacks, moves) = parseInput(input)
        moves.forEach { (amount, from, to) ->
            repeat(amount) {
                stacks[to].push(stacks[from].pop())
            }
        }
        return stacks.map(Stack<Char>::pop).joinToString("")
    }

    override fun part2(input: String): String {
        val (stacks, moves) = parseInput(input)
        moves.forEach { move ->
            stacks.move(move)
        }
        return stacks.map(Stack<Char>::pop).joinToString("")
    }


    private data class Move(
        val amount: Int,
        val from: Int,
        val to: Int
    )
}