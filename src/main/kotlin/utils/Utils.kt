package utils

import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import okio.use

fun readFullFile(path: String): String {
    return FileSystem.SYSTEM.source(path.toPath()).use { source ->
        source.buffer().use { buffer ->
            buffer.readUtf8()
        }
    }
}

fun readAocFile(day: Int): String {
    val path = "./resources/aoc22_%02d_input.txt".format(day)
    return readFullFile(path)
}