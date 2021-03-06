package day5

import lib.Line
import lib.Point2D
import lib.loadResourceAsString

private val input = loadResourceAsString("text/day5")

fun main() {
    val ventLines = parseInput()
    val topology = Topology(ventLines)

    part1(topology)
    part2(topology)
}

private fun parseInput(): List<Line> {
    return input.lines().map { line ->
        val (startX, startY, endX, endY) = line.split(" -> ").flatMap { it.split(",").map(String::toInt) }
        Line(Point2D(startX, startY), Point2D(endX, endY))
    }
}

private fun part1(topology: Topology) {
    val overlaps = topology.toVentCounts(true).values.count { it > 1 }
    println("Overlap count (cardinal only) $overlaps")
}

private fun part2(topology: Topology) {
    val overlaps = topology.toVentCounts(false).values.count { it > 1 }
    println("Overlap count (all) $overlaps")
}

private data class Topology(val width: Int, val height: Int, val ventLines: List<Line>) {
    constructor(ventLines: List<Line>) : this(
        ventLines.maxOf { listOf(it.start.x, it.end.x).maxOrNull()!! } + 1,
        ventLines.maxOf { listOf(it.start.y, it.end.y).maxOrNull()!! } + 1,
        ventLines
    )

    fun toVentCounts(cardinalLinesOnly: Boolean): Map<Point2D, Int> {
        return ventLines
            .filter { it.start.x == it.end.x || it.start.y == it.end.y || !cardinalLinesOnly }
            .flatMap(Line::toSequence)
            .groupBy { it }
            .mapValues { it.value.count() }
    }
}

