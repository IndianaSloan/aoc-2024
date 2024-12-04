import java.awt.Point

fun main() {

    fun part1(input: List<String>): Int {
        val points = findMatches(input)
        return points.count()
    }

    fun part2(input: List<String>): Int {
        val points = findCrossMatches(input)
        return points.count()
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("input/test_input")) == 18)
    check(part2(readInput("input/test_input")) == 9)

    val input = readInput("input/Day04")
    part1(input).println()
    part2(input).println()
}

fun checkWordMatch(grid: List<String>, word: String, x: Int, y: Int, xOffset: Int, yOffset: Int): Boolean {
    for (i in word.indices) {
        val nextX = x + (xOffset * i)
        val nextY = y + (yOffset * i)
        if (nextY !in grid.indices || nextX !in grid[0].indices || grid[nextY][nextX] != word[i]) {
            return false
        }
    }
    return true
}

private fun findMatches(grid: List<String>): List<Point> {
    val word = "XMAS"
    val matches = mutableListOf<Point>()
    val rowRange = 0 until grid[0].length - word.length + 1
    val rowRangeReversed = grid[0].lastIndex downTo word.length - 1
    val columnRange = 0 until grid.size - word.length + 1
    val columnRangeReversed = grid.lastIndex downTo word.length - 1

    // Horizontal
    for (y in grid.indices) {
        // Left -> Right
        for (x in rowRange) {
            if (checkWordMatch(grid, word, x, y, 1, 0)) {
                matches.add(Point(x, y))
            }
        }
        // Right -> Left
        for (x in rowRangeReversed) {
            if (checkWordMatch(grid, word, x, y, -1, 0)) {
                matches.add(Point(x, y))
            }
        }
    }

    // Vertical
    for (x in grid[0].indices) {
        // Top -> Bottom
        for (y in columnRange) {
            if (checkWordMatch(grid, word, x, y, 0, 1)) {
                matches.add(Point(x, y))
            }
        }
        // Bottom -> Top
        for (y in columnRangeReversed) {
            if (checkWordMatch(grid, word, x, y, 0, -1)) {
                matches.add(Point(x, y))
            }
        }
    }

    // Top Left -> Bottom Right
    for (y in columnRange) {
        for (x in rowRange) {
            if (checkWordMatch(grid, word, x, y, 1, 1)) {
                matches.add(Point(x, y))
            }
        }
    }
    // Top Right -> Bottom Left
    for (y in columnRange) {
        for (x in rowRangeReversed) {
            if (checkWordMatch(grid, word, x, y, -1, 1)) {
                matches.add(Point(x, y))
            }
        }
    }

    // Bottom Left -> Top Right
    for (y in columnRangeReversed) {
        for (x in rowRange) {
            if (checkWordMatch(grid, word, x, y, 1, -1)) {
                matches.add(Point(x, y))
            }
        }
    }

    // Bottom Right -> Top Left
    for (y in columnRangeReversed) {
        for (x in rowRangeReversed) {
            if (checkWordMatch(grid, word, x, y, -1, -1)) {
                matches.add(Point(x, y))
            }
        }
    }
    return matches
}

private fun findCrossMatches(grid: List<String>): List<Point> {
    val matches = mutableListOf<Point>()
    val rowRange = 1 until grid[0].length - 1
    val columnRange = 1 until grid.size - 1

    for (y in columnRange) {
        for (x in rowRange) {
            if (grid[y][x] == 'A') {
                if (checkCrossMatch(grid, x, y)) {
                    matches.add(Point(x, y))
                }
            }
        }
    }

    return matches
}

private fun checkCrossMatch(grid: List<String>, centerX: Int, centerY: Int): Boolean {
    val word = "MAS"
    // Top Left -> Bottom Right
    val ltr = checkWordMatch(grid, word, centerX - 1, centerY - 1, 1, 1) ||
            // Bottom Right -> Top Left
            checkWordMatch(grid, word, centerX + 1, centerY + 1, -1, -1)

    // Top Right -> Bottom Left
    val rtl = checkWordMatch(grid, word, centerX + 1, centerY - 1, -1, 1) ||
    // Bottom Left -> Top Right
        checkWordMatch(grid, word, centerX -1, centerY + 1, 1, -1)

    return ltr && rtl
}
