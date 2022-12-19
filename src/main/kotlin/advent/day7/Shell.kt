package advent.day7

data class CommandBlock(val command: String, val output: List<String> = emptyList())

class Shell(val fs: Filesystem = Filesystem(Dir("/")), var currentDir: Dir = fs.root) {

    constructor(root: Dir) : this(Filesystem(root))

    fun executeCommand(command: String, output: List<String> = emptyList()) =
        executeCommand(CommandBlock(command, output))

    private fun executeCommand(commandBlock: CommandBlock) {
        with(commandBlock) {
            when {
                command == "ls" -> addContents(output)
                command.startsWith("cd") -> currentDir =
                    when (val dir = command.split(" ")[1]) {
                        "/" -> fs.root
                        ".." -> currentDir.up ?: fs.root
                        else -> currentDir.contents.find { it.name == dir } as Dir
                    }
            }
        }
    }

    private fun addContents(lines: List<String>) {
        lines.forEach { line ->
            val (first, second) = line.split(" ")
            currentDir.contents.add(if (first == "dir") Dir(second, currentDir) else File(second, first.toInt()))
        }
    }

    fun parseCommands(input: String) =
        input.split(Regex("\n?\\$ ")).drop(1)
            .map { lines ->
                lines.split("\n")
                    .let { CommandBlock(it[0], it.drop(1)) }
            }

    fun executeScript(input: String) =
        apply { parseCommands(input).forEach { executeCommand(it) } }
}
