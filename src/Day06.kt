import Direction.*
import java.awt.Point
import kotlin.math.abs

enum class Direction { UP, DOWN, LEFT, RIGHT, }

fun main() {

    fun findNextBlocker(
        gridSize: Point,
        points: List<Point>,
        position: Pair<Point, Direction?>
    ): Pair<Point, Direction?> {
        val currentPosition = position.first
        return when (position.second) {
            UP -> {
                val point = points.filter { it.y < currentPosition.y && it.x == currentPosition.x }.maxByOrNull { it.y }
                if (point != null) {
                    Point(point.x, point.y + 1) to RIGHT
                } else Point(currentPosition.x, 0) to null
            }
            DOWN -> {
                val point = points.filter { it.y > currentPosition.y && it.x == currentPosition.x }.minByOrNull { it.y }
                if (point != null) {
                    Point(point.x, point.y - 1) to LEFT
                } else Point(currentPosition.x, gridSize.y) to null
            }
            LEFT -> {
                val point = points.filter { it.x < currentPosition.x && it.y == currentPosition.y }.maxByOrNull { it.x }
                if (point != null) {
                    Point(point.x + 1, point.y) to UP
                } else Point(0, currentPosition.y) to null
            }
            RIGHT -> {
                val point = points.filter { it.x > currentPosition.x && it.y == currentPosition.y }.minByOrNull { it.x }
                if (point != null) {
                    Point(point.x - 1, point.y) to DOWN
                } else Point(gridSize.x, currentPosition.y) to null
            }
            else -> position
        }
    }

    fun part1(input: List<String>): Int {
        val gridSize = Point(input.first().lastIndex, input.lastIndex)
        val blockers = mutableListOf<Point>()
        var currentPosition: Pair<Point, Direction?> = Pair(Point(0, 0), UP)
        val pointsVisited = mutableListOf<Point>()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '#') {
                    blockers.add(Point(x, y))
                }
            }
            if (line.contains('^')) {
                val x = line.indexOfFirst { it == '^' }
                currentPosition = Pair(Point(x, y), UP)
            }
        }
        while (currentPosition.second != null) {
            val nextPosition = findNextBlocker(gridSize, blockers.toList(), currentPosition)
            val pointsTouched = currentPosition.first.pointsTo(nextPosition.first)
            pointsVisited.addAll(pointsTouched)
            currentPosition = nextPosition
        }
        return pointsVisited.toSet().count()
    }

    fun part2(input: List<String>): Int {

        return 0
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("input/test_input")) == 41)

    val input = readInput("input/Day06")
    part1(input).println()
    part2(input).println()
}

fun Point.pointsTo(other: Point): List<Point> {
    val points = when {
        x != other.x -> {
            val range = if (x < other.x) x..other.x else x downTo other.x
            range.map { newX -> Point(newX, y) }
        }

        y != other.y -> {
            val range = if (y < other.y) y..other.y else y downTo other.y
            range.map { newY -> Point(x, newY) }
        }

        else -> emptyList()
    }
    return points
}
