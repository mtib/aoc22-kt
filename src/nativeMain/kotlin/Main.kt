val commandMap = mapOf(
    "aoc22_03" to ::aoc22_03,
    "aoc22_04" to ::aoc22_04
)

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Unexpected number of arguments")
        return
    }
    val commandName = args[0]
    val command = commandMap[commandName];
    if (command == null) {
        println("Unknown command")
        return
    }

    command()
}