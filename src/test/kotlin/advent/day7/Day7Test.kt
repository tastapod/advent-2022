package advent.day7

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class Day7Test {
    @Test
    fun `moves to tree root`() {
        // given
        val a: Dir
        val root = Dir("/", null).apply {
            contents.addAll(
                listOf(
                    Dir("a", this).also { a = it },
                    File("b.txt", 14848514),
                    File("c.dat", 8504156),
                    Dir("d", this)))
        }
        val shell = Shell(root).apply { currentDir = a }

        // when
        shell.executeCommand("cd /")

        // then
        assertEquals(root, shell.currentDir)
    }

    @Test
    fun `adds list of entries to current directory`() {
        // given
        val shell = Shell()

        // when
        shell.executeCommand(
            "ls", """
                dir a
                14848514 b.txt
                8504156 c.dat
                dir d
            """.trimIndent().split("\n"))

        // then
        val expectedContents = mutableSetOf(
            Dir("a", shell.fs.root), File("b.txt", 14848514), Dir("d", shell.fs.root), File("c.dat", 8504156))

        assertEquals(expectedContents, shell.fs.root.contents)
    }

    @Test
    fun `calculates size of directory contents`() {
        // given
        val a = Dir("a").apply {
            contents.addAll(
                listOf(
                    File("a1", 100), File("a2", 200), Dir(
                        "aa", this, mutableSetOf(
                            File("aa1", 10),
                            File("aa2", 20),
                        ))),
            )
        }

        // then
        assertEquals(330, a.size)
    }

    @Test
    fun `sums sizes of small directories`() {
        // given
        val fs = Filesystem(Dir("/", null).apply {
            contents.addAll(
                listOf(
                    Dir("a", this).apply {
                        contents.addAll(
                            listOf(
                                File("a1", 100),
                                File("a2", 200),
                                Dir(
                                    "aa", this, mutableSetOf(
                                        File("aa1", 10),
                                        File("aa2", 20),
                                    )),
                            ))
                    },
                    Dir(
                        "b", this, mutableSetOf(
                            File("b1", 30),
                            File("b2", 40),
                        )),
                ))
        })

        assertEquals(100, fs.sumDirectoriesBelowSize(100))
    }

    @Test
    fun `changes into directory`() {
        // given
        val root = Dir("/", null).apply {
            contents.addAll(
                listOf(
                    Dir("a", this), File("b.txt", 14848514), File("c.dat", 8504156), Dir("d", this)))
        }
        val shell = Shell(root)

        // when
        shell.executeCommand("cd a")

        // then
        assertEquals("a", shell.currentDir.name)
        assertNotEquals(shell.fs.root, shell.currentDir)
    }

    @Test
    fun `changes to parent directory`() {
        // given
        val root = Dir("/", null).apply {
            contents.addAll(
                listOf(
                    Dir("a", this), File("b.txt", 14848514), File("c.dat", 8504156), Dir("d", this)))
        }
        val shell = Shell(root)

        shell.executeCommand("cd a")
        assertEquals("a", shell.currentDir.name)
        assertNotEquals(shell.fs.root, shell.currentDir)

        // when
        shell.executeCommand("cd ..")

        // then
        assertEquals(shell.fs.root, shell.currentDir)
    }

    @Test
    fun `parses command sequence`() {
        // given
        val input = """
            $ cd /
            $ ls
            dir a
            10 b.txt
            20 c.dat
            dir d
            $ cd a
            $ ls
            dir e
            30 f
            40 g
            $ cd ..
        """.trimIndent()

        val shell = Shell()

        // when
        val commands = shell.parseCommands(input)

        // then
        assertEquals(
            listOf(
                CommandBlock("cd /"),
                CommandBlock("ls", listOf("dir a", "10 b.txt", "20 c.dat", "dir d")),
                CommandBlock("cd a"),
                CommandBlock("ls", listOf("dir e", "30 f", "40 g")),
                CommandBlock("cd ..")
            ), commands)
    }

    private val sampleInput = """
            $ cd /
            $ ls
            dir a
            14848514 b.txt
            8504156 c.dat
            dir d
            $ cd a
            $ ls
            dir e
            29116 f
            2557 g
            62596 h.lst
            $ cd e
            $ ls
            584 i
            $ cd ..
            $ cd ..
            $ cd d
            $ ls
            4060174 j
            8033020 d.log
            5626152 d.ext
            7214296 k
        """.trimIndent()


    @Test
    fun `calculates small directories in sample`() {
        val shell = Shell().executeScript(sampleInput)
        assertEquals(95437, shell.fs.sumDirectoriesBelowSize(100_000))
    }

    @Test
    fun `clears smallest directory to free up space`() {
        // given
        val capacity = 70_000_000
        val spaceRequired = 30_000_000
        val shell = Shell(Filesystem(capacity = capacity)).executeScript(sampleInput)

        // when
        val smallestDir = shell.fs.smallestDirToEnsureSpace(spaceRequired)

        // then
        assertEquals("d", smallestDir.name)
        assertEquals(24933642, smallestDir.size)
    }
}
