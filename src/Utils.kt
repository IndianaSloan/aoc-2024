import java.awt.Point
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.abs

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun Int.isEven(): Boolean = this % 2 == 0

fun Point.distanceTo(other: Point): Point {
    val dx = other.x - x
    val dy = other.y - y
    return Point(dx, dy)
}

fun Point.reverse(): Point {
    val newX = if (x > 0) -x else abs(x)
    val newY = if (y > 0) -y else abs(y)
    return Point(newX, newY)
}

fun Point.move(other: Point): Point {
    return Point(
        x + other.x,
        y + other.y,
    )
}

fun Point.isInGrid(grid: List<String>): Boolean {
    return x in 0..grid.first().lastIndex && y in 0..grid.lastIndex
}

fun Point.toVariations(): List<Point> {
    return listOf(
        this,
        this.rotate(),
        this.rotate().rotate(),
        this.rotate().reverse().rotate()
    )
}

fun Point.rotate(): Point {
    return Point(
        y,
        if (x > 0) -x else abs(x),
    )
}