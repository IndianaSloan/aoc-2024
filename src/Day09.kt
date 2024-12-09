import java.awt.Point
import kotlin.ranges.IntRange.Companion.EMPTY

fun main() {

    fun parseInput(input: List<String>): List<String> {
        val diskMap = mutableListOf<String>()
        var count = 0
        input.first().map(Char::digitToInt).forEachIndexed { index, number ->
            val range = if (number > 0) (1..number) else EMPTY
            if (!index.isEven()) {
                repeat(range.count()) { diskMap.add(".") }
            } else {
                repeat(range.count()) { diskMap.add(count.toString()) }
                count++
            }
        }
        println(diskMap.joinToString(""))
        return diskMap
    }

    fun deFragment(fileMap: List<String>): List<String> {
        val mutableDiskMap = fileMap.toMutableList()
        while(mutableDiskMap.indexOfFirst { it == FREE_SPACE } < mutableDiskMap.indexOfLast { it.toLongOrNull() != null }) {
            val nextDigitIndex = mutableDiskMap.indexOfLast { it.toLongOrNull() != null }
            val value = mutableDiskMap[nextDigitIndex]
            val nextSpaceIndex = mutableDiskMap.indexOfFirst { it == FREE_SPACE }
            mutableDiskMap.removeAt(nextSpaceIndex)
            mutableDiskMap.add(nextSpaceIndex, value)
            mutableDiskMap.removeAt(nextDigitIndex)
            mutableDiskMap.add(nextDigitIndex, FREE_SPACE)
        }
        return mutableDiskMap
    }

    fun part1(input: List<String>): Long {
        val diskMap = parseInput(input)
        val deFragmentedDiskMap = deFragment(diskMap)
        return deFragmentedDiskMap.mapNotNull { it.toLongOrNull() }
            .mapIndexed { index, value -> index * value }
            .sum()
    }

    fun part2(input: List<String>): Int {

        return 0
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(readInput("input/test_input")) == 1928)
    //check(part2(readInput("input/test_input")) == 34)

    val input = readInput("input/Day09")
    part1(input).println()
    part2(input).println()
}

private const val FREE_SPACE = "."