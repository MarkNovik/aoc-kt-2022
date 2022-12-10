private typealias Stacks<T> = List<List<T>>

object Day5 : AOC<String, String> {
    private fun parseMoves(moves: String): List<Move> = moves
        .lines()
        .map { it.split(" ").mapNotNull(String::toIntOrNull) }
        .map { (amount, from, to) -> Move(amount, from - 1, to - 1) }

    private fun parseStackLine(line: String): List<Char> = line
        .windowed(3, 4)
        .map { it.singleOrNull(Char::isLetter) ?: ' ' }

    private fun parseStacks(input: String): List<ArrayDeque<Char>> {
        val size = input.lines().last().maxOf { it.digitToIntOrNull() ?: 0 }
        return input
            .lines()
            .asReversed()
            .drop(1)
            .map(::parseStackLine)
            .map { it.upSizeTo(size, ' ') }
            .rotateLeft()
            .map { it.filter(Char::isLetter) }
            .map(::ArrayDeque)
    }

    private fun parseInput(input: String): Pair<List<ArrayDeque<Char>>, List<Move>> = input
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
        return stackManipulation(stacks, moves, Stacks<Char>::moveOneByOne)
    }

    override fun part2(input: String): String {
        val (stacks, moves) = parseInput(input)
        return stackManipulation(stacks, moves, Stacks<Char>::moveStack)
    }
}

private data class Move(val amount: Int, val from: Int, val to: Int)

private fun <T> T.id() = this

private fun Stacks<Char>.move(move: Move, placingAction: (List<Char>) -> List<Char>) =
    mapIndexed { index, stack ->
        when (index) {
            move.from -> stack.dropLast(move.amount)
            move.to -> stack + placingAction(this[move.from].takeLast(move.amount))
            else -> stack
        }
    }

private fun Stacks<Char>.moveOneByOne(move: Move): Stacks<Char> = move(move, List<Char>::asReversed)
private fun Stacks<Char>.moveStack(move: Move): Stacks<Char> = move(move, List<Char>::id)

private fun <T> List<List<T>>.rotateLeft(): List<List<T>> = first().indices.map { r -> map { it[r] } }

private fun <T> List<T>.upSizeTo(size: Int, filler: T): List<T> =
    if (this.size >= size) this
    else this + List(size - this.size) { filler }