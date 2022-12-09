import FS.Dir
import FS.File

object Day7 : AOC<Int> {

    private tailrec fun getFileSystem(commands: List<String>, fs: Dir = Dir("/")): Pair<List<String>, Dir> {
        if (commands.isEmpty()) return emptyList<String>() to fs

        val cmd = commands.first()
        val cmds = commands.drop(1)

        return when {
            cmd == "$ cd .." -> cmds to Dir(fs.name, fs.files)
            cmd.startsWith("dir") -> getFileSystem(cmds, fs)
            cmd.startsWith("$ ls") -> getFileSystem(cmds, fs)
            cmd.startsWith("$ cd") -> {
                val (ncmds, sfs) = getFileSystem(cmds, Dir(cmd.drop(5), emptyList()))
                getFileSystem(ncmds, Dir(fs.name, fs.files + sfs))
            }

            else -> {
                val (size, _) = cmd.split(" ")
                getFileSystem(cmds, Dir(fs.name, fs.files + File(size.toInt())))
            }
        }
    }

    private fun buildFs(input: String): Dir =
        getFileSystem(input.lines().drop(2), Dir("/")).second

    override fun part1(input: String): Int = buildFs(input)
        .dirSizes
        .filter { it < 100_000 }
        .sum()

    override fun part2(input: String): Int {
        val root = buildFs(input)
        val availableSpace = 70000000 - root.size
        val spaceNeeded = 30000000 - availableSpace
        return root
            .dirSizes
            .filter { it >= spaceNeeded }
            .min()
    }
}

private sealed interface FS {

    val size: Int
    val dirSizes: List<Int>
        get() = when (this) {
            is File -> emptyList()
            is Dir -> files.fold(emptyList<Int>()) { a, d -> a + d.dirSizes } + size
        }

    class Dir(val name: String, val files: List<FS> = emptyList()) : FS {
        override val size: Int get() = files.sumOf(FS::size)
    }

    class File(override val size: Int) : FS
}

