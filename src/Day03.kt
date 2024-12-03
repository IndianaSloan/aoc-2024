fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            INSTRUCTION.findAll(line)
                .toList()
                .sumOf { match -> match.value.calculate() }
        }
    }

    fun part2(input: List<String>): Int {
        var isAdding = true
        return input.sumOf { line ->
            EXTENDED_INSTRUCTION.findAll(line)
                .toList()
                .map(MatchResult::value)
                .sumOf { instruction ->
                    if (instruction == "do()") {
                        isAdding = true
                        0
                    } else if (instruction == "don't()") {
                        isAdding = false
                        0
                    } else if (isAdding) {
                        instruction.calculate()
                    } else {
                        0
                    }
                }
        }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("input/test_input")) == 161)
    check(part2(readInput("input/test_input")) == 48)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("input/Day03")
    part1(input).println()
    part2(input).println()
}

private fun String.calculate(): Int {
    val numbers = split(",")
        .map { value -> value.filter { it.isDigit() }.toInt() }
    println("${numbers[0]} * ${numbers[1]}")
    return numbers[0] * numbers[1]
}

private val INSTRUCTION = Regex("mul\\(\\d+,\\d+\\)")
private val EXTENDED_INSTRUCTION = Regex("mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)")
