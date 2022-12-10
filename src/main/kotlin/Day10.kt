import Command.Addx
import Command.Noop

object Day10 : AOC<Int, String> {

    private val indices = listOf(20, 60, 100, 140, 180, 220)

    private fun simulateCpu(
        commands: List<Command>,
    ): Pair<Int, String> {
        var result = 0
        val screen = StringBuilder()
        val cmds = commands.toMutableList()
        var cycle = 0
        var acc = 1
        fun addIfNeed() {
            if (cycle in indices) result += acc * cycle
        }

        fun draw() {
            val sprite = (acc - 1)..(acc + 1)
            if (((cycle - 1) % 40) in sprite) screen.append('#')
            else screen.append('.')
        }
        while (cmds.isNotEmpty()) {
            when (val cmd = cmds.removeFirst()) {
                Noop -> {
                    cycle++
                    draw()
                    addIfNeed()
                }

                is Addx -> {
                    cycle++
                    draw()
                    addIfNeed()
                    cycle++
                    addIfNeed()
                    draw()
                    acc += cmd.x
                }
            }
        }
        return result to screen.chunked(40).joinToString("\n")
    }

    override fun part1(input: String): Int = simulateCpu(
        commands = input.lines().map {
            if (it == "noop") Noop
            else Addx(it.split(" ").last().toInt())
        }
    ).first


    override fun part2(input: String): String = "\n" + simulateCpu(
        commands = input.lines().map {
            if (it == "noop") Noop
            else Addx(it.split(" ").last().toInt())
        }
    ).second
}

private sealed interface Command {

    @JvmInline
    value class Addx(val x: Int) : Command
    object Noop : Command
}

