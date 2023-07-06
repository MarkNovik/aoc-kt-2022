import me.mark.partikt.par
import me.mark.partikt.rep
import me.mark.partikt.rev

private typealias Stacks<T> = List<List<T>>

object Day5 : AOC<String, String> {
    private fun parseMoves(moves: String): List<Move> = moves
        .lines()
        .map { it.split(" ").mapNotNull(String::toIntOrNull) }
        .map { (amount, from, to) -> Move(amount, from - 1, to - 1) }

    private fun parseStackLine(line: String): List<Char> = line
        .windowed(3, 4)
        .map { it.singleOrNull(Char::isLetter) ?: ' ' }

    private fun parseStacks(input: String): List<List<Char>> {
        val size = input.lines().last().maxOf { it.digitToIntOrNull() ?: 0 }
        return input
            .lines()
            .asReversed()
            .drop(1)
            .map(::parseStackLine)
            .map(rev<_, _, Char, _>(::upSizeTo).par(' ', size))
            .let(::rotateLeft)
            .map(List<Char>::filter.rep(Char::isLetter))   // { it.filter(Char::isLetter) }
    }

    private fun parseInput(input: String): Pair<List<List<Char>>, List<Move>> = input
        .split(System.lineSeparator().repeat(2))
        .let { (stacks, moves) ->
            parseStacks(stacks) to parseMoves(moves)
        }

    private fun stackManipulation(
        stacks: Stacks<Char>,
        moves: List<Move>,
        movingAction: (Stacks<Char>, Move) -> Stacks<Char>
    ): String =
        moves.fold(stacks, movingAction).map(List<Char>::last).joinToString("")


    override fun part1(input: String): String {
        val (stacks, moves) = parseInput(input)
        return stackManipulation(stacks, moves, ::moveOneByOne)
    }

    override fun part2(input: String): String {
        val (stacks, moves) = parseInput(input)
        return stackManipulation(stacks, moves, ::moveStack)
    }
}

private data class Move(val amount: Int, val from: Int, val to: Int)

private fun <T> id(t: T) = t

private fun move(stacks: Stacks<Char>, move: Move, placingAction: (List<Char>) -> List<Char>) =
    stacks.mapIndexed { index, stack ->
        when (index) {
            move.from -> stack.dropLast(move.amount)
            move.to -> stack + placingAction(stacks[move.from].takeLast(move.amount))
            else -> stack
        }
    }

private fun moveOneByOne(stacks: Stacks<Char>, move: Move): Stacks<Char> = move(stacks, move, List<Char>::asReversed)
private fun moveStack(stacks: Stacks<Char>, move: Move): Stacks<Char> = move(stacks, move, ::id)

private fun <T> rotateLeft(lists: List<List<T>>): List<List<T>> =
    lists.first().indices.map { r -> lists.map(List<T>::get.rep(r)) }

private fun <T> upSizeTo(list: List<T>, size: Int, filler: T): List<T> =
    if (list.size >= size) list
    else list + List(size - list.size) { filler }