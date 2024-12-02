import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val safeRange = (1..3)
        return input
            .map { it.split(" ").map(String::toInt) }
            .count { levels ->
                val isAscending = levels.zipWithNext { a, b -> a < b }.all { it }
                val isDescending = levels.zipWithNext { a, b -> a > b }.all { it }
                val isSafeRange = levels.zipWithNext { a, b -> abs(a - b) in (safeRange) }.all { it }
                (isAscending || isDescending) && isSafeRange
            }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("input/test_input")) == 2)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("input/Day02")
    part1(input).println()
    part2(input).println()
}
