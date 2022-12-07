object Day7 : AOC<Int> {

    private fun buildFs(input: String): FS.Directory {
        val path = ArrayDeque<String>()
        val root = FS.Directory("root")
        var cursor = root
        val lines = ArrayDeque(input.lines())
        while (lines.isNotEmpty()) {
            val line = lines.removeFirst()
            if (line.startsWith("$")) {
                val command = line.slice(2..3)
                val arg = line.substringAfter(" ").substringAfter(" ", "")
                when (command) {
                    "cd" -> cursor = when (arg) {
                        "/" -> {
                            path.clear()
                            root
                        }

                        ".." -> {
                            path.removeLast()
                            cursor.parent ?: root
                        }

                        else -> {
                            path.addLast(arg)
                            cursor
                                .children
                                .filterIsInstance<FS.Directory>()
                                .find { it.name == arg }
                                ?: error("Unknown path `$arg`")
                        }
                    }

                    "ls" -> {
                        while (lines.firstOrNull()?.startsWith("$") == false) {
                            val (type, name) = lines.removeFirst().split(" ")
                            val size = type.toIntOrNull()
                            if (size != null) {
                                cursor.childFile(size)
                            } else if (type == "dir") {
                                cursor.childDir(name)
                            }
                        }
                    }

                    else -> error("Unknown command `$command`")
                }
            }
        }
        return root
    }

    override fun part1(input: String): Int {
        val root = buildFs(input)
        return root.sumSizesAtMost(100000)
    }

    override fun part2(input: String): Int {
        val root = buildFs(input)
        val availableSpace = 70000000 - root.size
        val spaceNeeded = 30000000 - availableSpace
        val allDirs = root.flattenChildren().filterIsInstance<FS.Directory>()
        return allDirs.filter { it.size >= spaceNeeded }.minOf { it.size }
    }

    private sealed interface FS {
        val parent: Directory?
        val size: Int

        class File(
            override val size: Int,
            override val parent: Directory? = null
        ) : FS

        class Directory(
            val name: String,
            override val parent: Directory? = null
        ) : FS {
            val children: List<FS> get() = childrenMutable.toList()
            val childrenDir get() = childrenMutable.filterIsInstance<Directory>()
            private val childrenMutable = mutableSetOf<FS>()
            override val size get() = children.sumOf { it.size }

            fun childFile(fileSize: Int): File {
                val file = File(fileSize, this)
                childrenMutable.add(file)
                return file
            }

            fun childDir(name: String): Directory {
                val dir = children
                    .filterIsInstance<Directory>()
                    .find { it.name == name }
                    ?: Directory(name, this)
                childrenMutable.add(dir)
                return dir
            }

            fun sumSizesAtMost(size: Int): Int {
                val mySize = if (this.size < size) this.size else 0
                return mySize + childrenDir.sumOf { it.sumSizesAtMost(size) }
            }

            fun flattenChildren(): List<FS> =
                children + childrenDir.flatMap(Directory::flattenChildren)


            override fun toString(): String = "${parent ?: ""}/$name"
        }
    }
}