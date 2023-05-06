import aoc.aoc2205
import aoc.aoc2206

val commandMap = mapOf(
    "aoc2205" to ::aoc2205, "aoc2206" to ::aoc2206
)

fun printHelp() {
    println("Usage: <entrypoint>")
    println("")
    println("Available entrypoints:")
    commandMap.keys.forEach { println(" - \"$it\"") }
}

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Unexpected number of arguments")
        printHelp()
        return
    }
    val entrypointName = args[0]
    val entrypoint = commandMap[entrypointName]
    if (entrypoint == null) {
        println("Unknown entrypoint: $entrypointName")
        printHelp()
        return
    }

    entrypoint()
}