fun main() {

    fun parseInput(input: List<String>): List<Pair<Long, List<Long>>> {
        return input.map { line ->
            line.split(": ")
                .let { it[0].toLong() to it[1].split(" ").map(String::toLong) }
        }
    }

    fun isValid(round: Pair<Long, List<Long>>, commands: List<Char>): Boolean {
        val iterationsCount = commands.size.exp(round.second.size - 1)
        return (0 until iterationsCount).map { iterationIndex ->
            iterationIndex.toString(commands.size).padStart(round.second.size - 1, '0')
                .map { c -> commands[c.digitToInt(commands.size)] }
                .joinToString("")
        }.any { iteration ->
            var acc = round.second.first().toLong()
            iteration.forEachIndexed { index, char ->
                when (char) {
                    '+' -> acc += round.second[index + 1]
                    '*' -> acc *= round.second[index + 1]
                    '|' -> acc = (acc.toString() + round.second[index + 1].toString()).toLong()
                    else -> acc += 0
                }
            }
            round.first == acc
        }
    }

    fun part1(input: List<String>): Long {
        val rounds = parseInput(input)
        return rounds.filter { isValid(it, listOf('+', '*')) }
            .sumOf { it.first }
    }

    fun part2(input: List<String>): Long {
        val rounds = parseInput(input)
        return rounds.filter { isValid(it, listOf('+', '*', '|')) }
            .sumOf { it.first }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("input/test_input")) == 3749L)
    check(part2(readInput("input/test_input")) == 11387L)

    val input = readInput("input/Day07")
    part1(input).println()
    part2(input).println()
}


private fun Int.exp(n: Int): Int {
    var accumulate = this
    repeat(n - 1) { accumulate *= this }
    return accumulate
}
