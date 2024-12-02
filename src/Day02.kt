import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        return input
            .map { it.split(" ").map(String::toInt) }
            .count { levels -> levels.isSafe() }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.split(" ").map(String::toInt) }
            .count { levels -> levels.isSafeWithDampening() }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("input/test_input")) == 2)
    check(part2(readInput("input/test_input")) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("input/Day02")
    part1(input).println()
    part2(input).println()
}

private val safeRange = (1..3)

private fun List<Int>.isSafe(): Boolean {
    val isAscending = zipWithNext { a, b -> a < b }.all { it }
    val isDescending = zipWithNext { a, b -> a > b }.all { it }
    val isSafeRange = zipWithNext { a, b -> abs(a - b) in (safeRange) }.all { it }
    return (isAscending || isDescending) && isSafeRange
}

private fun List<Int>.isSafeWithDampening(): Boolean {
    if (isSafe()) {
        return true
    }

    forEachIndexed { index, _ ->
        val list = this.toMutableList()
        list.removeAt(index)
        if (list.isSafe()) {
            return true
        }
    }
    return false
}