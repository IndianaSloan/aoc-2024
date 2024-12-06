import Direction.*
import java.awt.Point

fun main() {

    var currentPosition = Position(0, 0, UP)
    val obstacles = mutableListOf<Point>()
    val pointsVisited = mutableListOf<Position>()

    fun parseInput(input: List<String>) {
        obstacles.clear()
        pointsVisited.clear()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '#') {
                    obstacles.add(Point(x, y))
                }
            }
            if (line.contains('^')) {
                val x = line.indexOfFirst { it == '^' }
                currentPosition = Position(x, y, UP)
            }
        }
    }

    fun part1(input: List<String>): Int {
        parseInput(input)
        pointsVisited.add(currentPosition.copy())
        while (!currentPosition.canEscape(input)) {
            currentPosition.move()
            pointsVisited.add(currentPosition.copy())
            if (!currentPosition.canMove(input)) {
                currentPosition.turn()
            }
        }
        return pointsVisited.map { Point(it.x, it.y) }.toSet().count()
    }

    fun part2(input: List<String>): Int {
        parseInput(input)
        pointsVisited.add(currentPosition.copy())
        var loopCount = 0
        while (!currentPosition.canEscape(input)) {
            currentPosition.move()
            pointsVisited.add(currentPosition.copy())

            // Take a copy of the current point, turn it and move until the next obstacle. If we've already
            // been to the position it ends up, then we are theoretically in a loop.
            val posCopy = currentPosition.copy()
            posCopy.turn()
            while (posCopy.canMove(input)) {
                posCopy.move()
            }
            if (pointsVisited.contains(posCopy)) {
                loopCount++
            }

            if (!currentPosition.canMove(input)) {
                currentPosition.turn()
            }
        }
        return loopCount
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("input/test_input")) == 41)
    check(part2(readInput("input/test_input")) == 6)

    val input = readInput("input/Day06")
    part1(input).println()
    part2(input).println()
}

private enum class Direction { UP, DOWN, LEFT, RIGHT }

private data class Position(var x: Int, var y: Int, var direction: Direction?) {

    fun move() {
        when (direction) {
            UP -> y -= 1
            DOWN -> y += 1
            LEFT -> x -= 1
            RIGHT -> x += 1
            else -> {} // Do nothing
        }
    }

    fun canMove(grid: List<String>): Boolean {
        return if (x == 0 || x == grid.first().lastIndex || y == 0 || y == grid.lastIndex) {
            false
        } else {
            val nextPoint = when (direction) {
                UP -> grid[y - 1][x]
                DOWN -> grid[y + 1][x]
                LEFT -> grid[y][x - 1]
                RIGHT -> grid[y][x + 1]
                null -> return false
            }
            nextPoint != '#'
        }
    }

    fun canEscape(grid: List<String>): Boolean {
        return x == 0 || x == grid.first().lastIndex || y == 0 || y == grid.lastIndex
    }

    fun turn() {
        direction = when (direction) {
            UP -> RIGHT
            DOWN -> LEFT
            LEFT -> UP
            RIGHT -> DOWN
            else -> null
        }
    }
}
