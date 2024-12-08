import java.awt.Point

fun main() {

    fun parseNodes(input: List<String>): List<Node> {
        return input.flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, char -> if (char != '.') Node(Point(x, y), char) else null }
        }
    }

    fun part1(input: List<String>): Int {
        val nodes = parseNodes(input)
        val groups = nodes.groupBy { it.char }
        return groups.flatMap { (char, nodes) ->
            val consumedPoints = groups[char]!!.map(Node::point)
            nodes.flatMap { currentNode ->
                nodes.toMutableList().apply { remove(currentNode) }
                    .map(currentNode::distanceTo)
                    .map(Point::reverse)
                    .map { currentNode.point.move(it) }
                    .filter { it.isInGrid(input) && it !in consumedPoints }
            }
        }.toSet().count()
    }

    fun part2(input: List<String>): Int {
        val nodes = parseNodes(input)
        val groups = nodes.groupBy { it.char }
        val count = groups.flatMap { (char, nodes) ->
            val consumedPoints = groups[char]!!.map(Node::point)
            nodes.flatMap { currentNode ->
                val distances =  nodes.toMutableList().apply { remove(currentNode) }
                    .map(currentNode::distanceTo)

                val possiblePoints = distances.flatMap { it.toVariations() }
                    .map { currentNode.point.move(it) }
                    .filter { it.isInGrid(input) && it !in consumedPoints }
                possiblePoints
            }
        }.toSet()
        println(count.sortedBy { it.y })
        return count.count()
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("input/test_input")) == 14)
    check(part2(readInput("input/test_input")) == 34)

    val input = readInput("input/Day08")
    part1(input).println()
    part2(input).println()
}

data class Node(val point: Point, val char: Char) {

    fun distanceTo(other: Node): Point {
        return point.distanceTo(other.point)
    }
}
