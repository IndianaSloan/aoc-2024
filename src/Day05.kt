fun main() {

    fun part1(input: List<String>): Int {
        val ordering = parseOrdering(input)
        val validUpdates = filterUpdates(input, ordering, true)
        return validUpdates.sumOf { numbers -> numbers[numbers.size / 2] }
    }

    fun part2(input: List<String>): Int {
        val ordering = parseOrdering(input)
        val invalidUpdates = filterUpdates(input, ordering, false)
        val validUpdates = invalidUpdates.map { numbers ->
            val validNumbers = mutableListOf<Int>()
            val inValidNumbers = mutableListOf<Int>()
            numbers.forEachIndexed { index, currentNumber ->
                (index + 1 until numbers.size).forEach { i ->
                    val nextNumber = numbers[i]
                    val invalid = ordering[nextNumber].orEmpty().contains(currentNumber)
                    if (invalid && !inValidNumbers.contains(currentNumber)) {
                        inValidNumbers.add(currentNumber)
                    }
                }
                if (currentNumber !in inValidNumbers) {
                    validNumbers.add(currentNumber)
                }
            }
            inValidNumbers.forEach { currentInvalidNumber ->
                var indexToInsert = 0
                for (i in validNumbers.indices) {
                    val currentNumber = validNumbers[i]
                    val shouldPlaceAfterIndex = ordering[currentNumber].orEmpty().contains(currentInvalidNumber)
                    if (shouldPlaceAfterIndex) {
                        val currentInvalidNumberRules = ordering[currentInvalidNumber].orEmpty()
                        val isEnd = i == validNumbers.lastIndex
                        if (isEnd) {
                            indexToInsert = validNumbers.lastIndex + 1
                            break
                        }
                        val nextNumber = validNumbers[i + 1]
                        val shouldPlaceBeforeNext = currentInvalidNumberRules.contains(nextNumber)
                        if (shouldPlaceBeforeNext) {
                            indexToInsert = i + 1
                            break
                        }
                    }
                }
                validNumbers.add(indexToInsert, currentInvalidNumber)
            }
            validNumbers
        }
        return validUpdates.sumOf { numbers -> numbers[numbers.size / 2] }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("input/test_input")) == 143)
    check(part2(readInput("input/test_input")) == 123)

    val input = readInput("input/Day05")
    part1(input).println()
    part2(input).println()
}

private fun parseOrdering(input: List<String>): Map<Int, List<Int>> {
    return input.filter { it.contains("|") }
        .map {
            val numbers = it.split("|").map(String::toInt)
            Pair(numbers.first(), numbers.last())
        }
        .groupBy { it.first }
        .mapValues { (_, value) -> value.map { it.second } }
}

private fun filterUpdates(input: List<String>, ordering: Map<Int, List<Int>>, returnValid: Boolean): List<List<Int>> {
    return input.filter { !it.contains("|") && it.isNotEmpty() }
        .map { it.split(",").map(String::toInt) }
        .filter { numbers ->
            val invalidCount = numbers.mapIndexed { index, currentNumber ->
                val isInvalid = (index + 1 until numbers.size).any { i ->
                    val nextNumber = numbers[i]
                    ordering[nextNumber].orEmpty().contains(currentNumber)
                }
                isInvalid
            }.count { it }
            if (returnValid) invalidCount == 0 else invalidCount > 0
        }
}
