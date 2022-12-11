import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.div
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

val resources = Path("src") / "main" / "resources"
fun read(path: Path): String = path.readText()

private val String.txt get() = "$this.txt"

private val String.spaceBeforeDigit: String
    get() = replace(Regex("""(\w)(\d)""")) {
        val (_, w, d) = it.groupValues
        "$w $d"
    }

inline fun tryRun(block: () -> Any): String = try {
    block().toString()
} catch (e: NotImplementedError) {
    "Not Implemented"
} /*catch (t: Throwable) {
    "Error: $t"
}*/

@OptIn(ExperimentalTime::class)
inline fun timed(block: () -> Any): String {
    val (v, t) = measureTimedValue(block)
    return "$v, $t"
}

fun aoc() {
    AOC::class
        .sealedSubclasses
        .mapNotNull {
            val obj = it.objectInstance ?: return@mapNotNull null
            val name = it.simpleName ?: return@mapNotNull null
            name to obj
        }
        .forEach { (name, obj) ->
            print("${name.spaceBeforeDigit}:")
            val path = resources / name.txt
            if (!path.exists()) return@forEach println(" No File")
            else println()

            val input = path.readText()
            println("\tPart 1: ${tryRun { timed { obj.part1(input) } }}")
            println("\tPart 2: ${tryRun { timed { obj.part2(input) } }}")
        }
}

fun main() = aoc()