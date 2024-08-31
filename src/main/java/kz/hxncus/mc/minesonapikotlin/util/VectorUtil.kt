package kz.hxncus.mc.minesonapikotlin.util

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector

/**
 * The zero Vector.
 * Don't modify it.
 * If you want a modifiable one, create them.
 */
val ZERO_VECTOR: Vector = Vector()

/**
 * Gets min and max vector.
 *
 * @param firstBlock  the first block
 * @param secondBlock the second block
 * @return the min and max vector
 */
fun getMinAndMaxVector(firstBlock: Block?, secondBlock: Block?): Pair<Vector, Vector> {
    val boundingBox = BoundingBox.of(firstBlock!!, secondBlock!!)
    return Pair(boundingBox.min, boundingBox.max)
}

/**
 * Gets min and max vector.
 *
 * @param firstLoc  the first loc
 * @param secondLoc the second loc
 * @return the min and max vector
 */
fun getMinAndMaxVector(firstLoc: Location?, secondLoc: Location?): Pair<Vector, Vector> {
    val boundingBox = BoundingBox.of(firstLoc!!, secondLoc!!)
    return Pair(boundingBox.min, boundingBox.max)
}

/**
 * Gets min and max vector.
 *
 * @param firstVector  the first vector
 * @param secondVector the second vector
 * @return the min and max vector
 */
fun getMinAndMaxVector(firstVector: Vector?, secondVector: Vector?): Pair<Vector, Vector> {
    val boundingBox = BoundingBox.of(firstVector!!, secondVector!!)
    return Pair(boundingBox.min, boundingBox.max)
}

/**
 * Gets min and max vector.
 *
 * @param location the location
 * @param x        the x
 * @param y        the y
 * @param z        the z
 * @return the min and max vector
 */
fun getMinAndMaxVector(location: Location?, x: Double, y: Double, z: Double): Pair<Vector, Vector> {
    val boundingBox = BoundingBox.of(location!!, x, y, z)
    return Pair(boundingBox.min, boundingBox.max)
}

/**
 * Gets min and max vector.
 *
 * @param vector the vector
 * @param x      the x
 * @param y      the y
 * @param z      the z
 * @return the min and max vector
 */
fun getMinAndMaxVector(vector: Vector?, x: Double, y: Double, z: Double): Pair<Vector, Vector> {
    val boundingBox = BoundingBox.of(vector!!, x, y, z)
    return Pair(boundingBox.min, boundingBox.max)
}
