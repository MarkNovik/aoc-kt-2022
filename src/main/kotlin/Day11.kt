object Day11 : AOC<Long, Long> {
    override fun part1(input: String): Long = parseMonkeys(input)
        .repeat(20, ::roundChill)
        .map { it.inspected }
        .sortedDescending()
        .take(2)
        .reduce(Long::times)

    override fun part2(input: String): Long {
        val monkeys = parseMonkeys(input)
        val base = monkeys.fold(1L) { acc, monkey -> acc * monkey.testDivisibleBy }
        return monkeys
            .repeat(10000) { roundPanic(it, base) }
            .map { it.inspected }
            .sortedDescending()
            .take(2)
            .reduce(Long::times)
    }
}

private data class Monkey(
    val id: Long,
    val items: List<Long>,
    val operation: (Long) -> Long,
    val testDivisibleBy: Long,
    val ifTrueThrowTo: Long,
    val ifFalseThrowTo: Long,
    val inspected: Long = 0
) {
    fun test(item: Long) = item % testDivisibleBy == 0L
}

private operator fun <E> List<E>.component6(): E = get(5)

private fun String.extractNumbers() = split(Regex("""\D+""")).mapNotNull(String::toLongOrNull)
private fun String.extractNumber() = extractNumbers().single()
private fun extractOperation(line: String): (Long) -> Long {
    val (sign, operand) = line.split(" ").takeLast(2)
    val op: (Long, Long) -> Long = when (sign) {
        "*" -> Long::times
        "+" -> Long::plus
        else -> error("Unexpected operation $sign")
    }
    if (operand == "old") return {
        op(it, it)
    }
    else return {
        op(it, operand.toLong())
    }
}

private fun parseMonkey(input: String): Monkey {
    val (
        id,
        items,
        operation,
        test,
        ifTrue,
        ifFalse
    ) = input.lines()
    return Monkey(
        id = id.extractNumber(),
        items = items.extractNumbers(),
        operation = extractOperation(operation),
        testDivisibleBy = test.extractNumber(),
        ifTrueThrowTo = ifTrue.extractNumber(),
        ifFalseThrowTo = ifFalse.extractNumber(),
    )
}

private fun parseMonkeys(input: String): Set<Monkey> = input
    .split(System.lineSeparator().repeat(2))
    .map(::parseMonkey)
    .toSet()

private tailrec fun round(monkeys: Set<Monkey>, relief: (Long) -> Long, i: Long = 0): Set<Monkey> {
    if (i >= monkeys.size) return monkeys

    val cm = monkeys.single { it.id == i }
    val (inspectedTrue, inspectedFalse) = cm.items
        .map { item -> relief(cm.operation(item)) }
        .partition(cm::test)

    return round(
        monkeys.map { monkey ->
            when (monkey.id) {
                i -> monkey.copy(
                    items = emptyList(),
                    inspected = monkey.inspected + (inspectedTrue.size + inspectedFalse.size)
                )

                cm.ifTrueThrowTo -> monkey.copy(
                    items = monkey.items + inspectedTrue
                )

                cm.ifFalseThrowTo -> monkey.copy(
                    items = monkey.items + inspectedFalse
                )

                else -> monkey
            }
        }.toSet(),
        relief,
        i + 1
    )
}

private fun roundChill(monkeys: Set<Monkey>): Set<Monkey> = round(
    monkeys,
    { it / 3 },
    0
)

private fun roundPanic(
    monkeys: Set<Monkey>,
    base: Long
): Set<Monkey> {
    return round(
        monkeys,
        { it % base },
        0
    )
}

private tailrec fun <T> T.repeat(times: Long, action: (T) -> T): T =
    if (times == 0L) this
    else action(this).repeat(times - 1, action)