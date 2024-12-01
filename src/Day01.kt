import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()
        input.forEach {
            val numbers = it.split("   ").map(String::toInt)
            firstList.add(numbers[0])
            secondList.add(numbers[1])
        }

        val sortedFirst = firstList.sorted()
        val sortedSecond = secondList.sorted()
        val result = sortedFirst.mapIndexed { index, i -> abs(i - sortedSecond[index]) }
            .sum()
        return result
        //return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("input/test_input")) == 11)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("input/Day01")
    part1(input).println()
    part2(input).println()
}
