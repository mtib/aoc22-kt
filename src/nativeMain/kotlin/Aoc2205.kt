import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import okio.use

/*
This file implements a solution to this challenge: https://adventofcode.com/2022/day/5
 */

typealias CraneName = Int
typealias Container = Char

class CraneState(description: String) {
    private val cranes: List<CraneName> = run {
        val descriptionLines = description.lines()
        val craneNames = descriptionLines[descriptionLines.size - 1]
        Regex("""\d""")
            .findAll(craneNames, 0).map { it.groupValues[0].toInt() }
            .toList()
    }
    /** Front is top, Last is bottom */
    private val craneStacks = run {
        val stacks = mutableMapOf<CraneName, ArrayDeque<Container>>()
        val descriptionLines = description.lines()
        for ((index, crane) in cranes.withIndex()) {
            stacks[crane] = ArrayDeque()
            val descriptionLineIndex = 1 + index * 4 // assumes single character crane names
            for (line in descriptionLines.slice(0 until descriptionLines.size - 1)) {
                if (line.length < descriptionLineIndex || line[descriptionLineIndex] == ' ') {
                    continue;
                }
                val container: Container = line[descriptionLineIndex];
                stacks[crane]!!.addLast(container);
            }
        }

        stacks.toMap()
    }

    /** Move top container from `from` to `to` */
    fun performMove(from: CraneName, to: CraneName) {
        val movedContainer = craneStacks[from]!!.removeFirst()
        craneStacks[to]!!.addFirst(movedContainer)
    }

    /** Move multiple containers at once without changing their order (PART 2) */
    fun performMoveMultiple(from: CraneName, to: CraneName, amount: Int) {
        val movedContainers = craneStacks[from]!!.take(amount)
        for (i in 0 until amount) {
            craneStacks[from]!!.removeFirst()
        }
        craneStacks[to]!!.addAll(0, movedContainers)
    }

    private fun readTop(): List<Container> {
        return cranes.map { craneStacks[it]!!.first() }
    }

    fun readTopString(): String {
        return readTop().joinToString(separator = "")
    }
}

class Move(val origin: CraneName, val destination: CraneName, val amount: Int) {
    init {
        if (amount <= 0) {
            throw Exception("Invalid amount: $amount")
        }
    }
}

fun aoc2205() {
    val data = FileSystem.SYSTEM.source("./src/nativeMain/resources/aoc22_05_input.txt".toPath()).use { source ->
        source.buffer().use { buffer ->
            buffer.readUtf8()
        }
    }
    val craneStateDescription = run {
        var numNewlines = 0
        data.takeWhile { char ->
            when (char) {
                '\n' -> numNewlines += 1
                else -> numNewlines = 0
            }
            numNewlines < 2
        }.let { it.slice(0 until it.length - 1) }
    }

    val moves = run {
        val moves = mutableListOf<Move>()
        data.lines().forEach { line ->
            val match = Regex("""move (\d+) from (\d) to (\d)""").matchEntire(line);
            match?.run {
                val numCrates = groupValues[1].toInt()
                val origin: CraneName = groupValues[2].toInt()
                val destination: CraneName = groupValues[3].toInt()

                moves.add(Move(origin, destination, numCrates))
            }
        }
        moves.toList()
    }

    val craneStateFirst = CraneState(craneStateDescription);
    moves.forEach { move ->
        for (i in 0 until move.amount) {
            craneStateFirst.performMove(move.origin, move.destination)
        }
     }
    println("1st part result: ${craneStateFirst.readTopString()}")

    val craneStateSecond = CraneState(craneStateDescription);
    moves.forEach { move ->
        craneStateSecond.performMoveMultiple(move.origin, move.destination, move.amount)
    }
    println("2nd part result: ${craneStateSecond.readTopString()}")
}