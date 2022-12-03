import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.div
import kotlin.io.path.readText

val resources = Path("src") / "main" / "resources"
fun read(path: Path): String = path.readText()

private val String.txt get() = "$this.txt"

private val String.spaceBeforeDigit: String
    get() = replace(Regex("""(\w)(\d)""")) {
        val (_, w, d) = it.groupValues
        "$w $d"
    }

fun main() {
    AOC::class
        .sealedSubclasses
        .mapNotNull {
            val obj = it.objectInstance ?: return@mapNotNull null
            val name = it.simpleName ?: return@mapNotNull null
            name to obj
        }
        .forEach { (name, obj) ->
            println(
                "${name.spaceBeforeDigit}:"
            )
            val input = read(resources / name.txt)
            println("\tPart 1: ${obj.part1(input)}")
            println("\tPart 2: ${obj.part2(input)}")
        }
}


