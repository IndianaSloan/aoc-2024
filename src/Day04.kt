import java.awt.Point

fun main() {

    fun part1(input: List<String>): Int {
        val points = findMatches(input)
        return points.count()
    }

    fun part2(input: List<String>): Int {

        return 0
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("input/test_input")) == 18)

    val input = readInput("input/Day04")
    part1(input).println()
}

private const val WORD_MATCH = "XMAS"

fun findMatches(grid: List<String>): List<Point> {
    val matches = mutableListOf<Point>()
    val rowRange = 0 until grid[0].length - WORD_MATCH.length + 1
    val rowRangeReversed = grid[0].lastIndex downTo WORD_MATCH.length - 1
    val columnRange = 0 until grid.size - WORD_MATCH.length + 1
    val columnRangeReversed = grid.lastIndex downTo WORD_MATCH.length - 1

    fun checkWordMatch(x: Int, y: Int, xOffset: Int, yOffset: Int): Boolean {
        for (i in WORD_MATCH.indices) {
            val nextX = x + (xOffset * i)
            val nextY = y + (yOffset * i)
            if (nextY !in grid.indices || nextX !in grid[0].indices || grid[nextY][nextX] != WORD_MATCH[i]) {
                return false
            }
        }
        return true
    }

    // Horizontal
    for (y in grid.indices) {
        // Left -> Right
        for (x in rowRange) {
            if (checkWordMatch(x, y, 1, 0)) {
                matches.add(Point(x, y))
            }
        }
        // Right -> Left
        for (x in rowRangeReversed) {
            if (checkWordMatch(x, y, -1, 0)) {
                matches.add(Point(x, y))
            }
        }
    }

    // Vertical
    for (x in grid[0].indices) {
        // Top -> Bottom
        for (y in columnRange) {
            if (checkWordMatch(x, y, 0, 1)) {
                matches.add(Point(x, y))
            }
        }
        // Bottom -> Top
        for (y in columnRangeReversed) {
            if (checkWordMatch(x, y, 0, -1)) {
                matches.add(Point(x, y))
            }
        }
    }

    // Top Left -> Bottom Right
    for (y in columnRange) {
        for (x in rowRange) {
            if (checkWordMatch(x, y, 1, 1)) {
                matches.add(Point(x, y))
            }
        }
    }
    // Top Right -> Bottom Left
    for (y in columnRange) {
        for (x in rowRangeReversed) {
            if (checkWordMatch(x, y, -1, 1)) {
                matches.add(Point(x, y))
            }
        }
    }

    // Bottom Left -> Top Right
    for (y in columnRangeReversed) {
        for (x in rowRange) {
            if (checkWordMatch(x, y, 1, -1)) {
                matches.add(Point(x, y))
            }
        }
    }

    // Bottom Right -> Top Left
    for (y in columnRangeReversed) {
        for (x in rowRangeReversed) {
            if (checkWordMatch(x, y, -1, -1)) {
                matches.add(Point(x, y))
            }
        }
    }
    return matches
}