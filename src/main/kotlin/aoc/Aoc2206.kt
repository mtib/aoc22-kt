package aoc

import utils.readAocFile

fun findMarker(data: String, markerLength: Int): Int {
    for (i in markerLength until data.length) {
        val window = data.slice(i - markerLength until i)
        if (window.toSet().size == markerLength) {
            return i
        }
    }
    throw Exception("Marker not found")
}

fun aoc2206() {
    val data = readAocFile(6)

    fun printMarker(index: Int, length: Int) {
        println("@${index}: ${data.slice(index - length until index)}")
    }

    val firstMarker = findMarker(data, 4)
    printMarker(firstMarker, 4)

    val secondMarker = findMarker(data, 14)
    printMarker(secondMarker, 14)
}