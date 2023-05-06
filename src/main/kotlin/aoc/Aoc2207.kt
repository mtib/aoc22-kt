package aoc

import utils.readAocFile

const val FilesystemSpace = 70000000
const val RequiredFreeSpaceForUpdate = 30000000

abstract class Node {
    abstract fun getSize(): Int
}

class File(size: Int) : Node() {
    private val internalSize = size
    override fun getSize(): Int {
        return internalSize
    }
}

class Directory(val name: String, var parent: Directory? = null) : Node() {
    init {
        parent?.addChild(this)
    }

    private val children = mutableListOf<Node>()
    private var lastSize: Int? = null

    fun addChild(node: Node) {
        children.add(node)
        lastSize = null
    }

    fun getChildrenList(): List<Node> {
        return children.toList()
    }

    fun findSubdirectory(subdirectoryName: String): Directory? {
        return children.find { it is Directory && it.name === subdirectoryName } as Directory?
    }

    override fun getSize(): Int {
        val size = children.sumOf { it.getSize() }
        lastSize = size
        return size
    }
}

fun isCdRoot(s: String): Boolean {
    return s == "$ cd /"
}

fun isCdUp(s: String): Boolean {
    return s == "$ cd .."
}

fun isCdFolder(s: String): Boolean {
    return s.startsWith("$ cd ") && !isCdRoot(s) && !isCdUp(s)
}

fun getCdFolder(s: String): String {
    return s.split(' ').last()
}

fun isLs(s: String): Boolean {
    return s == "$ ls"
}

fun isDirLs(s: String): Boolean {
    return s.startsWith("dir ")
}

val fileLsRegex = Regex("""^(\d+) ([a-z.]+)""")

fun isFileLs(s: String): Boolean {
    return s.matches(fileLsRegex)
}

fun getFileLs(s: String): File {
    val groups = fileLsRegex.matchEntire(s)!!
    return File(groups.groupValues[1].toInt())
}

fun findAtMost100kSizedDirectories(root: Directory): List<Directory> {
    val matchingDirs = mutableListOf<Directory>()

    fun recursiveFind(dir: Directory) {
        val totalSize = dir.getSize()
        if (totalSize < 100000) {
            matchingDirs.add(dir)
        }

        val children = dir.getChildrenList()
        children.forEach {
            if (it is Directory) {
                recursiveFind(it)
            }
        }
    }

    recursiveFind(root)

    return matchingDirs.toList()
}

fun findDirectoryClosestToSizeWithoutGoingUnder(root: Directory, sizeTarget: Int): Directory {
    var bestMatch = root

    fun recursiveFind(dir: Directory) {
        val diffSize = dir.getSize() - sizeTarget
        if (diffSize < 0) {
            return // All children will be too small as well
        }
        if (diffSize < bestMatch.getSize() - sizeTarget) {
            bestMatch = dir
        }
        val children = dir.getChildrenList()
        children.forEach {
            if (it is Directory) {
                recursiveFind(it)
            }
        }
    }

    recursiveFind(root)

    return bestMatch
}

fun aoc2207() {
    val commands = readAocFile(7).lines()
    val rootDir = Directory("/")
    var currentDir = rootDir

    for (command in commands) {
        when {
            isCdRoot(command) -> {
                currentDir = rootDir
            }

            isCdUp(command) -> {
                val parent = currentDir.parent
                if (parent != null) {
                    currentDir = parent
                } else {
                    throw Exception("Cannot move up, folder ${currentDir.name} does not have a parent")
                }
            }

            isCdFolder(command) -> {
                val folderName = getCdFolder(command)
                val existingSubdirectory = currentDir.findSubdirectory(folderName)
                val target = existingSubdirectory ?: Directory(folderName, parent = currentDir)
                currentDir = target
            }

            isLs(command) -> {}

            isDirLs(command) -> {}
            isFileLs(command) -> {
                val file = getFileLs(command)
                currentDir.addChild(file)
            }

            else -> {
                throw Exception("Unknown command: $command")
            }
        }
    }

    println("Total size: ${rootDir.getSize()}")

    val candidates = findAtMost100kSizedDirectories(rootDir)
    run {
        val names = candidates.map { "${it.name} (${it.getSize()})" }
        println("Candidates: ${names.joinToString()}")
    }
    run {
        val sizes = candidates.sumOf { it.getSize() }
        println("Sum of candidate sizes: $sizes")
    }

    // Part 2

    val currentFreeSpace = FilesystemSpace - rootDir.getSize()
    val spaceToBeFreed = RequiredFreeSpaceForUpdate - currentFreeSpace
    val bestMatch = findDirectoryClosestToSizeWithoutGoingUnder(rootDir, spaceToBeFreed)
    println("Best match for freeing $spaceToBeFreed is folder ${bestMatch.name} (${bestMatch.getSize()})")
}