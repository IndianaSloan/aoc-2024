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
        return diskMap
    }

    fun deFragment(fileMap: List<String>): List<String> {
        val mutableDiskMap = fileMap.toMutableList()
        while (mutableDiskMap.indexOfFirst { it == FREE_SPACE } < mutableDiskMap.indexOfLast { it.toLongOrNull() != null }) {
            val nextDigitIndex = mutableDiskMap.indexOfLast { it != FREE_SPACE }
            val value = mutableDiskMap[nextDigitIndex]
            val nextSpaceIndex = mutableDiskMap.indexOfFirst { it == FREE_SPACE }
            mutableDiskMap.removeAt(nextSpaceIndex)
            mutableDiskMap.add(nextSpaceIndex, value)
            mutableDiskMap.removeAt(nextDigitIndex)
            mutableDiskMap.add(nextDigitIndex, FREE_SPACE)
        }
        return mutableDiskMap
    }

    fun deFragmentEqualSpace(fileMap: List<String>): List<String> {
        val mutableDiskMap = fileMap.toMutableList()
        val needsPlaced = mutableListOf<List<String>>()
        while (mutableDiskMap.indexOfFirst { it == FREE_SPACE } < mutableDiskMap.indexOfLast { it.toLongOrNull() != null }) {
            val nextDigitEndIndex = mutableDiskMap.indexOfLast { it != FREE_SPACE }
            val nextDigitValue = mutableDiskMap[nextDigitEndIndex]
            val nextDigitStartIndex = mutableDiskMap.indexOfFirst { it == nextDigitValue }
            val requiredFreeSpace = nextDigitEndIndex - nextDigitStartIndex + 1

            var freeSpaceStartIndex = 0
            var freeSpaceEndIndex = 0

            var currentStartValue = mutableDiskMap[freeSpaceStartIndex]
            while (currentStartValue != FREE_SPACE) {
                freeSpaceStartIndex++
                currentStartValue = mutableDiskMap[freeSpaceStartIndex]
            }
            freeSpaceEndIndex = freeSpaceStartIndex
            var currentEndValue = mutableDiskMap[freeSpaceEndIndex]
            while (currentEndValue == FREE_SPACE) {
                freeSpaceEndIndex++
                currentEndValue = mutableDiskMap[freeSpaceEndIndex]
                if (currentEndValue != FREE_SPACE) {
                    freeSpaceEndIndex--
                }
            }
            if (freeSpaceEndIndex - freeSpaceStartIndex >= requiredFreeSpace) {
                (0 until requiredFreeSpace).forEach { index ->
                    mutableDiskMap.removeAt(freeSpaceStartIndex + index)
                    mutableDiskMap.add(freeSpaceStartIndex + index, nextDigitValue)
                    mutableDiskMap.removeAt(nextDigitStartIndex + index)
                    mutableDiskMap.add(nextDigitStartIndex + index, FREE_SPACE)
                }
            } else {
                val removedItems = mutableListOf<String>()
                ((requiredFreeSpace - 1) downTo 0).forEach { index ->
                    mutableDiskMap.removeAt(nextDigitStartIndex + index)
                    removedItems.add(nextDigitValue)
                }
                needsPlaced.add(removedItems)
            }
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

    fun part2(input: List<String>): Long {
        val diskMap = parseInput(input)
        val deFragmentedDiskMap = deFragmentEqualSpace(diskMap)
        return 0
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("input/test_input")) == 1928L)
    check(part2(readInput("input/test_input")) == 2858L)

    val input = readInput("input/Day09")
    part1(input).println()
    part2(input).println()
}

private const val FREE_SPACE = "."