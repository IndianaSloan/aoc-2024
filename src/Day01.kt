import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val (sortedFirst, sortedSecond) = splitLists(input)
        val result = sortedFirst.mapIndexed { index, i -> abs(i - sortedSecond[index]) }
            .sum()
        return result
    }

    fun part2(input: List<String>): Int {
        val (sortedFirst, sortedSecond) = splitLists(input)
        return sortedFirst.sumOf { item ->
            val similarity = sortedSecond.count { it == item }
            item * similarity
        }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("input/test_input")) == 11)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("input/Day01")
    part1(input).println()
    part2(input).println()
}


private fun splitLists(input: List<String>): Pair<List<Int>, List<Int>> {
    val firstList = mutableListOf<Int>()
    val secondList = mutableListOf<Int>()
    input.forEach {
        val numbers = it.split("   ").map(String::toInt)
        firstList.add(numbers[0])
        secondList.add(numbers[1])
    }

    val sortedFirst = firstList.sorted()
    val sortedSecond = secondList.sorted()
    return Pair(sortedFirst, sortedSecond)
}