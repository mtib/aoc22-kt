# Advent of Code 2022 in Kotlin

This is not aiming to be a full [AOC](https://adventofcode.com/) in [Kotlin](https://kotlinlang.org/).
I'm only doing some of them to refresh my memory of Kotlin.

To run any implemented challenge run `gradle build` and run the resulting executable in the project root.
[Main.kt](./src/nativeMain/kotlin/Main.kt) multiplexes between the entrypoints using the first command-line argument.
Run the project without any arguments to list available entrypoints.

## Implemented challenges

### [Day 5: Supply Stacks](https://adventofcode.com/2022/day/5)

[Source](./src/nativeMain/kotlin/Aoc2205.kt)

```sh
# run (debug)
./build/bin/native/debugExecutable/aoc22-kt.kexe aoc2205
# run (release)
./build/bin/native/releaseExecutable/aoc22-kt.kexe aoc2205
```

### [Day 6: Tuning Trouble](https://adventofcode.com/2022/day/6)

[Source](./src/nativeMain/kotlin/Aoc2206.kt) (WIP)

```sh
# run (debug)
./build/bin/native/debugExecutable/aoc22-kt.kexe aoc2206
# run (release)
./build/bin/native/releaseExecutable/aoc22-kt.kexe aoc2206
```
